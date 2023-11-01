import { NgModule } from "@angular/core";
import { CommonModule } from "@angular/common";

import { SubjectsRoutingModule } from "./subjects-routing.module";
import { SubjectsComponent } from "./subjects.component";
import { FeatherModule } from "angular-feather";
import { MatButtonModule } from "@angular/material/button";
import { MatFormFieldModule } from "@angular/material/form-field";
import { MatInputModule } from "@angular/material/input";
import { MatPaginatorModule } from "@angular/material/paginator";
import { MatSortModule } from "@angular/material/sort";
import { MatTableModule } from "@angular/material/table";
import { ReactiveFormsModule } from "@angular/forms";
import { TranslateModule } from "@ngx-translate/core";
import { UpsertComponent } from "@app/pages/subjects/upsert/upsert.component";
import { MatDialogModule } from "@angular/material/dialog";
import { LoaderModule } from "@layout/loader/loader.module";

@NgModule({
    declarations: [SubjectsComponent, UpsertComponent],
    imports: [
        CommonModule,
        SubjectsRoutingModule,
        FeatherModule,
        MatButtonModule,
        MatFormFieldModule,
        MatInputModule,
        MatPaginatorModule,
        MatSortModule,
        MatTableModule,
        ReactiveFormsModule,
        TranslateModule,
        MatDialogModule,
        LoaderModule,
    ],
})
export class SubjectsModule {}
