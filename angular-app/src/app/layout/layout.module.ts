import { CommonModule } from "@angular/common";
import { NgModule } from "@angular/core";
import { MatButtonModule } from "@angular/material/button";
import { RouterModule } from "@angular/router";
import { TranslateModule } from "@ngx-translate/core";
import { BaseComponent } from "./base/base.component";
import { MatTabsModule } from "@angular/material/tabs";
import { NgbTooltip } from "@ng-bootstrap/ng-bootstrap";
import { MatTooltipModule } from "@angular/material/tooltip";

@NgModule({
    declarations: [BaseComponent],
    imports: [
        CommonModule,
        TranslateModule,
        RouterModule,
        MatButtonModule,
        MatTabsModule,
        NgbTooltip,
        MatTooltipModule,
    ],
    exports: [BaseComponent],
})
export class LayoutModule {}
