import { Component, Input } from "@angular/core";

@Component({
    selector: "app-loader",
    templateUrl: "./loader.component.html",
    styleUrls: ["./loader.component.scss"],
})
export class LoaderComponent {
    @Input() diameter = 50;
    @Input() showIcon = true;

    get imgSize(): number {
        return this.diameter - 25;
    }
}
