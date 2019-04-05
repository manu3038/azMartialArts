import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IBeltLevel } from 'app/shared/model/belt-level.model';
import { BeltLevelService } from './belt-level.service';

@Component({
    selector: 'jhi-belt-level-delete-dialog',
    templateUrl: './belt-level-delete-dialog.component.html'
})
export class BeltLevelDeleteDialogComponent {
    beltLevel: IBeltLevel;

    constructor(private beltLevelService: BeltLevelService, public activeModal: NgbActiveModal, private eventManager: JhiEventManager) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.beltLevelService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'beltLevelListModification',
                content: 'Deleted an beltLevel'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-belt-level-delete-popup',
    template: ''
})
export class BeltLevelDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ beltLevel }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(BeltLevelDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.beltLevel = beltLevel;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate([{ outlets: { popup: null } }], { replaceUrl: true, queryParamsHandling: 'merge' });
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate([{ outlets: { popup: null } }], { replaceUrl: true, queryParamsHandling: 'merge' });
                        this.ngbModalRef = null;
                    }
                );
            }, 0);
        });
    }

    ngOnDestroy() {
        this.ngbModalRef = null;
    }
}
