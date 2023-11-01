import { NgModule } from "@angular/core";
import { CommonModule } from "@angular/common";

import { AuthorsRoutingModule } from "./authors-routing.module";
import { AuthorsComponent } from "@app/pages/authors/authors.component";
import { MatTableModule } from "@angular/material/table";
import { MatSortModule } from "@angular/material/sort";
import { MatPaginatorModule } from "@angular/material/paginator";
import { ReactiveFormsModule } from "@angular/forms";
import { TranslateModule } from "@ngx-translate/core";
import { MatFormFieldModule } from "@angular/material/form-field";
import { MatInputModule } from "@angular/material/input";
import { MatButtonModule } from "@angular/material/button";
import { MatIconModule } from "@angular/material/icon";
import { FeatherModule } from "angular-feather";
import { UpsertComponent } from "./upsert/upsert.component";
import { MatDialogModule } from "@angular/material/dialog";
import { ConfirmationDialogModule } from "@layout/confirmation-dialog/confirmation-dialog.module";

@NgModule({
    declarations: [AuthorsComponent, UpsertComponent],
    imports: [
        CommonModule,
        AuthorsRoutingModule,
        MatTableModule,
        MatSortModule,
        MatPaginatorModule,
        ReactiveFormsModule,
        TranslateModule,
        MatFormFieldModule,
        MatInputModule,
        MatButtonModule,
        MatIconModule,
        FeatherModule,
        MatDialogModule,
        ConfirmationDialogModule,
    ],
})
export class AuthorsModule {}
