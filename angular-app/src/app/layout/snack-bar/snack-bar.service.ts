import { Injectable } from "@angular/core";
import { MatSnackBar } from "@angular/material/snack-bar";
import { SnackBarComponent } from "./snack-bar.component";

export type TSnackBar = "error" | "warn" | "success" | "info";

export interface ISnackConfig {
    type: TSnackBar;
    message: string;
}

@Injectable({
    providedIn: "root",
})
export class SnackBarService {
    config!: ISnackConfig;

    constructor(private _snackbar: MatSnackBar) {}

    openSnackBar(config: ISnackConfig): void {
        this.config = config;
        this._snackbar.openFromComponent(SnackBarComponent, {
            horizontalPosition: "end",
            verticalPosition: "top",
            duration: 5000,
            panelClass: `notification-${config.type}`,
        });
    }

    dismiss(): void {
        this._snackbar.dismiss();
    }
}
