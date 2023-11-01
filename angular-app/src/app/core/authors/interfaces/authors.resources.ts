import { IResource } from "../../shared/interfaces/hateoas";
import { IAuthorResponse } from "./authors.response";

export interface IAuthorsResource extends IAuthorResponse, IResource {}

export interface IAuthorsResourceList extends IResource {
    _embedded: {
        authorResponseList: IAuthorResponse[];
    };
}
