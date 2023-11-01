import { SubjectsPipe } from "./subjects.pipe";
import { ISubjectResponse } from "@app/core/subjects/interfaces/subject.response";

describe("[U] - SubjectsPipe", () => {
    it("create an instance", () => {
        const pipe = new SubjectsPipe();
        expect(pipe).toBeTruthy();
    });

    it("Show return all subjects", () => {
        const authors: ISubjectResponse[] = [
            { id: 1, description: "Teste" },
            { id: 2, description: "Teste 2" },
        ];

        const pipe = new SubjectsPipe();
        expect(pipe.transform(authors)).toEqual(authors.map((item) => item.description).join(", "));
    });
});
