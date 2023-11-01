import { ComponentFixture, TestBed } from "@angular/core/testing";

import { UpsertComponent } from "./upsert.component";
import { FormsModule, ReactiveFormsModule } from "@angular/forms";
import { MAT_DIALOG_DATA } from "@angular/material/dialog";
import { TranslateLoader, TranslateModule } from "@ngx-translate/core";
import { TranslateFileLoader } from "@app/app.module";
import { MatButtonModule } from "@angular/material/button";
import { MatInputModule } from "@angular/material/input";
import { NoopAnimationsModule } from "@angular/platform-browser/animations";

describe("[U] - Authors - UpsertComponent", () => {
    let component: UpsertComponent;
    let fixture: ComponentFixture<UpsertComponent>;

    beforeEach(() => {
        TestBed.configureTestingModule({
            declarations: [UpsertComponent],
            imports: [
                NoopAnimationsModule,
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

    it("should require the 'name' field", () => {
        const nameControl = component.name;
        nameControl.setValue("");

        expect(nameControl.valid).toBeFalsy();
        expect(nameControl.hasError("required")).toBeTruthy();
    });

    it("should limit the 'name' field to a maximum of 40 characters", () => {
        const nameControl = component.name;
        nameControl.setValue(Array.from(Array(45).keys()));

        expect(nameControl.valid).toBeFalsy();
        expect(nameControl.hasError("maxlength")).toBeTruthy();
    });
});
