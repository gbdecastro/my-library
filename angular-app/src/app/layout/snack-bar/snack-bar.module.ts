import { CommonModule } from "@angular/common";
import { NgModule } from "@angular/core";
import { MatButtonModule } from "@angular/material/button";
import { MatSnackBarModule } from "@angular/material/snack-bar";
import { SnackBarComponent } from "./snack-bar.component";
import { SnackBarService } from "./snack-bar.service";
import { FeatherModule } from "angular-feather";
import { allIcons } from "angular-feather/icons";

@NgModule({
    declarations: [SnackBarComponent],
    imports: [CommonModule, FeatherModule.pick(allIcons), MatButtonModule, MatSnackBarModule],
    providers: [SnackBarService],
})
export class SnackBarModule {}
