import { ComponentFixture, TestBed } from "@angular/core/testing";

import { BooksComponent } from "./books.component";
import { HttpClientTestingModule } from "@angular/common/http/testing";
import { BooksService } from "@app/core/books/services/books.service";
import { TranslateLoader, TranslateModule, TranslateService } from "@ngx-translate/core";
import { SnackBarService } from "@layout/snack-bar/snack-bar.service";
import { LANG_PT_BR } from "@app/core/i18n/pt-br/pt-br";
import { of } from "rxjs";
import { BrowserAnimationsModule } from "@angular/platform-browser/animations";
import { TranslateFileLoader } from "@app/app.module";
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
import { BookPipesModule } from "@app/core/books/pipes/book-pipes.module";
import { NgbTooltipModule } from "@ng-bootstrap/ng-bootstrap";
import { MatTooltipModule } from "@angular/material/tooltip";
import { FeatherModule } from "angular-feather";
import { allIcons } from "angular-feather/icons";
import { SnackBarModule } from "@layout/snack-bar/snack-bar.module";
import { IBookResponse } from "@app/core/books/interfaces/books.response";
import { BOOK_FORM, BOOK_RESOURCE, BOOK_RESPONSE, BOOKS } from "@app/core/mocks/book.mock";
import { IBookForm } from "@app/core/books/interfaces/books.request";

export class MatDialogMock {
    open(): any {
        return {
            afterClosed: () => of(),
        };
    }
}

describe("[U] - BooksComponent", () => {
    let component: BooksComponent;
    let fixture: ComponentFixture<BooksComponent>;
    let bookService: BooksService;
    let translate: TranslateService;
    let snackBar: SnackBarService;

    beforeEach(() => {
        TestBed.configureTestingModule({
            declarations: [BooksComponent],
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
                BookPipesModule,
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

        bookService = TestBed.inject(BooksService);
        spyOn(bookService, "getAll").and.returnValue(of([{ ...BOOK_RESPONSE }]));

        snackBar = TestBed.inject(SnackBarService);

        fixture = TestBed.createComponent(BooksComponent);
        component = fixture.componentInstance;
        fixture.detectChanges();
    });

    it("should create", () => {
        expect(component).toBeTruthy();
        expect(snackBar).toBeTruthy();
        expect(component.bookData.data).not.toBeNull();
        expect(component.bookData.paginator).not.toBeNull();
        expect(component.bookData.sort).not.toBeNull();
    });

    it("should open management dialog for creation", () => {
        spyOn(component["dialog"], "open").and.returnValue({
            afterClosed: () => of({ ...BOOK_FORM }),
        } as any);

        spyOn(bookService, "create").and.returnValue(of({ ...BOOK_RESOURCE }));
        spyOn(snackBar, "openSnackBar").and.callThrough();

        component.openManagementDialog();

        expect(bookService.create).toHaveBeenCalled();
        expect(snackBar.openSnackBar).toHaveBeenCalled();
    });

    it("should open management dialog for edition", () => {
        const book: IBookResponse = { ...BOOKS._embedded.bookResponseList[0] };
        const bookForm: IBookForm = { ...BOOK_FORM };

        spyOn(component["dialog"], "open").and.returnValue({
            afterClosed: () => of(bookForm),
        } as any);

        spyOn(bookService, "update").and.returnValue(of({ ...BOOK_RESOURCE }));
        spyOn(snackBar, "openSnackBar").and.callThrough();

        component.openManagementDialog(book);

        expect(bookService.update).toHaveBeenCalled();
        expect(snackBar.openSnackBar).toHaveBeenCalled();
    });

    it("should delete a book", () => {
        const book: IBookResponse = { ...BOOKS._embedded.bookResponseList[0] };

        spyOn(component["dialog"], "open").and.returnValue({
            afterClosed: () => of(true),
        } as any);

        spyOn(bookService, "delete").and.returnValue(of(undefined));
        spyOn(snackBar, "openSnackBar").and.callThrough();

        component.onDelete(book);

        expect(bookService.delete).toHaveBeenCalledWith(book.id);
        expect(snackBar.openSnackBar).toHaveBeenCalled();
    });

    it("should filter", () => {
        component.search.setValue("teste");
        fixture.detectChanges();

        expect(component.bookData.filteredData).toEqual([]);
    });
});
