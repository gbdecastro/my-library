import { Injectable } from "@angular/core";
import { environment } from "../../../../environments/environment";
import { HttpClient } from "@angular/common/http";
import { Observable } from "rxjs";
import { IAuthorsResource, IAuthorsResourceList } from "../interfaces/authors.resources";
import { IAuthorRequest } from "../interfaces/authors.request";

@Injectable({
    providedIn: "root",
})
export class AuthorsService {
    private API_URL = `${environment.API_URL}/authors`;

    constructor(private http: HttpClient) {}

    getAll(): Observable<IAuthorsResourceList> {
        return this.http.get<IAuthorsResourceList>(this.API_URL);
    }

    getById(id: number): Observable<IAuthorsResource> {
        return this.http.get<IAuthorsResource>(`${this.API_URL}/${id}`);
    }

    create(authorRequest: IAuthorRequest): Observable<IAuthorsResource> {
        return this.http.post<IAuthorsResource>(this.API_URL, authorRequest);
    }

    update(id: number, authorRequest: IAuthorRequest): Observable<IAuthorsResource> {
        return this.http.put<IAuthorsResource>(this.API_URL.concat(`/${id}`), authorRequest);
    }

    delete(id: number): Observable<void> {
        return this.http.delete<void>(this.API_URL.concat(`/${id}`));
    }
}
