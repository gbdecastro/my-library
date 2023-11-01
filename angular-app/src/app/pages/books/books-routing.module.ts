import { NgModule } from "@angular/core";
import { RouterModule, Routes } from "@angular/router";
import { BooksComponent } from "./books.component";
import { NotFoundComponent } from "../handlers/not-found/not-found.component";

const routes: Routes = [
    {
        path: "",
        component: BooksComponent,
    },
    { path: "**", component: NotFoundComponent },
];

@NgModule({
    imports: [RouterModule.forChild(routes)],
    exports: [RouterModule],
})
export class BooksRoutingModule {}
