import { Pipe, PipeTransform } from "@angular/core";
import { ISubjectResponse } from "@app/core/subjects/interfaces/subject.response";

@Pipe({
    name: "subjects",
})
export class SubjectsPipe implements PipeTransform {
    transform(value: ISubjectResponse[], ...args: unknown[]): string {
        return value.map((item) => item.description).join(", ");
    }
}
