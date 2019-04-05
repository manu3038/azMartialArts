/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { AzmartialartsTestModule } from '../../../test.module';
import { BeltLevelUpdateComponent } from 'app/entities/belt-level/belt-level-update.component';
import { BeltLevelService } from 'app/entities/belt-level/belt-level.service';
import { BeltLevel } from 'app/shared/model/belt-level.model';

describe('Component Tests', () => {
    describe('BeltLevel Management Update Component', () => {
        let comp: BeltLevelUpdateComponent;
        let fixture: ComponentFixture<BeltLevelUpdateComponent>;
        let service: BeltLevelService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [AzmartialartsTestModule],
                declarations: [BeltLevelUpdateComponent]
            })
                .overrideTemplate(BeltLevelUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(BeltLevelUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(BeltLevelService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new BeltLevel(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.beltLevel = entity;
                    // WHEN
                    comp.save();
                    tick(); // simulate async

                    // THEN
                    expect(service.update).toHaveBeenCalledWith(entity);
                    expect(comp.isSaving).toEqual(false);
                })
            );

            it(
                'Should call create service on save for new entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new BeltLevel();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.beltLevel = entity;
                    // WHEN
                    comp.save();
                    tick(); // simulate async

                    // THEN
                    expect(service.create).toHaveBeenCalledWith(entity);
                    expect(comp.isSaving).toEqual(false);
                })
            );
        });
    });
});
