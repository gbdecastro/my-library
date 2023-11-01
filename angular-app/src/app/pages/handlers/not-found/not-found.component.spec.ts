import { ComponentFixture, TestBed } from "@angular/core/testing";
import { TranslateFileLoader } from "@app/app.module";
import { TranslateLoader, TranslateModule, TranslateService } from "@ngx-translate/core";

import { NotFoundComponent } from "./not-found.component";

describe("[U] - NotFoundComponent", () => {
    let component: NotFoundComponent;
    let fixture: ComponentFixture<NotFoundComponent>;
    let translate: TranslateService;
    let nativeElement: Element;

    beforeEach(async () => {
        await TestBed.configureTestingModule({
            declarations: [NotFoundComponent],
            imports: [
                TranslateModule.forRoot({
                    loader: {
                        provide: TranslateLoader,
                        useClass: TranslateFileLoader,
                    },
                }),
            ],
        }).compileComponents();

        translate = TestBed.inject(TranslateService);
        translate.use("pt-br");

        fixture = TestBed.createComponent(NotFoundComponent);
        component = fixture.componentInstance;
        nativeElement = fixture.debugElement.nativeElement;

        fixture.detectChanges();
    });

    it("should create", () => {
        expect(component).toBeTruthy();
    });

    it("should have text 'Not Found'", () => {
        const h1EL = nativeElement.querySelector("h1");
        expect(h1EL?.textContent?.trim()).toEqual(translate.instant("COMMON.NOT_FOUND"));
    });
});
