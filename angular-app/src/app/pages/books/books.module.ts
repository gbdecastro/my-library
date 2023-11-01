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

@NgModule({
    declarations: [BooksComponent],
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
    ],
})
export class BooksModule {}
