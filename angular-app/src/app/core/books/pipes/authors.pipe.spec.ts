import { AuthorsPipe } from "./authors.pipe";
import { IAuthorResponse } from "@app/core/authors/interfaces/authors.response";

describe("[U] - AuthorsPipe", () => {
    it("create an instance", () => {
        const pipe = new AuthorsPipe();
        expect(pipe).toBeTruthy();
    });
    it("Show return all authors", () => {
        const authors: IAuthorResponse[] = [
            { id: 1, name: "Teste" },
            { id: 2, name: "Teste 2" },
        ];

        const pipe = new AuthorsPipe();
        expect(pipe.transform(authors)).toEqual(authors.map((item) => item.name).join(", "));
    });
});
