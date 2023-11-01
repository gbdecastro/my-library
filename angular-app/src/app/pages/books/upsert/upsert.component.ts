import { Component, Inject, OnDestroy } from "@angular/core";
import { AbstractControl, FormBuilder, FormGroup, Validators } from "@angular/forms";
import { MAT_DIALOG_DATA } from "@angular/material/dialog";
import { IBookResponse } from "@app/core/books/interfaces/books.response";
import { SubjectService } from "@app/core/subjects/services/subject.service";
import { AuthorsService } from "@app/core/authors/services/authors.service";
import { IAuthorResponse } from "@app/core/authors/interfaces/authors.response";
import { ISubjectResponse } from "@app/core/subjects/interfaces/subject.response";
import { forkJoin, Subject, takeUntil } from "rxjs";

@Component({
    selector: "app-upsert",
    templateUrl: "./upsert.component.html",
    styleUrls: ["./upsert.component.scss"],
})
export class UpsertComponent implements OnDestroy {
    form: FormGroup;
    authorOptions: IAuthorResponse[] = [];
    subjectOptions: ISubjectResponse[] = [];

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
            publicationYear: this.fb.control(data.book?.publicationYear, [
                Validators.required,
                Validators.maxLength(4),
                Validators.minLength(4),
            ]),
            authors: this.fb.control(data.book?.authors.map((a) => a.id) || [], [
                Validators.required,
            ]),
            subjects: this.fb.control(data.book?.subjects.map((b) => b.id) || [], [
                Validators.required,
            ]),
        });

        forkJoin({
            authors: this.authorService.getAll(),
            subjects: this.subjectService.getAll(),
        })
            .pipe(takeUntil(this.unsubscribe$))
            .subscribe((data) => {
                this.authorOptions = data.authors._embedded.authorResponseList;
                this.subjectOptions = data.subjects._embedded.subjectResponseList;
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

    get authors(): AbstractControl {
        return this.form.controls["authors"];
    }

    get subjects(): AbstractControl {
        return this.form.controls["subjects"];
    }

    ngOnDestroy(): void {
        this.unsubscribe$.next();
        this.unsubscribe$.complete();
    }
}
