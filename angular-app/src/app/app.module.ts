import { NgModule } from "@angular/core";
import { BrowserModule } from "@angular/platform-browser";

import { AppRoutingModule } from "./app-routing.module";
import { AppComponent } from "./app.component";
import { BrowserAnimationsModule } from "@angular/platform-browser/animations";
import { NgbModule } from "@ng-bootstrap/ng-bootstrap";
import { TranslateLoader, TranslateModule } from "@ngx-translate/core";
import { Observable, of } from "rxjs";
import { I18N_PTBR, LANG_PT_BR } from "./core/i18n/pt-br/pt-br";
import { I18N_EN } from "./core/i18n/en/en";
import { MAT_FORM_FIELD_DEFAULT_OPTIONS } from "@angular/material/form-field";
import { HttpClientModule } from "@angular/common/http";
import { ApiInterceptorModule } from "@app/core/interceptor/api.interceptor.module";

export class TranslateFileLoader implements TranslateLoader {
    getTranslation(lang: string): Observable<any> {
        switch (lang) {
            case LANG_PT_BR:
                return of(I18N_PTBR);
            default:
                return of(I18N_EN);
        }
    }
}

@NgModule({
    declarations: [AppComponent],
    imports: [
        BrowserModule,
        AppRoutingModule,
        BrowserAnimationsModule,
        NgbModule,
        TranslateModule.forRoot({
            loader: {
                provide: TranslateLoader,
                useClass: TranslateFileLoader,
            },
        }),
        HttpClientModule,
        ApiInterceptorModule,
    ],
    providers: [
        {
            provide: MAT_FORM_FIELD_DEFAULT_OPTIONS,
            useValue: { appearance: "outline" },
        },
    ],
    bootstrap: [AppComponent],
})
export class AppModule {}
