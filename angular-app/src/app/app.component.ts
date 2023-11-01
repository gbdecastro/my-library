import { Component } from "@angular/core";
import { TranslateService } from "@ngx-translate/core";
import { LANG_EN } from "@app/core/i18n/en/en";

@Component({
    selector: "app-root",
    templateUrl: "./app.component.html",
    styleUrls: ["./app.component.scss"],
})
export class AppComponent {
    title = "angular-app";

    constructor(private readonly i18n: TranslateService) {
        this.i18n.setDefaultLang(LANG_EN);
    }
}
