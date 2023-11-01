import { IResource } from "../../shared/interfaces/hateoas";
import { IBookResponse } from "./books.response";

export interface IBooksResource extends IBookResponse, IResource {}

export interface IBooksResourceList extends IResource {
    _embedded: {
        bookResponseList: IBookResponse[];
    };
}
