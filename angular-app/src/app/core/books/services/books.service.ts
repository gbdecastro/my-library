import { Injectable } from "@angular/core";
import { environment } from "../../../../environments/environment";
import { HttpClient } from "@angular/common/http";
import { Observable } from "rxjs";
import { IBooksResource, IBooksResourceList } from "@app/core/books/interfaces/books.resources";
import { IBookRequest } from "@app/core/books/interfaces/books.request";

@Injectable({
    providedIn: "root",
})
export class BooksService {
    private API_URL = `${environment.API_URL}/books`;

    constructor(private http: HttpClient) {}

    getAll(): Observable<IBooksResourceList> {
        return this.http.get<IBooksResourceList>(this.API_URL);
    }

    getById(id: number): Observable<IBooksResource> {
        return this.http.get<IBooksResource>(`${this.API_URL}/${id}`);
    }

    create(authorRequest: IBookRequest): Observable<IBooksResource> {
        return this.http.post<IBooksResource>(this.API_URL, authorRequest);
    }

    update(id: number, authorRequest: IBookRequest): Observable<IBooksResource> {
        return this.http.put<IBooksResource>(this.API_URL.concat(`/${id}`), authorRequest);
    }

    delete(id: number): Observable<void> {
        return this.http.delete<void>(this.API_URL.concat(`/${id}`));
    }
}
