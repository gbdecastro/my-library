import { ComponentFixture, TestBed } from "@angular/core/testing";
import { MatProgressSpinnerModule } from "@angular/material/progress-spinner";

import { LoaderComponent } from "./loader.component";
import { FeatherModule } from "angular-feather";
import { allIcons } from "angular-feather/icons";

describe("[U] - LoaderComponent", () => {
    let component: LoaderComponent;
    let fixture: ComponentFixture<LoaderComponent>;

    beforeEach(async () => {
        await TestBed.configureTestingModule({
            declarations: [LoaderComponent],
            imports: [MatProgressSpinnerModule, FeatherModule.pick(allIcons)],
        }).compileComponents();

        fixture = TestBed.createComponent(LoaderComponent);
        component = fixture.componentInstance;

        fixture.detectChanges();
    });

    it("should create", () => {
        expect(component).toBeTruthy();
    });

    it("should change img size", () => {
        component.diameter = 50;
        fixture.detectChanges();
        const imgSize = component.imgSize;
        expect(imgSize).toEqual(25);
    });
});
