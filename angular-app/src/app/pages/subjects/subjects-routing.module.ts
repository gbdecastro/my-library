import { NgModule } from "@angular/core";
import { RouterModule, Routes } from "@angular/router";
import { NotFoundComponent } from "../handlers/not-found/not-found.component";
import { SubjectsComponent } from "./subjects.component";

const routes: Routes = [
    {
        path: "",
        component: SubjectsComponent,
    },
    { path: "**", component: NotFoundComponent },
];

@NgModule({
    imports: [RouterModule.forChild(routes)],
    exports: [RouterModule],
})
export class SubjectsRoutingModule {}
