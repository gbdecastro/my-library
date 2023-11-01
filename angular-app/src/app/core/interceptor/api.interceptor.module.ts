import { CommonModule } from "@angular/common";
import { HTTP_INTERCEPTORS } from "@angular/common/http";
import { NgModule } from "@angular/core";
import { TranslateModule } from "@ngx-translate/core";
import { ApiInterceptor } from "./api.interceptor";
import { SnackBarModule } from "../../layout/snack-bar/snack-bar.module";

@NgModule({
    imports: [CommonModule, SnackBarModule, TranslateModule],
    providers: [
        ApiInterceptor,
        {
            provide: HTTP_INTERCEPTORS,
            useClass: ApiInterceptor,
            multi: true,
        },
    ],
})
export class ApiInterceptorModule {}
