import { ComponentFixture, TestBed } from "@angular/core/testing";
import { RouterTestingModule } from "@angular/router/testing";
import { AppComponent } from "./app.component";
import { TranslateLoader, TranslateModule, TranslateService } from "@ngx-translate/core";
import { TranslateFileLoader } from "@app/app.module";
import { LANG_PT_BR } from "@app/core/i18n/pt-br/pt-br";
import { NgSelectModule } from "@ng-select/ng-select";

describe("[U] - AppComponent", () => {
    let component: AppComponent;
    let fixture: ComponentFixture<AppComponent>;
    let translate: TranslateService;

    beforeEach(() => {
        TestBed.configureTestingModule({
            imports: [
                RouterTestingModule,
                TranslateModule.forRoot({
                    loader: {
                        provide: TranslateLoader,
                        useClass: TranslateFileLoader,
                    },
                }),
                NgSelectModule,
            ],
            declarations: [AppComponent],
        });

        translate = TestBed.inject(TranslateService);
        translate.use(LANG_PT_BR);

        fixture = TestBed.createComponent(AppComponent);
        component = fixture.componentInstance;
    });

    it("should create the app", () => {
        expect(component).toBeTruthy();
        expect(component).toBeTruthy();
    });
});
