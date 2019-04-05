import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { IBeltLevel } from 'app/shared/model/belt-level.model';
import { BeltLevelService } from './belt-level.service';

@Component({
    selector: 'jhi-belt-level-update',
    templateUrl: './belt-level-update.component.html'
})
export class BeltLevelUpdateComponent implements OnInit {
    private _beltLevel: IBeltLevel;
    isSaving: boolean;

    constructor(private beltLevelService: BeltLevelService, private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ beltLevel }) => {
            this.beltLevel = beltLevel;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.beltLevel.id !== undefined) {
            this.subscribeToSaveResponse(this.beltLevelService.update(this.beltLevel));
        } else {
            this.subscribeToSaveResponse(this.beltLevelService.create(this.beltLevel));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IBeltLevel>>) {
        result.subscribe((res: HttpResponse<IBeltLevel>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }
    get beltLevel() {
        return this._beltLevel;
    }

    set beltLevel(beltLevel: IBeltLevel) {
        this._beltLevel = beltLevel;
    }
}
