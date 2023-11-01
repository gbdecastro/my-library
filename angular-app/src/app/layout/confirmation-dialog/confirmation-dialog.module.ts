import { NgModule } from "@angular/core";
import { CommonModule } from "@angular/common";
import { ConfirmationDialogComponent } from "@layout/confirmation-dialog/confirmation-dialog.component";
import { MatDialogModule } from "@angular/material/dialog";
import { MatButtonModule } from "@angular/material/button";
import { TranslateModule } from "@ngx-translate/core";

@NgModule({
    declarations: [ConfirmationDialogComponent],
    imports: [CommonModule, MatDialogModule, MatButtonModule, TranslateModule],
})
export class ConfirmationDialogModule {}
