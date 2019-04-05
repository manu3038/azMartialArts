/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { AzmartialartsTestModule } from '../../../test.module';
import { BeltLevelDeleteDialogComponent } from 'app/entities/belt-level/belt-level-delete-dialog.component';
import { BeltLevelService } from 'app/entities/belt-level/belt-level.service';

describe('Component Tests', () => {
    describe('BeltLevel Management Delete Component', () => {
        let comp: BeltLevelDeleteDialogComponent;
        let fixture: ComponentFixture<BeltLevelDeleteDialogComponent>;
        let service: BeltLevelService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [AzmartialartsTestModule],
                declarations: [BeltLevelDeleteDialogComponent]
            })
                .overrideTemplate(BeltLevelDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(BeltLevelDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(BeltLevelService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('confirmDelete', () => {
            it(
                'Should call delete service on confirmDelete',
                inject(
                    [],
                    fakeAsync(() => {
                        // GIVEN
                        spyOn(service, 'delete').and.returnValue(of({}));

                        // WHEN
                        comp.confirmDelete(123);
                        tick();

                        // THEN
                        expect(service.delete).toHaveBeenCalledWith(123);
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });
});
