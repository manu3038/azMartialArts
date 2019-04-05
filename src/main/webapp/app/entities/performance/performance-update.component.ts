import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { IPerformance } from 'app/shared/model/performance.model';
import { PerformanceService } from './performance.service';

@Component({
    selector: 'jhi-performance-update',
    templateUrl: './performance-update.component.html'
})
export class PerformanceUpdateComponent implements OnInit {
    private _performance: IPerformance;
    isSaving: boolean;

    constructor(private performanceService: PerformanceService, private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ performance }) => {
            this.performance = performance;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.performance.id !== undefined) {
            this.subscribeToSaveResponse(this.performanceService.update(this.performance));
        } else {
            this.subscribeToSaveResponse(this.performanceService.create(this.performance));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IPerformance>>) {
        result.subscribe((res: HttpResponse<IPerformance>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }
    get performance() {
        return this._performance;
    }

    set performance(performance: IPerformance) {
        this._performance = performance;
    }
}
