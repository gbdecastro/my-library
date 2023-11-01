import { Component } from "@angular/core";
import { TranslateService } from "@ngx-translate/core";

@Component({
    selector: "app-base",
    templateUrl: "./base.component.html",
    styleUrls: ["./base.component.scss"],
})
export class BaseComponent {
    readonly MENU = [
        { title: "MENU.AUTHORS", path: "authors" },
        { title: "MENU.SUBJECTS", path: "subjects" },
        { title: "MENU.BOOKS", path: "books" },
    ];

    currentLang: string;

    constructor(private readonly i18n: TranslateService) {
        this.currentLang = this.i18n.defaultLang;
        this.i18n.onLangChange.subscribe(() => {
            this.currentLang = this.i18n.currentLang;
        });
    }

    onLocale(lang: string): void {
        this.i18n.use(lang);
    }
}
