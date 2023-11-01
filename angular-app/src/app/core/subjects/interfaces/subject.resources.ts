import { IResource } from "../../shared/interfaces/hateoas";
import { ISubjectResponse } from "@app/core/subjects/interfaces/subject.response";

export interface ISubjectResource extends ISubjectResponse, IResource {}

export interface ISubjectResourceList extends IResource {
    _embedded: {
        subjectResponseList: ISubjectResponse[];
    };
}
