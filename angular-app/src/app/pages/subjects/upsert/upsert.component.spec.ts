import { ComponentFixture, TestBed } from "@angular/core/testing";

import { UpsertComponent } from "./upsert.component";
import { FormsModule, ReactiveFormsModule } from "@angular/forms";
import { MatInputModule } from "@angular/material/input";
import { MatButtonModule } from "@angular/material/button";
import { TranslateLoader, TranslateModule } from "@ngx-translate/core";
import { TranslateFileLoader } from "@app/app.module";
import { MAT_DIALOG_DATA } from "@angular/material/dialog";

describe("[U] - UpsertComponent", () => {
    let component: UpsertComponent;
    let fixture: ComponentFixture<UpsertComponent>;

    beforeEach(() => {
        TestBed.configureTestingModule({
            declarations: [UpsertComponent],
            imports: [
                ReactiveFormsModule,
                MatInputModule,
                FormsModule,
                MatButtonModule,
                TranslateModule.forRoot({
                    loader: {
                        provide: TranslateLoader,
                        useClass: TranslateFileLoader,
                    },
                }),
            ],
            providers: [
                {
                    provide: MAT_DIALOG_DATA,
                    useValue: { author: null },
                },
            ],
        });

        fixture = TestBed.createComponent(UpsertComponent);
        component = fixture.componentInstance;
    });

    it("should create the component", () => {
        expect(component).toBeTruthy();
    });

    it("should require the 'description' field", () => {
        const descriptionControl = component.description;
        descriptionControl.setValue("");

        expect(descriptionControl.valid).toBeFalsy();
        expect(descriptionControl.hasError("required")).toBeTruthy();
    });

    it("should limit the 'description' field to a maximum of 40 characters", () => {
        const descriptionControl = component.description;
        descriptionControl.setValue(Array.from(Array(45).keys()));

        expect(descriptionControl.valid).toBeFalsy();
        expect(descriptionControl.hasError("maxlength")).toBeTruthy();
    });
});
