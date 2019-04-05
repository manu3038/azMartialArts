import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IPerformance } from 'app/shared/model/performance.model';
import { Principal } from 'app/core';
import { PerformanceService } from './performance.service';

@Component({
    selector: 'jhi-performance',
    templateUrl: './performance.component.html'
})
export class PerformanceComponent implements OnInit, OnDestroy {
    performances: IPerformance[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private performanceService: PerformanceService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {}

    loadAll() {
        this.performanceService.query().subscribe(
            (res: HttpResponse<IPerformance[]>) => {
                this.performances = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    ngOnInit() {
        this.loadAll();
        this.principal.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInPerformances();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IPerformance) {
        return item.id;
    }

    registerChangeInPerformances() {
        this.eventSubscriber = this.eventManager.subscribe('performanceListModification', response => this.loadAll());
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
