import { Component, Inject } from "@angular/core";
import { AbstractControl, FormBuilder, FormGroup, Validators } from "@angular/forms";
import { MAT_DIALOG_DATA } from "@angular/material/dialog";
import { IAuthorResponse } from "@app/core/authors/interfaces/authors.response";

@Component({
    selector: "app-upsert",
    templateUrl: "./upsert.component.html",
    styleUrls: ["./upsert.component.scss"],
})
export class UpsertComponent {
    form: FormGroup;

    constructor(
        private readonly fb: FormBuilder,
        @Inject(MAT_DIALOG_DATA) private data: { author: IAuthorResponse | null }
    ) {
        this.form = this.fb.group({
            name: this.fb.control(data.author?.name, [
                Validators.required,
                Validators.maxLength(40),
            ]),
        });
    }

    get name(): AbstractControl {
        return this.form.controls["name"];
    }
}
