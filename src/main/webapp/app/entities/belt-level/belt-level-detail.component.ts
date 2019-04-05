import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IBeltLevel } from 'app/shared/model/belt-level.model';

@Component({
    selector: 'jhi-belt-level-detail',
    templateUrl: './belt-level-detail.component.html'
})
export class BeltLevelDetailComponent implements OnInit {
    beltLevel: IBeltLevel;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ beltLevel }) => {
            this.beltLevel = beltLevel;
        });
    }

    previousState() {
        window.history.back();
    }
}
