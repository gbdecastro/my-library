import { NgModule } from "@angular/core";
import { RouterModule, Routes } from "@angular/router";
import { BaseComponent } from "@layout/base/base.component";
import { NotFoundComponent } from "@app/pages/handlers/not-found/not-found.component";

const routes: Routes = [
    {
        path: "",
        component: BaseComponent,
        children: [
            {
                path: "authors",
                loadChildren: () => import("./authors/authors.module").then((m) => m.AuthorsModule),
            },
            {
                path: "books",
                loadChildren: () => import("./books/books.module").then((m) => m.BooksModule),
            },
            {
                path: "subjects",
                loadChildren: () =>
                    import("./subjects/subjects.module").then((m) => m.SubjectsModule),
            },
        ],
    },
    { path: "**", component: NotFoundComponent },
];

@NgModule({
    imports: [RouterModule.forChild(routes)],
    exports: [RouterModule],
})
export class PagesRoutingModule {}
