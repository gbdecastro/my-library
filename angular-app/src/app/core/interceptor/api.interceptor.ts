import {
    HttpErrorResponse,
    HttpEvent,
    HttpHandler,
    HttpInterceptor,
    HttpRequest,
} from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Router } from "@angular/router";
import { TranslateService } from "@ngx-translate/core";
import { catchError, Observable, throwError } from "rxjs";
import { SnackBarService } from "../../layout/snack-bar/snack-bar.service";

@Injectable()
export class ApiInterceptor implements HttpInterceptor {
    constructor(
        private readonly snackBarService: SnackBarService,
        private readonly i18nService: TranslateService,
        protected readonly router: Router
    ) {}

    intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
        return next.handle(request).pipe(
            catchError((error: HttpErrorResponse) => {
                const notAuthorized = [403, 401];

                if (notAuthorized.includes(error.status)) {
                    // skip
                } else if (error.status === 0) {
                    this.connectionError();
                } else if (error.status == 400) {
                    this.backendError(error);
                } else {
                    this.unknownError();
                }

                return throwError(() => error);
            })
        );
    }

    connectionError(): void {
        this.snackBarService.openSnackBar({
            message: this.i18nService.instant("COMMON.CHECK_YOUR_CONNECTION"),
            type: "error",
        });
    }

    backendError(error: HttpErrorResponse): void {
        this.snackBarService.openSnackBar({
            message: error.error?.message,
            type: "error",
        });
    }

    unknownError(): void {
        this.snackBarService.openSnackBar({
            message: this.i18nService.instant("COMMON.UNKNOWN_ERROR"),
            type: "error",
        });
    }
}
