import { CommonModule } from "@angular/common";
import { NgModule } from "@angular/core";
import { MatProgressSpinnerModule } from "@angular/material/progress-spinner";
import { LoaderComponent } from "./loader.component";
import { FeatherModule } from "angular-feather";
import { allIcons } from "angular-feather/icons";

@NgModule({
    declarations: [LoaderComponent],
    imports: [CommonModule, MatProgressSpinnerModule, FeatherModule.pick(allIcons)],
    exports: [LoaderComponent],
})
export class LoaderModule {}
