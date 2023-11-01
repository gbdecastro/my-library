import { ComponentFixture, TestBed } from "@angular/core/testing";
import { RouterModule } from "@angular/router";
import { TranslateLoader, TranslateModule, TranslateService } from "@ngx-translate/core";
import { MenuComponent } from "../menu/menu.component";
import { BaseComponent } from "./base.component";
import { BrowserAnimationsModule } from "@angular/platform-browser/animations";
import { MatExpansionModule } from "@angular/material/expansion";
import { CommonModule } from "@angular/common";
import { MatDividerModule } from "@angular/material/divider";
import { MatIconModule } from "@angular/material/icon";
import { MatButtonModule } from "@angular/material/button";
import { MatSidenavModule } from "@angular/material/sidenav";
import { NgbTooltipModule } from "@ng-bootstrap/ng-bootstrap";
import { LayoutModule as LayoutCDKModule } from "@angular/cdk/layout";
import { HttpClientTestingModule } from "@angular/common/http/testing";
import { TranslateFileLoader } from "../../app.module";

describe("[U] - BaseComponent", () => {
    let component: BaseComponent;
    let fixture: ComponentFixture<BaseComponent>;
    let translate: TranslateService;

    beforeEach(async () => {
        await TestBed.configureTestingModule({
            declarations: [BaseComponent, MenuComponent],
            imports: [
                BrowserAnimationsModule,
                TranslateModule.forRoot({
                    loader: {
                        provide: TranslateLoader,
                        useClass: TranslateFileLoader,
                    },
                }),
                RouterModule,
                MatExpansionModule,
                CommonModule,
                MatDividerModule,
                MatIconModule,
                MatButtonModule,
                MatSidenavModule,
                NgbTooltipModule,
                LayoutCDKModule,
                HttpClientTestingModule,
            ],
            providers: [],
        }).compileComponents();

        translate = TestBed.inject(TranslateService);
        translate.use("pt-br");

        fixture = TestBed.createComponent(BaseComponent);
        component = fixture.componentInstance;

        fixture.detectChanges();
    });

    it("should create", () => {
        expect(component).toBeTruthy();
    });
});
