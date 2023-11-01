import { Component, Inject } from "@angular/core";
import { AbstractControl, FormBuilder, FormGroup, Validators } from "@angular/forms";
import { MAT_DIALOG_DATA } from "@angular/material/dialog";
import { ISubjectResponse } from "@app/core/subjects/interfaces/subject.response";

@Component({
    selector: "app-upsert",
    templateUrl: "./upsert.component.html",
    styleUrls: ["./upsert.component.scss"],
})
export class UpsertComponent {
    form: FormGroup;

    constructor(
        private readonly fb: FormBuilder,
        @Inject(MAT_DIALOG_DATA) private data: { subject: ISubjectResponse | null }
    ) {
        this.form = this.fb.group({
            description: this.fb.control(data.subject?.description, [
                Validators.required,
                Validators.maxLength(40),
            ]),
        });
    }

    get description(): AbstractControl {
        return this.form.controls["description"];
    }
}
