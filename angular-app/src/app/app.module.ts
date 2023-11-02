import { DEFAULT_CURRENCY_CODE, LOCALE_ID, NgModule } from "@angular/core";
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
import { NgSelectModule } from "@ng-select/ng-select";
import { registerLocaleData } from "@angular/common";
import localePt from "@angular/common/locales/pt";
import { NgxCurrencyInputMode, provideEnvironmentNgxCurrency } from "ngx-currency";

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

registerLocaleData(localePt, "pt-BR");

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
        NgSelectModule,
    ],
    providers: [
        provideEnvironmentNgxCurrency({
            align: "right",
            allowNegative: false,
            allowZero: false,
            decimal: ",",
            precision: 2,
            prefix: "R$ ",
            suffix: "",
            thousands: ".",
            nullable: true,
            min: null,
            max: null,
            inputMode: NgxCurrencyInputMode.Financial,
        }),
        {
            provide: MAT_FORM_FIELD_DEFAULT_OPTIONS,
            useValue: { appearance: "outline" },
        },
        {
            provide: LOCALE_ID,
            useValue: "pt-BR",
        },
        {
            provide: DEFAULT_CURRENCY_CODE,
            useValue: "USD",
        },
    ],
    bootstrap: [AppComponent],
})
export class AppModule {}
