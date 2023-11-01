import { NgModule } from "@angular/core";
import { CommonModule } from "@angular/common";

import { BooksRoutingModule } from "./books-routing.module";
import { BooksComponent } from "./books.component";
import { FeatherModule } from "angular-feather";
import { MatButtonModule } from "@angular/material/button";
import { MatDialogModule } from "@angular/material/dialog";
import { MatFormFieldModule } from "@angular/material/form-field";
import { MatInputModule } from "@angular/material/input";
import { ReactiveFormsModule } from "@angular/forms";
import { TranslateModule } from "@ngx-translate/core";
import { MatTableModule } from "@angular/material/table";
import { LoaderModule } from "@layout/loader/loader.module";
import { MatSortModule } from "@angular/material/sort";
import { MatPaginatorModule } from "@angular/material/paginator";
import { UpsertComponent } from "./upsert/upsert.component";
import { NgSelectModule } from "@ng-select/ng-select";
import { MatChipsModule } from "@angular/material/chips";
import { MatIconModule } from "@angular/material/icon";
import { BookPipesModule } from "@app/core/books/pipes/book-pipes.module";
import { NgbTooltipModule } from "@ng-bootstrap/ng-bootstrap";
import { MatTooltipModule } from "@angular/material/tooltip";

@NgModule({
    declarations: [BooksComponent, UpsertComponent],
    imports: [
        CommonModule,
        BooksRoutingModule,
        FeatherModule,
        MatButtonModule,
        MatDialogModule,
        MatFormFieldModule,
        MatInputModule,
        ReactiveFormsModule,
        TranslateModule,
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
    ],
})
export class BooksModule {}
