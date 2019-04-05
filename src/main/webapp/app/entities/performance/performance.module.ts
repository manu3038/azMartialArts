import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { AzmartialartsSharedModule } from 'app/shared';
import {
    PerformanceComponent,
    PerformanceDetailComponent,
    PerformanceUpdateComponent,
    PerformanceDeletePopupComponent,
    PerformanceDeleteDialogComponent,
    performanceRoute,
    performancePopupRoute
} from './';

const ENTITY_STATES = [...performanceRoute, ...performancePopupRoute];

@NgModule({
    imports: [AzmartialartsSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        PerformanceComponent,
        PerformanceDetailComponent,
        PerformanceUpdateComponent,
        PerformanceDeleteDialogComponent,
        PerformanceDeletePopupComponent
    ],
    entryComponents: [PerformanceComponent, PerformanceUpdateComponent, PerformanceDeleteDialogComponent, PerformanceDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AzmartialartsPerformanceModule {}
