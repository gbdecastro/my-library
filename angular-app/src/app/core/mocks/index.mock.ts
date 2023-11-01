import { IBooksResource, IBooksResourceList } from "@app/core/books/interfaces/books.resources";
import { IBookRequest } from "@app/core/books/interfaces/books.request";
import { IAuthorRequest } from "@app/core/authors/interfaces/authors.request";
import {
    IAuthorsResource,
    IAuthorsResourceList,
} from "@app/core/authors/interfaces/authors.resources";
import { ISubjectRequest } from "@app/core/subjects/interfaces/subject.request";
import {
    ISubjectResource,
    ISubjectResourceList,
} from "@app/core/subjects/interfaces/subject.resources";

export const AUTHOR_REQUEST: IAuthorRequest = {
    name: "",
};

export const AUTHOR_RESOURCE: IAuthorsResource = {
    _links: {},
    id: 20,
    name: "Author 1",
};

export const AUTHORS: IAuthorsResourceList = {
    _embedded: {
        authorResponseList: [AUTHOR_RESOURCE],
    },
    _links: {},
};

export const SUBJECT_REQUEST: ISubjectRequest = {
    description: "",
};

export const SUBJECT_RESOURCE: ISubjectResource = {
    _links: {},
    id: 50,
    description: "Subject 1",
};

export const SUBJECTS: ISubjectResourceList = {
    _embedded: {
        subjectResponseList: [SUBJECT_RESOURCE],
    },
    _links: {},
};

export const BOOK_REQUEST: IBookRequest = {
    title: "",
    edition: 0,
    publicationYear: "",
    authors: [],
    subjects: [],
};

export const BOOK_RESOURCE: IBooksResource = {
    _links: {},
    id: 70,
    title: "Book 1",
    edition: 1,
    publicationYear: "2023",
    authors: [AUTHOR_RESOURCE],
    subjects: [SUBJECT_RESOURCE],
};

export const BOOKS: IBooksResourceList = {
    _embedded: {
        bookResponseList: [BOOK_RESOURCE],
    },
    _links: {},
};
