import { ComponentFixture, TestBed } from "@angular/core/testing";

import { ConfirmationDialogComponent } from "./confirmation-dialog.component";
import { MAT_DIALOG_DATA, MatDialogModule } from "@angular/material/dialog";
import { TranslateLoader, TranslateModule } from "@ngx-translate/core";
import { TranslateFileLoader } from "@app/app.module";
import { MatButtonModule } from "@angular/material/button";

describe("[U] - ConfirmationDialogComponent", () => {
    let component: ConfirmationDialogComponent;
    let fixture: ComponentFixture<ConfirmationDialogComponent>;

    beforeEach(() => {
        TestBed.configureTestingModule({
            imports: [
                MatDialogModule,
                MatButtonModule,
                TranslateModule.forRoot({
                    loader: {
                        provide: TranslateLoader,
                        useClass: TranslateFileLoader,
                    },
                }),
            ],
            declarations: [ConfirmationDialogComponent],
            providers: [
                {
                    provide: MAT_DIALOG_DATA,
                    useValue: { title: "Test Title", message: "Test Message" },
                },
            ],
        });

        fixture = TestBed.createComponent(ConfirmationDialogComponent);
        component = fixture.componentInstance;
        fixture.detectChanges();
    });

    it("should create the ConfirmationDialogComponent", () => {
        expect(component).toBeTruthy();
        expect(component.data.title).toEqual("Test Title");
        expect(component.data.message).toEqual("Test Message");
    });
});
