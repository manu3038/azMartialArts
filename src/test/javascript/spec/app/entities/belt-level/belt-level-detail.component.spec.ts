/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { AzmartialartsTestModule } from '../../../test.module';
import { BeltLevelDetailComponent } from 'app/entities/belt-level/belt-level-detail.component';
import { BeltLevel } from 'app/shared/model/belt-level.model';

describe('Component Tests', () => {
    describe('BeltLevel Management Detail Component', () => {
        let comp: BeltLevelDetailComponent;
        let fixture: ComponentFixture<BeltLevelDetailComponent>;
        const route = ({ data: of({ beltLevel: new BeltLevel(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [AzmartialartsTestModule],
                declarations: [BeltLevelDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(BeltLevelDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(BeltLevelDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.beltLevel).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
