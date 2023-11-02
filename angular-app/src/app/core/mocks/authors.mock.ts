import { IAuthorRequest } from "@app/core/authors/interfaces/authors.request";
import {
    IAuthorsResource,
    IAuthorsResourceList,
} from "@app/core/authors/interfaces/authors.resources";
import { IAuthorResponse } from "@app/core/authors/interfaces/authors.response";

export const AUTHOR_REQUEST: IAuthorRequest = {
    name: "",
};

export const AUTHOR_RESOURCE: IAuthorsResource = {
    _links: {},
    id: 20,
    name: "Author 1",
};

export const AUTHOR_RESPONSE: IAuthorResponse = {
    id: 20,
    name: "Author 1",
};

export const AUTHORS: IAuthorsResourceList = {
    _embedded: {
        authorResponseList: [AUTHOR_RESPONSE],
    },
    _links: {},
};
