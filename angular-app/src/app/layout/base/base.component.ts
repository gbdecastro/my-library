import { Component } from "@angular/core";
import { TranslateService } from "@ngx-translate/core";
import { LANG_PT_BR } from "@app/core/i18n/pt-br/pt-br";
import { LANG_EN } from "@app/core/i18n/en/en";

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

    readonly pt = LANG_PT_BR;
    readonly en = LANG_EN;

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
