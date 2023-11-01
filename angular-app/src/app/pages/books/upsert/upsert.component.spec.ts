import { ComponentFixture, TestBed } from "@angular/core/testing";

import { UpsertComponent } from "./upsert.component";
import { FormsModule, ReactiveFormsModule } from "@angular/forms";
import { MAT_DIALOG_DATA, MatDialogModule } from "@angular/material/dialog";
import { AuthorsService } from "@app/core/authors/services/authors.service";
import { SubjectService } from "@app/core/subjects/services/subject.service";
import { of } from "rxjs";
import {
    AUTHOR_RESOURCE,
    AUTHORS,
    BOOK_RESOURCE,
    SUBJECT_RESOURCE,
    SUBJECTS,
} from "@app/core/mocks/index.mock";
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

        spyOn(authorService, "getAll").and.returnValue(of({ ...AUTHORS }));
        spyOn(subjectService, "getAll").and.returnValue(of({ ...SUBJECTS }));

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

    it("should require the 'publicationYear' field with a 4-digit value", () => {
        const publicationYearControl = component.publicationYear;
        publicationYearControl.setValue("23"); // Set a value with less than 4 digits

        expect(publicationYearControl.valid).toBeFalsy();
        expect(publicationYearControl.hasError("required")).toBeFalsy();
        expect(publicationYearControl.hasError("minlength")).toBeTruthy();
        expect(publicationYearControl.hasError("maxlength")).toBeFalsy();
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
        expect(component.authorOptions).toEqual([{ ...AUTHOR_RESOURCE }]);
        expect(component.subjectOptions).toEqual([{ ...SUBJECT_RESOURCE }]);
    });

    it("should unsubscribe from observables on component destruction", () => {
        const unsubscribeSpy = spyOn(component["unsubscribe$"], "next");
        component.ngOnDestroy();
        expect(unsubscribeSpy).toHaveBeenCalled();
    });
});
