import { ComponentFixture, TestBed } from "@angular/core/testing";
import { MatButtonModule } from "@angular/material/button";
import { MatSnackBarModule } from "@angular/material/snack-bar";

import { SnackBarComponent } from "./snack-bar.component";
import { SnackBarService, TSnackBar } from "./snack-bar.service";
import { FeatherModule } from "angular-feather";
import { allIcons } from "angular-feather/icons";

describe("[U] - SnackBarComponent", () => {
    let component: SnackBarComponent;
    let fixture: ComponentFixture<SnackBarComponent>;
    let nativeElement: Element;
    let service: SnackBarService;
    const message = "Message";
    const types: TSnackBar[] = ["error", "info", "success", "warn"];

    beforeEach(async () => {
        await TestBed.configureTestingModule({
            declarations: [SnackBarComponent],
            imports: [FeatherModule.pick(allIcons), MatButtonModule, MatSnackBarModule],
            providers: [
                {
                    provide: SnackBarService,
                    useValue: {
                        config: {
                            message: message,
                            type: "info",
                        },
                        dismiss: (): void => {
                            return;
                        },
                    },
                },
            ],
        }).compileComponents();

        service = TestBed.inject(SnackBarService);

        fixture = TestBed.createComponent(SnackBarComponent);
        component = fixture.componentInstance;
        nativeElement = fixture.nativeElement;
        fixture.detectChanges();
    });

    it("should create", () => {
        expect(component).toBeTruthy();
        expect(nativeElement.querySelector("i-feather")).toBeTruthy();
        expect(nativeElement.querySelector(".title")).toBeTruthy();
        expect(nativeElement.querySelector("button i-feather[name='x']")).toBeTruthy();
    });

    it("should close snack bar", () => {
        const spy = spyOn(service, "dismiss").and.callThrough();

        component.onClose();
        fixture.detectChanges();

        expect(spy).toHaveBeenCalled();
    });

    types.forEach((type) => {
        it(`should have a ${type}  snackbar`, () => {
            component.snackBarType = type;
            fixture.detectChanges();

            const hostEL = nativeElement.querySelector(`.${component.getNotificationClass()[1]}`);
            expect(hostEL).toBeTruthy();
        });
    });
});
