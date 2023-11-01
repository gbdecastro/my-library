import { NgModule } from "@angular/core";
import { CommonModule } from "@angular/common";
import { AuthorsPipe } from "@app/core/books/pipes/authors.pipe";
import { SubjectsPipe } from "@app/core/books/pipes/subjects.pipe";

@NgModule({
    declarations: [AuthorsPipe, SubjectsPipe],
    exports: [AuthorsPipe, SubjectsPipe],
    providers: [AuthorsPipe, SubjectsPipe],
    imports: [CommonModule],
})
export class BookPipesModule {}
