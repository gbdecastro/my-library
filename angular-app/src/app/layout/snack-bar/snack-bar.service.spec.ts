import { TestBed } from "@angular/core/testing";
import { MatSnackBar, MatSnackBarModule } from "@angular/material/snack-bar";
import { SnackBarComponent } from "./snack-bar.component";
import { ISnackConfig, SnackBarService } from "./snack-bar.service";

describe("[U] - SnackBarService", () => {
    let snackBarService: SnackBarService;
    let snackBar: MatSnackBar;
    let snackBarOpenSpy: jasmine.Spy;
    let snackBarDismissSpy: jasmine.Spy;

    beforeEach(() => {
        TestBed.configureTestingModule({
            imports: [MatSnackBarModule],
            providers: [SnackBarService],
        });

        snackBarService = TestBed.inject(SnackBarService);
        snackBar = TestBed.inject(MatSnackBar);
        snackBarOpenSpy = spyOn(snackBar, "openFromComponent");
        snackBarDismissSpy = spyOn(snackBar, "dismiss");
    });

    it("should be created", () => {
        expect(snackBarService).toBeTruthy();
    });

    it("should open snackbar with the correct configuration", () => {
        const config: ISnackConfig = {
            type: "success",
            message: "Test message",
        };
        snackBarService.openSnackBar(config);

        expect(snackBarOpenSpy).toHaveBeenCalledWith(SnackBarComponent, {
            horizontalPosition: "end",
            verticalPosition: "top",
            duration: 5000,
            panelClass: `notification-${config.type}`,
        });
        expect(snackBarService.config).toEqual(config);
    });

    it("should dismiss the snackbar", () => {
        snackBarService.dismiss();

        expect(snackBarDismissSpy).toHaveBeenCalled();
    });
});
