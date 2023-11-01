import { ComponentFixture, TestBed } from "@angular/core/testing";
import { MatProgressSpinnerModule } from "@angular/material/progress-spinner";

import { LoaderComponent } from "./loader.component";

describe("[U] - LoaderComponent", () => {
    let component: LoaderComponent;
    let fixture: ComponentFixture<LoaderComponent>;
    let nativeElement: Element;

    beforeEach(async () => {
        await TestBed.configureTestingModule({
            declarations: [LoaderComponent],
            imports: [MatProgressSpinnerModule],
        }).compileComponents();

        fixture = TestBed.createComponent(LoaderComponent);
        component = fixture.componentInstance;
        nativeElement = fixture.debugElement.nativeElement;

        fixture.detectChanges();
    });

    it("should create", () => {
        expect(component).toBeTruthy();
    });

    it("should have a spinner with sifex logo", () => {
        expect(nativeElement.querySelector(".spinner-progress-wait")).toBeTruthy();
        expect(nativeElement.querySelector("img")).toBeTruthy();
    });
});
