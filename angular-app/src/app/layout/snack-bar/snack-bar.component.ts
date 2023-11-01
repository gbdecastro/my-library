import { Component, OnInit } from "@angular/core";
import { SnackBarService, TSnackBar } from "./snack-bar.service";

@Component({
    selector: "app-snack-bar",
    templateUrl: "./snack-bar.component.html",
    styleUrls: ["./snack-bar.component.scss"],
})
export class SnackBarComponent implements OnInit {
    message = "";
    snackBarType: TSnackBar = "info";

    constructor(private readonly snackBarService: SnackBarService) {}

    ngOnInit(): void {
        this.message = this.snackBarService.config.message;
        this.snackBarType = this.snackBarService.config.type;
    }

    onClose(): void {
        this.snackBarService.dismiss();
    }

    getNotificationClass(): string[] {
        return ["notification", "notification-" + this.snackBarType];
    }
}
