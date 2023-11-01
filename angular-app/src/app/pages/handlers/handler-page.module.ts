import { CommonModule } from "@angular/common";
import { NgModule } from "@angular/core";
import { TranslateModule } from "@ngx-translate/core";
import { NotFoundComponent } from "./not-found/not-found.component";

@NgModule({
    declarations: [NotFoundComponent],
    imports: [CommonModule, TranslateModule],
    exports: [NotFoundComponent],
})
export class HandlerPageModule {}
