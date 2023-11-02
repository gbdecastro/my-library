import { Injectable } from "@angular/core";
import { environment } from "../../../../environments/environment";
import { HttpClient } from "@angular/common/http";
import { map, Observable } from "rxjs";
import { ISubjectRequest } from "../interfaces/subject.request";
import {
    ISubjectResource,
    ISubjectResourceList,
} from "@app/core/subjects/interfaces/subject.resources";
import { ISubjectResponse } from "@app/core/subjects/interfaces/subject.response";

@Injectable({
    providedIn: "root",
})
export class SubjectService {
    private API_URL = `${environment.API_URL}/subjects`;

    constructor(private http: HttpClient) {}

    getAll(): Observable<ISubjectResponse[]> {
        return this.http.get<ISubjectResourceList>(this.API_URL).pipe(
            map((response) => {
                if (response._embedded?.subjectResponseList) {
                    return response._embedded.subjectResponseList as ISubjectResponse[];
                } else {
                    return [];
                }
            })
        );
    }

    getById(id: number): Observable<ISubjectResource> {
        return this.http.get<ISubjectResource>(`${this.API_URL}/${id}`);
    }

    create(subjectRequest: ISubjectRequest): Observable<ISubjectResource> {
        return this.http.post<ISubjectResource>(this.API_URL, subjectRequest);
    }

    update(id: number, subjectRequest: ISubjectRequest): Observable<ISubjectResource> {
        return this.http.put<ISubjectResource>(this.API_URL.concat(`/${id}`), subjectRequest);
    }

    delete(id: number): Observable<void> {
        return this.http.delete<void>(this.API_URL.concat(`/${id}`));
    }
}
