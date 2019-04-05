import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IBeltLevel } from 'app/shared/model/belt-level.model';
import { Principal } from 'app/core';
import { BeltLevelService } from './belt-level.service';

@Component({
    selector: 'jhi-belt-level',
    templateUrl: './belt-level.component.html'
})
export class BeltLevelComponent implements OnInit, OnDestroy {
    beltLevels: IBeltLevel[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private beltLevelService: BeltLevelService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {}

    loadAll() {
        this.beltLevelService.query().subscribe(
            (res: HttpResponse<IBeltLevel[]>) => {
                this.beltLevels = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    ngOnInit() {
        this.loadAll();
        this.principal.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInBeltLevels();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IBeltLevel) {
        return item.id;
    }

    registerChangeInBeltLevels() {
        this.eventSubscriber = this.eventManager.subscribe('beltLevelListModification', response => this.loadAll());
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
