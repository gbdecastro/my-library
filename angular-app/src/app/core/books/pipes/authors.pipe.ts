import { Pipe, PipeTransform } from "@angular/core";
import { IAuthorResponse } from "@app/core/authors/interfaces/authors.response";

@Pipe({
    name: "authors",
})
export class AuthorsPipe implements PipeTransform {
    transform(value: IAuthorResponse[], ...args: unknown[]): string {
        return value.map((item) => item.name).join(", ");
    }
}
