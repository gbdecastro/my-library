import { NgModule } from "@angular/core";
import { RouterModule, Routes } from "@angular/router";
import { AuthorsComponent } from "@app/pages/authors/authors.component";
import { NotFoundComponent } from "@app/pages/handlers/not-found/not-found.component";

const routes: Routes = [
    {
        path: "",
        component: AuthorsComponent,
    },
    { path: "**", component: NotFoundComponent },
];

@NgModule({
    imports: [RouterModule.forChild(routes)],
    exports: [RouterModule],
})
export class AuthorsRoutingModule {}
