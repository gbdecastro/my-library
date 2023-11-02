import { Component, Inject, OnDestroy } from "@angular/core";
import { AbstractControl, FormBuilder, FormGroup, Validators } from "@angular/forms";
import { MAT_DIALOG_DATA } from "@angular/material/dialog";
import { IBookResponse } from "@app/core/books/interfaces/books.response";
import { SubjectService } from "@app/core/subjects/services/subject.service";
import { AuthorsService } from "@app/core/authors/services/authors.service";
import { IAuthorResponse } from "@app/core/authors/interfaces/authors.response";
import { ISubjectResponse } from "@app/core/subjects/interfaces/subject.response";
import { finalize, forkJoin, Subject, takeUntil } from "rxjs";
import { IAuthorRequest } from "@app/core/authors/interfaces/authors.request";
import { ISubjectRequest } from "@app/core/subjects/interfaces/subject.request";
import { DateAdapter, MAT_DATE_FORMATS } from "@angular/material/core";
import { MatDatepicker } from "@angular/material/datepicker";
import { CustomDateAdapter } from "@app/core/shared/custom-adapter.date";

export const MY_FORMATS = {
    parse: {
        dateInput: "yyyy",
    },
    display: {
        dateInput: "yyyy",
        monthYearLabel: "yyyy",
        dateA11yLabel: "LL",
        monthYearA11yLabel: "yyyy",
    },
};

@Component({
    selector: "app-upsert",
    templateUrl: "./upsert.component.html",
    styleUrls: ["./upsert.component.scss"],
    providers: [
        {
            provide: DateAdapter,
            useClass: CustomDateAdapter,
        },
        { provide: MAT_DATE_FORMATS, useValue: MY_FORMATS },
    ],
})
export class UpsertComponent implements OnDestroy {
    form: FormGroup;
    authorOptions: IAuthorResponse[] = [];
    subjectOptions: ISubjectResponse[] = [];
    loading = false;
    private readonly unsubscribe$: Subject<void> = new Subject();

    constructor(
        private readonly fb: FormBuilder,
        private readonly subjectService: SubjectService,
        private readonly authorService: AuthorsService,
        @Inject(MAT_DIALOG_DATA) private data: { book: IBookResponse | null }
    ) {
        this.form = this.fb.group({
            title: this.fb.control(data.book?.title, [
                Validators.required,
                Validators.maxLength(40),
            ]),
            edition: this.fb.control(data.book?.edition, [Validators.required, Validators.min(1)]),
            publicationYear: this.fb.control(data.book?.publicationYear, [Validators.required]),
            price: this.fb.control(data.book?.price, [Validators.required]),
            authors: this.fb.control(data.book?.authors || [], [Validators.required]),
            subjects: this.fb.control(data.book?.subjects || [], [Validators.required]),
        });

        this.loading = true;
        forkJoin({
            authors: this.authorService.getAll(),
            subjects: this.subjectService.getAll(),
        })
            .pipe(
                takeUntil(this.unsubscribe$),
                finalize(() => (this.loading = false))
            )
            .subscribe((data) => {
                this.authorOptions = data.authors;
                this.subjectOptions = data.subjects;
            });
    }

    get title(): AbstractControl {
        return this.form.controls["title"];
    }

    get edition(): AbstractControl {
        return this.form.controls["edition"];
    }

    get publicationYear(): AbstractControl {
        return this.form.controls["publicationYear"];
    }

    get price(): AbstractControl {
        return this.form.controls["price"];
    }

    get authors(): AbstractControl {
        return this.form.controls["authors"];
    }

    get subjects(): AbstractControl {
        return this.form.controls["subjects"];
    }

    tagAuthor(name: string): IAuthorRequest {
        return { name };
    }

    tagSubject(description: string): ISubjectRequest {
        return { description };
    }

    chosenYearHandler(date: Date, dp: MatDatepicker<any>): void {
        this.publicationYear.setValue(date);
        dp.close();
    }

    ngOnDestroy(): void {
        this.unsubscribe$.next();
        this.unsubscribe$.complete();
    }
}
