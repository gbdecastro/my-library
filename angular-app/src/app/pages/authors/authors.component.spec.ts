import { ComponentFixture, TestBed } from "@angular/core/testing";

import { AuthorsComponent } from "./authors.component";
import { TranslateLoader, TranslateModule, TranslateService } from "@ngx-translate/core";
import { SnackBarService } from "@layout/snack-bar/snack-bar.service";
import { AuthorsService } from "@app/core/authors/services/authors.service";
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
import { LANG_PT_BR } from "@app/core/i18n/pt-br/pt-br";
import { of } from "rxjs";
import { AUTHOR_REQUEST, AUTHOR_RESOURCE, AUTHORS } from "@app/core/mocks/index.mock";
import { IAuthorResponse } from "@app/core/authors/interfaces/authors.response";

describe("[U] - AuthorsComponent", () => {
    let component: AuthorsComponent;
    let fixture: ComponentFixture<AuthorsComponent>;
    let service: AuthorsService;
    let translate: TranslateService;
    let snackBar: SnackBarService;

    beforeEach(() => {
        TestBed.configureTestingModule({
            declarations: [AuthorsComponent],
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

        service = TestBed.inject(AuthorsService);
        spyOn(service, "getAll").and.returnValue(of({ ...AUTHORS }));

        snackBar = TestBed.inject(SnackBarService);

        fixture = TestBed.createComponent(AuthorsComponent);
        component = fixture.componentInstance;
        fixture.detectChanges();
    });

    it("should create", () => {
        expect(component).toBeTruthy();
        expect(snackBar).toBeTruthy();
        expect(component.authorsData.data).not.toBeNull();
        expect(component.authorsData.paginator).not.toBeNull();
        expect(component.authorsData.sort).not.toBeNull();
    });

    it("should open management dialog for creation", () => {
        spyOn(component["dialog"], "open").and.returnValue({
            afterClosed: () => of({ ...AUTHOR_REQUEST }),
        } as any);

        spyOn(service, "create").and.returnValue(of({ ...AUTHOR_RESOURCE }));
        spyOn(snackBar, "openSnackBar").and.callThrough();

        component.openManagementDialog();

        expect(service.create).toHaveBeenCalledWith({ ...AUTHOR_REQUEST });
        expect(snackBar.openSnackBar).toHaveBeenCalled();
    });

    it("should open management dialog for edition", () => {
        const author: IAuthorResponse = { ...AUTHORS._embedded.authorResponseList[0] };
        const authorRequest = { ...AUTHOR_REQUEST };

        spyOn(component["dialog"], "open").and.returnValue({
            afterClosed: () => of(authorRequest),
        } as any);

        spyOn(service, "update").and.returnValue(of({ ...AUTHOR_RESOURCE }));
        spyOn(snackBar, "openSnackBar").and.callThrough();

        component.openManagementDialog(author);

        expect(service.update).toHaveBeenCalledWith(author.id, authorRequest);
        expect(snackBar.openSnackBar).toHaveBeenCalled();
    });

    it("should delete a author", () => {
        const author: IAuthorResponse = AUTHORS._embedded.authorResponseList[0];

        spyOn(component["dialog"], "open").and.returnValue({
            afterClosed: () => of(true),
        } as any);

        spyOn(service, "delete").and.returnValue(of(undefined));
        spyOn(snackBar, "openSnackBar").and.callThrough();

        component.onDelete(author);

        expect(service.delete).toHaveBeenCalledWith(author.id);
        expect(snackBar.openSnackBar).toHaveBeenCalled();
    });

    it("should filter", () => {
        component.search.setValue("teste");
        fixture.detectChanges();

        expect(component.authorsData.filteredData).toEqual([]);
    });
});
