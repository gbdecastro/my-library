import { ComponentFixture, TestBed } from "@angular/core/testing";

import { SubjectsComponent } from "./subjects.component";
import { TranslateLoader, TranslateModule, TranslateService } from "@ngx-translate/core";
import { SnackBarService } from "@layout/snack-bar/snack-bar.service";
import { SubjectService } from "@app/core/subjects/services/subject.service";
import { LANG_PT_BR } from "@app/core/i18n/pt-br/pt-br";
import { of } from "rxjs";
import { SUBJECT_REQUEST, SUBJECT_RESOURCE, SUBJECTS } from "@app/core/mocks/index.mock";
import { FeatherModule } from "angular-feather";
import { allIcons } from "angular-feather/icons";
import { MatButtonModule } from "@angular/material/button";
import { MatDialog, MatDialogModule } from "@angular/material/dialog";
import { MatFormFieldModule } from "@angular/material/form-field";
import { MatInputModule } from "@angular/material/input";
import { ReactiveFormsModule } from "@angular/forms";
import { MatTableModule } from "@angular/material/table";
import { LoaderModule } from "@layout/loader/loader.module";
import { MatSortModule } from "@angular/material/sort";
import { MatPaginatorModule } from "@angular/material/paginator";
import { NgSelectModule } from "@ng-select/ng-select";
import { MatChipsModule } from "@angular/material/chips";
import { MatIconModule } from "@angular/material/icon";
import { NgbTooltipModule } from "@ng-bootstrap/ng-bootstrap";
import { MatTooltipModule } from "@angular/material/tooltip";
import { BrowserAnimationsModule } from "@angular/platform-browser/animations";
import { SnackBarModule } from "@layout/snack-bar/snack-bar.module";
import { TranslateFileLoader } from "@app/app.module";
import { HttpClientTestingModule } from "@angular/common/http/testing";
import { MatDialogMock } from "@app/pages/books/books.component.spec";
import { ISubjectResponse } from "@app/core/subjects/interfaces/subject.response";

describe("[U] - SubjectsComponent", () => {
    let component: SubjectsComponent;
    let fixture: ComponentFixture<SubjectsComponent>;
    let service: SubjectService;
    let translate: TranslateService;
    let snackBar: SnackBarService;

    beforeEach(() => {
        TestBed.configureTestingModule({
            declarations: [SubjectsComponent],
            imports: [
                FeatherModule.pick(allIcons),
                MatButtonModule,
                MatDialogModule,
                MatFormFieldModule,
                MatInputModule,
                ReactiveFormsModule,
                MatTableModule,
                LoaderModule,
                MatSortModule,
                MatPaginatorModule,
                NgSelectModule,
                MatChipsModule,
                MatIconModule,
                NgbTooltipModule,
                MatTooltipModule,
                BrowserAnimationsModule,
                SnackBarModule,
                TranslateModule.forRoot({
                    loader: {
                        provide: TranslateLoader,
                        useClass: TranslateFileLoader,
                    },
                }),
                HttpClientTestingModule,
            ],
            providers: [{ provide: MatDialog, useClass: MatDialogMock }],
        });

        translate = TestBed.inject(TranslateService);
        translate.use(LANG_PT_BR);

        service = TestBed.inject(SubjectService);
        spyOn(service, "getAll").and.returnValue(of({ ...SUBJECTS }));

        snackBar = TestBed.inject(SnackBarService);

        fixture = TestBed.createComponent(SubjectsComponent);
        component = fixture.componentInstance;
        fixture.detectChanges();
    });

    it("should create", () => {
        expect(component).toBeTruthy();
    });

    it("should create", () => {
        expect(component).toBeTruthy();
        expect(snackBar).toBeTruthy();
        expect(component.subjectsData.data).not.toBeNull();
        expect(component.subjectsData.paginator).not.toBeNull();
        expect(component.subjectsData.sort).not.toBeNull();
    });

    it("should open management dialog for creation", () => {
        spyOn(component["dialog"], "open").and.returnValue({
            afterClosed: () => of({ ...SUBJECT_REQUEST }),
        } as any);

        spyOn(service, "create").and.returnValue(of({ ...SUBJECT_RESOURCE }));
        spyOn(snackBar, "openSnackBar").and.callThrough();

        component.openManagementDialog();

        expect(service.create).toHaveBeenCalledWith({ ...SUBJECT_REQUEST });
        expect(snackBar.openSnackBar).toHaveBeenCalled();
    });

    it("should open management dialog for edition", () => {
        const subject: ISubjectResponse = { ...SUBJECTS._embedded.subjectResponseList[0] };
        const subjectRequest = { ...SUBJECT_REQUEST };

        spyOn(component["dialog"], "open").and.returnValue({
            afterClosed: () => of(subjectRequest),
        } as any);

        spyOn(service, "update").and.returnValue(of({ ...SUBJECT_RESOURCE }));
        spyOn(snackBar, "openSnackBar").and.callThrough();

        component.openManagementDialog(subject);

        expect(service.update).toHaveBeenCalledWith(subject.id, subjectRequest);
        expect(snackBar.openSnackBar).toHaveBeenCalled();
    });

    it("should delete a book", () => {
        const subject: ISubjectResponse = { ...SUBJECTS._embedded.subjectResponseList[0] };

        spyOn(component["dialog"], "open").and.returnValue({
            afterClosed: () => of(true),
        } as any);

        spyOn(service, "delete").and.returnValue(of(undefined));
        spyOn(snackBar, "openSnackBar").and.callThrough();

        component.onDelete(subject);

        expect(service.delete).toHaveBeenCalledWith(subject.id);
        expect(snackBar.openSnackBar).toHaveBeenCalled();
    });

    it("should filter", () => {
        component.search.setValue("teste");
        fixture.detectChanges();

        expect(component.subjectsData.filteredData).toEqual([]);
    });
});
