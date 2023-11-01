import { CommonModule } from "@angular/common";
import { NgModule } from "@angular/core";

import { PagesRoutingModule } from "./pages-routing.module";
import { HandlerPageModule } from "./handlers/handler-page.module";
import { LayoutModule } from "@layout/layout.module";

@NgModule({
    declarations: [],
    imports: [CommonModule, PagesRoutingModule, HandlerPageModule, LayoutModule],
})
export class PagesModule {}
