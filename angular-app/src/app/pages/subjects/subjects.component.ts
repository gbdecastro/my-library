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
import { ISubjectResponse } from "@app/core/subjects/interfaces/subject.response";
import { SubjectService } from "@app/core/subjects/services/subject.service";
import { ISubjectRequest } from "@app/core/subjects/interfaces/subject.request";
import { UpsertComponent } from "@app/pages/subjects/upsert/upsert.component";

@Component({
    selector: "app-subjects",
    templateUrl: "./subjects.component.html",
    styleUrls: ["./subjects.component.scss"],
})
export class SubjectsComponent implements OnInit, OnDestroy {
    readonly columns = ["id", "description", "action"];
    subjectsData: MatTableDataSource<ISubjectResponse> = new MatTableDataSource<ISubjectResponse>();
    @ViewChild(MatPaginator) paginator!: MatPaginator;
    @ViewChild(MatSort) sort!: MatSort;
    formFilter: FormGroup;
    loading = false;
    private readonly unsubscribe$: Subject<void> = new Subject();

    constructor(
        private readonly service: SubjectService,
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
                this.subjectsData.filter = data;
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
                this.subjectsData = new MatTableDataSource<ISubjectResponse>(data);
                this.subjectsData.paginator = this.paginator;
                this.subjectsData.sort = this.sort;
                this.loading = false;
            });
    }

    ngOnDestroy(): void {
        this.unsubscribe$.next();
        this.unsubscribe$.complete();
    }

    openManagementDialog(subject: ISubjectResponse | null = null): void {
        const dialog = this.dialog.open(UpsertComponent, {
            width: "560px",
            hasBackdrop: false,
            data: {
                subject: subject,
            },
        });

        dialog
            .afterClosed()
            .pipe(takeUntil(this.unsubscribe$))
            .subscribe((data: ISubjectRequest | null) => {
                if (data) {
                    subject ? this.update(subject.id, data) : this.create(data);
                }
            });
    }

    onDelete(row: ISubjectResponse): void {
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
                            this.subjectsData.data = [
                                ...this.subjectsData.data.filter((item) => item.id !== row.id),
                            ];
                            this.snackBar.openSnackBar({
                                message: this.i18n.instant("SUBJECTS.DELETE_MESSAGE"),
                                type: "success",
                            });
                            this.loading = false;
                        });
                }
            });
    }

    private create(subject: ISubjectRequest): void {
        this.loading = true;
        this.service
            .create(subject)
            .pipe(takeUntil(this.unsubscribe$))
            .subscribe((data) => {
                this.subjectsData.data.push(data);
                this.subjectsData.data = [...this.subjectsData.data];

                this.snackBar.openSnackBar({
                    message: this.i18n.instant("SUBJECTS.CREATE_MESSAGE"),
                    type: "success",
                });
                this.loading = false;
            });
    }

    private update(id: number, subject: ISubjectRequest): void {
        this.loading = true;
        this.service
            .update(id, subject)
            .pipe(takeUntil(this.unsubscribe$))
            .subscribe((data) => {
                const index = this.subjectsData.data.findIndex((r) => r.id === data.id);
                this.subjectsData.data[index] = data;
                this.subjectsData.data = [...this.subjectsData.data];

                this.snackBar.openSnackBar({
                    message: this.i18n.instant("SUBJECTS.UPDATE_MESSAGE"),
                    type: "success",
                });
                this.loading = false;
            });
    }
}
