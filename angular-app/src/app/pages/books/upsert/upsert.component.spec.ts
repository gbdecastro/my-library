import { ComponentFixture, TestBed } from "@angular/core/testing";

import { UpsertComponent } from "./upsert.component";
import { FormsModule, ReactiveFormsModule } from "@angular/forms";
import { MAT_DIALOG_DATA, MatDialogModule } from "@angular/material/dialog";
import { AuthorsService } from "@app/core/authors/services/authors.service";
import { SubjectService } from "@app/core/subjects/services/subject.service";
import { of } from "rxjs";
import { NoopAnimationsModule } from "@angular/platform-browser/animations";
import { MatInputModule } from "@angular/material/input";
import { MatButtonModule } from "@angular/material/button";
import { TranslateLoader, TranslateModule } from "@ngx-translate/core";
import { TranslateFileLoader } from "@app/app.module";
import { NgSelectModule } from "@ng-select/ng-select";
import { HttpClientTestingModule } from "@angular/common/http/testing";
import { FeatherModule } from "angular-feather";
import { allIcons } from "angular-feather/icons";
import { MatChipsModule } from "@angular/material/chips";
import { MatIconModule } from "@angular/material/icon";
import { BOOK_RESOURCE } from "@app/core/mocks/book.mock";
import { AUTHOR_RESPONSE } from "@app/core/mocks/authors.mock";
import { SUBJECT_RESPONSE } from "@app/core/mocks/subject.mock";
import { MatDatepicker, MatDatepickerModule } from "@angular/material/datepicker";

describe("[U] Books - UpsertComponent", () => {
    let component: UpsertComponent;
    let fixture: ComponentFixture<UpsertComponent>;
    let authorService: AuthorsService;
    let subjectService: SubjectService;

    beforeEach(() => {
        TestBed.configureTestingModule({
            declarations: [UpsertComponent],
            imports: [
                NoopAnimationsModule,
                ReactiveFormsModule,
                MatInputModule,
                FormsModule,
                MatButtonModule,
                MatChipsModule,
                TranslateModule.forRoot({
                    loader: {
                        provide: TranslateLoader,
                        useClass: TranslateFileLoader,
                    },
                }),
                NgSelectModule,
                HttpClientTestingModule,
                MatDialogModule,
                MatDatepickerModule,
                MatIconModule,
                FeatherModule.pick(allIcons),
            ],
            providers: [
                SubjectService,
                AuthorsService,
                { provide: MAT_DIALOG_DATA, useValue: { book: { ...BOOK_RESOURCE } } },
            ],
        });

        authorService = TestBed.inject(AuthorsService);
        subjectService = TestBed.inject(SubjectService);

        spyOn(authorService, "getAll").and.returnValue(of([{ ...AUTHOR_RESPONSE }]));
        spyOn(subjectService, "getAll").and.returnValue(of([{ ...SUBJECT_RESPONSE }]));

        fixture = TestBed.createComponent(UpsertComponent);
        component = fixture.componentInstance;
    });

    it("should create the component", () => {
        expect(component).toBeTruthy();
    });

    it("should initialize the form with the provided book data", () => {
        fixture.detectChanges();

        expect(component.form.value.title).toBe(BOOK_RESOURCE.title);
        expect(component.form.value.edition).toBe(BOOK_RESOURCE.edition);
        expect(component.form.value.publicationYear).toBe(BOOK_RESOURCE.publicationYear);
        // Add more expectations for other form controls if necessary
    });

    it("should require the 'title' field", () => {
        const titleControl = component.title;
        titleControl.setValue(""); // Set an empty value

        expect(titleControl.valid).toBeFalsy();
        expect(titleControl.hasError("required")).toBeTruthy();
    });

    it("should require the 'edition' field with a minimum value of 1", () => {
        const editionControl = component.edition;
        editionControl.setValue(0); // Set a value less than 1

        expect(editionControl.valid).toBeFalsy();
        expect(editionControl.hasError("required")).toBeFalsy();
        expect(editionControl.hasError("min")).toBeTruthy();
    });

    it("should require the 'authors' and 'subjects' fields", () => {
        const authorsControl = component.authors;
        const subjectsControl = component.subjects;

        authorsControl.setValue([]);
        subjectsControl.setValue([]);

        expect(authorsControl.valid).toBeFalsy();
        expect(authorsControl.hasError("required")).toBeTruthy();

        expect(subjectsControl.valid).toBeFalsy();
        expect(subjectsControl.hasError("required")).toBeTruthy();
    });

    it("should load author and subject options", () => {
        expect(component.authorOptions).toEqual([{ ...AUTHOR_RESPONSE }]);
        expect(component.subjectOptions).toEqual([{ ...SUBJECT_RESPONSE }]);
    });

    it("should add tag to author and subject", () => {
        const name = "test";
        const description = "test";
        expect(component.tagAuthor(name)).toEqual({ name });
        expect(component.tagSubject(description)).toEqual({ description });
    });

    it("should choice year", () => {
        const date = new Date();
        component.chosenYearHandler(date, { close(): void {} } as MatDatepicker<any>);
        expect(component.publicationYear.value).toEqual(date);
    });

    it("should unsubscribe from observables on component destruction", () => {
        const unsubscribeSpy = spyOn(component["unsubscribe$"], "next");
        component.ngOnDestroy();
        expect(unsubscribeSpy).toHaveBeenCalled();
    });
});
