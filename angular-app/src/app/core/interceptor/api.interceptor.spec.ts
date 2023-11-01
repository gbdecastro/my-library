import { HTTP_INTERCEPTORS, HttpClient, HttpErrorResponse } from "@angular/common/http";
import { HttpClientTestingModule, HttpTestingController } from "@angular/common/http/testing";
import { TestBed } from "@angular/core/testing";
import { MatSnackBarModule } from "@angular/material/snack-bar";
import { TranslateLoader, TranslateModule, TranslateService } from "@ngx-translate/core";
import { catchError, of } from "rxjs";
import { ApiInterceptor } from "./api.interceptor";
import { RouterTestingModule } from "@angular/router/testing";
import { SnackBarService } from "../../layout/snack-bar/snack-bar.service";
import { TranslateFileLoader } from "../../app.module";

describe(`[U] ${ApiInterceptor.name} unit tests`, () => {
    let httpClient: HttpClient;
    let httpTestingController: HttpTestingController;
    let snackBarService: SnackBarService;
    let translateService: TranslateService;
    let interceptor: ApiInterceptor;
    const endpoint = "http://localhost:3000/api/info";

    beforeEach(() => {
        TestBed.configureTestingModule({
            imports: [
                HttpClientTestingModule,
                MatSnackBarModule,
                RouterTestingModule.withRoutes([]),
                TranslateModule.forRoot({
                    loader: {
                        provide: TranslateLoader,
                        useClass: TranslateFileLoader,
                    },
                }),
            ],
            providers: [
                SnackBarService,
                ApiInterceptor,
                {
                    provide: HTTP_INTERCEPTORS,
                    useClass: ApiInterceptor,
                    multi: true,
                },
            ],
        });

        httpClient = TestBed.inject(HttpClient);
        snackBarService = TestBed.inject(SnackBarService);
        httpTestingController = TestBed.inject(HttpTestingController);
        translateService = TestBed.inject(TranslateService);
        translateService.use("pt-br");

        interceptor = TestBed.inject(ApiInterceptor);
    });

    it("should request endpoint", () => {
        const expectedResponse = { name: "sifex", version: "v0.0.0" };

        httpClient.get(endpoint).subscribe((response) => {
            expect(response).toEqual(expectedResponse);
        });

        const httpRequest = httpTestingController.expectOne(endpoint);

        httpRequest.flush(expectedResponse);
    });

    it("should handle connection error", () => {
        const http_code = 0;

        spyOn(snackBarService, "openSnackBar").withArgs({
            message: translateService.instant("BACKEND_ERRORS.COMMON.CHECK_YOUR_CONNECTION"),
            type: "error",
        });

        httpClient
            .get(endpoint)
            .pipe(
                catchError((error: any) => {
                    expect(error.message).toEqual(
                        `Http failure response for ${endpoint}: ${http_code} `
                    );
                    expect(snackBarService.openSnackBar).toHaveBeenCalled();
                    return of(error);
                })
            )
            .subscribe();

        const httpRequest = httpTestingController.expectOne(endpoint);

        httpRequest.error(new ProgressEvent(`${http_code}`), {
            status: http_code,
        });
    });

    it("should handle internal errors", () => {
        const http_code = 500;

        spyOn(snackBarService, "openSnackBar").withArgs({
            message: "Message",
            type: "error",
        });

        httpClient
            .get(endpoint)
            .pipe(
                catchError((error: any) => {
                    expect(error.message).toEqual(
                        `Http failure response for ${endpoint}: ${http_code} `
                    );
                    expect(snackBarService.openSnackBar).toHaveBeenCalled();
                    return of(error);
                })
            )
            .subscribe();

        const httpRequest = httpTestingController.expectOne(endpoint);

        httpRequest.error(new ProgressEvent(`${http_code}`), {
            status: http_code,
        });
    });

    it("should show a common backend errors", () => {
        const errorResponse: HttpErrorResponse = new HttpErrorResponse({
            status: 400,
            error: { messageCode: "ERROR" },
        });

        spyOn(snackBarService, "openSnackBar").withArgs({
            message: "Message Error",
            type: "error",
        });

        interceptor.backendError(errorResponse);

        expect(snackBarService.openSnackBar).toHaveBeenCalled();
    });
});
