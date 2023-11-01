import {ComponentFixture, TestBed} from "@angular/core/testing";
import {TranslateLoader, TranslateModule, TranslateService} from "@ngx-translate/core";
import {BaseComponent} from "./base.component";
import {BrowserAnimationsModule} from "@angular/platform-browser/animations";
import {CommonModule} from "@angular/common";
import {MatButtonModule} from "@angular/material/button";
import {MatSidenavModule} from "@angular/material/sidenav";

import {HttpClientTestingModule} from "@angular/common/http/testing";
import {TranslateFileLoader} from "@app/app.module";
import {MatTooltipModule} from "@angular/material/tooltip";
import {MatTabsModule} from "@angular/material/tabs";
import {LANG_PT_BR} from "@app/core/i18n/pt-br/pt-br";
import {LANG_EN} from "@app/core/i18n/en/en";
import {RouterTestingModule} from "@angular/router/testing";

describe("[U] - BaseComponent", () => {
    let component: BaseComponent;
    let fixture: ComponentFixture<BaseComponent>;
    let translate: TranslateService;

    beforeEach(async () => {
        await TestBed.configureTestingModule({
            declarations: [BaseComponent],
            imports: [
                BrowserAnimationsModule,
                TranslateModule.forRoot({
                    loader: {
                        provide: TranslateLoader,
                        useClass: TranslateFileLoader,
                    },
                }),
                RouterTestingModule,
                CommonModule,
                MatTooltipModule,
                MatButtonModule,
                MatTabsModule,
                MatSidenavModule,
                HttpClientTestingModule,
            ],
            providers: [],
        }).compileComponents();

        translate = TestBed.inject(TranslateService);
        translate.use(LANG_PT_BR);

        fixture = TestBed.createComponent(BaseComponent);
        component = fixture.componentInstance;

        fixture.detectChanges();
    });

    it("should create", () => {
        expect(component).toBeTruthy();
    });

    it("should change locale", ()=> {

        spyOn(translate, "use").and.callThrough();

        component.onLocale(LANG_EN);

        expect(translate.use).toHaveBeenCalledWith(LANG_EN);

    })
});
