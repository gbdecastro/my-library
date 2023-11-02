import { IBookForm, IBookRequest } from "@app/core/books/interfaces/books.request";
import { IBooksResource, IBooksResourceList } from "@app/core/books/interfaces/books.resources";
import { AUTHOR_RESPONSE } from "@app/core/mocks/authors.mock";
import { IBookResponse } from "@app/core/books/interfaces/books.response";
import { SUBJECT_RESOURCE } from "@app/core/mocks/subject.mock";
import { format } from "date-fns";

export const BOOK_REQUEST: IBookRequest = {
    title: "",
    edition: 0,
    price: 1.1,
    publicationYear: format(new Date(), "yyyy"),
    authors: [],
    subjects: [],
};

export const BOOK_FORM: IBookForm = {
    title: "",
    edition: 0,
    price: 1.1,
    publicationYear: new Date(),
    authors: [],
    subjects: [],
};

export const BOOK_RESOURCE: IBooksResource = {
    _links: {},
    id: 70,
    title: "Book 1",
    edition: 1,
    price: 1.1,
    publicationYear: format(new Date(), "yyyy"),
    authors: [AUTHOR_RESPONSE],
    subjects: [SUBJECT_RESOURCE],
};

export const BOOK_RESPONSE: IBookResponse = {
    id: 70,
    title: "Book 1",
    edition: 1,
    price: 1.1,
    publicationYear: format(new Date(), "yyyy"),
    authors: [AUTHOR_RESPONSE],
    subjects: [SUBJECT_RESOURCE],
};

export const BOOKS: IBooksResourceList = {
    _embedded: {
        bookResponseList: [BOOK_RESOURCE],
    },
    _links: {},
};
