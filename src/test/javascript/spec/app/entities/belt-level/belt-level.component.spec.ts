/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { AzmartialartsTestModule } from '../../../test.module';
import { BeltLevelComponent } from 'app/entities/belt-level/belt-level.component';
import { BeltLevelService } from 'app/entities/belt-level/belt-level.service';
import { BeltLevel } from 'app/shared/model/belt-level.model';

describe('Component Tests', () => {
    describe('BeltLevel Management Component', () => {
        let comp: BeltLevelComponent;
        let fixture: ComponentFixture<BeltLevelComponent>;
        let service: BeltLevelService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [AzmartialartsTestModule],
                declarations: [BeltLevelComponent],
                providers: []
            })
                .overrideTemplate(BeltLevelComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(BeltLevelComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(BeltLevelService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new BeltLevel(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.beltLevels[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
