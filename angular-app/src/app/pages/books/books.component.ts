import { Component, OnDestroy, OnInit, ViewChild } from "@angular/core";
import { MatTableDataSource } from "@angular/material/table";
import { MatPaginator } from "@angular/material/paginator";
import { MatSort } from "@angular/material/sort";
import { AbstractControl, FormBuilder, FormGroup } from "@angular/forms";
import { distinctUntilChanged, Subject, takeUntil } from "rxjs";
import { TranslateService } from "@ngx-translate/core";
import { SnackBarService } from "@layout/snack-bar/snack-bar.service";
import { MatDialog } from "@angular/material/dialog";
import { ConfirmationDialogComponent } from "@layout/confirmation-dialog/confirmation-dialog.component";
import { IBookResponse } from "@app/core/books/interfaces/books.response";
import { BooksService } from "@app/core/books/services/books.service";
import { IBookRequest } from "@app/core/books/interfaces/books.request";
import { UpsertComponent } from "@app/pages/books/upsert/upsert.component";

@Component({
    selector: "app-books",
    templateUrl: "./books.component.html",
    styleUrls: ["./books.component.scss"],
})
export class BooksComponent implements OnInit, OnDestroy {
    readonly columns = [
        "id",
        "title",
        "edition",
        "publicationYear",
        "authors",
        "subjects",
        "action",
    ];
    bookData: MatTableDataSource<IBookResponse> = new MatTableDataSource<IBookResponse>();
    @ViewChild(MatPaginator) paginator!: MatPaginator;
    @ViewChild(MatSort) sort!: MatSort;
    formFilter: FormGroup;
    loading = false;
    private readonly unsubscribe$: Subject<void> = new Subject();

    constructor(
        private readonly service: BooksService,
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
                this.bookData.filter = data;
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
                this.bookData = new MatTableDataSource<IBookResponse>(
                    data._embedded.bookResponseList
                );
                this.bookData.paginator = this.paginator;
                this.bookData.sort = this.sort;
                this.loading = false;
            });
    }

    ngOnDestroy(): void {
        this.unsubscribe$.next();
        this.unsubscribe$.complete();
    }

    openManagementDialog(book: IBookResponse | null = null): void {
        const dialog = this.dialog.open(UpsertComponent, {
            width: "60%",
            data: {
                book: book,
            },
        });

        dialog
            .afterClosed()
            .pipe(takeUntil(this.unsubscribe$))
            .subscribe((data: IBookRequest | null) => {
                if (data) {
                    book ? this.update(book.id, data) : this.create(data);
                }
            });
    }

    onDelete(row: IBookResponse): void {
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
                            this.bookData.data = [
                                ...this.bookData.data.filter((item) => item.id !== row.id),
                            ];
                            this.snackBar.openSnackBar({
                                message: this.i18n.instant("BOOKS.DELETE_MESSAGE"),
                                type: "success",
                            });
                            this.loading = false;
                        });
                }
            });
    }

    private create(book: IBookRequest): void {
        this.loading = true;
        this.service
            .create(book)
            .pipe(takeUntil(this.unsubscribe$))
            .subscribe((data) => {
                this.bookData.data.push(data);
                this.bookData.data = [...this.bookData.data];

                this.snackBar.openSnackBar({
                    message: this.i18n.instant("BOOKS.CREATE_MESSAGE"),
                    type: "success",
                });
                this.loading = false;
            });
    }

    private update(id: number, book: IBookRequest): void {
        this.loading = true;
        this.service
            .update(id, book)
            .pipe(takeUntil(this.unsubscribe$))
            .subscribe((data) => {
                const index = this.bookData.data.findIndex((r) => r.id === data.id);
                this.bookData.data[index] = data;
                this.bookData.data = [...this.bookData.data];

                this.snackBar.openSnackBar({
                    message: this.i18n.instant("BOOKS.UPDATE_MESSAGE"),
                    type: "success",
                });

                this.loading = false;
            });
    }
}
