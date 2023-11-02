import { ISubjectRequest } from "@app/core/subjects/interfaces/subject.request";
import {
    ISubjectResource,
    ISubjectResourceList,
} from "@app/core/subjects/interfaces/subject.resources";
import { ISubjectResponse } from "@app/core/subjects/interfaces/subject.response";

export const SUBJECT_REQUEST: ISubjectRequest = {
    description: "",
};

export const SUBJECT_RESOURCE: ISubjectResource = {
    _links: {},
    id: 50,
    description: "Subject 1",
};

export const SUBJECT_RESPONSE: ISubjectResponse = {
    id: 50,
    description: "Subject 1",
};

export const SUBJECTS: ISubjectResourceList = {
    _embedded: {
        subjectResponseList: [SUBJECT_RESPONSE],
    },
    _links: {},
};
