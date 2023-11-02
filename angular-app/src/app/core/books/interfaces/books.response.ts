import { IAuthorResponse } from "@app/core/authors/interfaces/authors.response";
import { ISubjectResponse } from "@app/core/subjects/interfaces/subject.response";

export interface IBookResponse {
    id: number;
    title: string;
    edition: number;
    price: number;
    publicationYear: string;

    authors: IAuthorResponse[];
    subjects: ISubjectResponse[];
}
