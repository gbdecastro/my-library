import { Component, OnDestroy, OnInit, ViewChild } from "@angular/core";
import { AuthorsService } from "@app/core/authors/services/authors.service";
import { IAuthorResponse } from "@app/core/authors/interfaces/authors.response";
import { MatTableDataSource } from "@angular/material/table";
import { MatPaginator } from "@angular/material/paginator";
import { MatSort } from "@angular/material/sort";
import { AbstractControl, FormBuilder, FormGroup } from "@angular/forms";
import { distinctUntilChanged, Subject, takeUntil } from "rxjs";
import { MatDialog } from "@angular/material/dialog";
import { UpsertComponent } from "@app/pages/authors/upsert/upsert.component";
import { IAuthorRequest } from "@app/core/authors/interfaces/authors.request";
import { ConfirmationDialogComponent } from "@layout/confirmation-dialog/confirmation-dialog.component";
import { TranslateService } from "@ngx-translate/core";
import { SnackBarService } from "@layout/snack-bar/snack-bar.service";

@Component({
    selector: "app-authors",
    templateUrl: "./authors.component.html",
    styleUrls: ["./authors.component.scss"],
})
export class AuthorsComponent implements OnInit, OnDestroy {
    readonly columns = ["id", "name", "action"];
    authorsData: MatTableDataSource<IAuthorResponse> = new MatTableDataSource<IAuthorResponse>();
    @ViewChild(MatPaginator) paginator!: MatPaginator;
    @ViewChild(MatSort) sort!: MatSort;
    formFilter: FormGroup;
    loading = false;
    private readonly unsubscribe$: Subject<void> = new Subject();

    constructor(
        private readonly service: AuthorsService,
        private readonly fb: FormBuilder,
        private readonly i18n: TranslateService,
        private readonly snackBar: SnackBarService,
        private readonly dialog: MatDialog
    ) {
        this.formFilter = this.fb.group({
            search: this.fb.control(""),
        });

        this.search.valueChanges
            .pipe(distinctUntilChanged(), takeUntil(this.unsubscribe$))
            .subscribe((data) => {
                this.authorsData.filter = data;
            });
    }

    get search(): AbstractControl {
        return this.formFilter.controls["search"];
    }

    ngOnInit(): void {
        this.loading = true;
        this.service
            .getAll()
            .pipe(takeUntil(this.unsubscribe$))
            .subscribe((data) => {
                this.authorsData = new MatTableDataSource<IAuthorResponse>(data);
                this.authorsData.paginator = this.paginator;
                this.authorsData.sort = this.sort;
                this.loading = false;
            });
    }

    ngOnDestroy(): void {
        this.unsubscribe$.next();
        this.unsubscribe$.complete();
    }

    openManagementDialog(author: IAuthorResponse | null = null): void {
        const dialog = this.dialog.open(UpsertComponent, {
            width: "560px",
            hasBackdrop: false,
            data: {
                author: author,
            },
        });

        dialog
            .afterClosed()
            .pipe(takeUntil(this.unsubscribe$))
            .subscribe((data: IAuthorRequest | null) => {
                if (data) {
                    author ? this.update(author.id, data) : this.create(data);
                }
            });
    }

    onDelete(row: IAuthorResponse): void {
        const dialog = this.dialog.open(ConfirmationDialogComponent, {
            width: "360px",
            hasBackdrop: false,
            data: {
                title: this.i18n.instant("COMMON.CAUTION"),
                message: this.i18n.instant("COMMON.CONFIRM_ACTION"),
            },
        });

        dialog
            .afterClosed()
            .pipe(takeUntil(this.unsubscribe$))
            .subscribe((confirm) => {
                if (confirm) {
                    this.loading = true;
                    this.service
                        .delete(row.id)
                        .pipe(takeUntil(this.unsubscribe$))
                        .subscribe(() => {
                            this.authorsData.data = [
                                ...this.authorsData.data.filter((item) => item.id !== row.id),
                            ];
                            this.snackBar.openSnackBar({
                                message: this.i18n.instant("AUTHORS.DELETE_MESSAGE"),
                                type: "success",
                            });
                            this.loading = false;
                        });
                }
            });
    }

    private create(author: IAuthorRequest): void {
        this.loading = true;
        this.service
            .create(author)
            .pipe(takeUntil(this.unsubscribe$))
            .subscribe((data) => {
                this.authorsData.data.push(data);
                this.authorsData.data = [...this.authorsData.data];

                this.snackBar.openSnackBar({
                    message: this.i18n.instant("AUTHORS.CREATE_MESSAGE"),
                    type: "success",
                });
                this.loading = false;
            });
    }

    private update(id: number, author: IAuthorRequest): void {
        this.loading = true;
        this.service
            .update(id, author)
            .pipe(takeUntil(this.unsubscribe$))
            .subscribe((data) => {
                const index = this.authorsData.data.findIndex((r) => r.id === data.id);
                this.authorsData.data[index] = data;
                this.authorsData.data = [...this.authorsData.data];

                this.snackBar.openSnackBar({
                    message: this.i18n.instant("AUTHORS.UPDATE_MESSAGE"),
                    type: "success",
                });

                this.loading = false;
            });
    }
}
