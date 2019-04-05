import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { AzmartialartsSharedModule } from 'app/shared';
import {
    BeltLevelComponent,
    BeltLevelDetailComponent,
    BeltLevelUpdateComponent,
    BeltLevelDeletePopupComponent,
    BeltLevelDeleteDialogComponent,
    beltLevelRoute,
    beltLevelPopupRoute
} from './';

const ENTITY_STATES = [...beltLevelRoute, ...beltLevelPopupRoute];

@NgModule({
    imports: [AzmartialartsSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        BeltLevelComponent,
        BeltLevelDetailComponent,
        BeltLevelUpdateComponent,
        BeltLevelDeleteDialogComponent,
        BeltLevelDeletePopupComponent
    ],
    entryComponents: [BeltLevelComponent, BeltLevelUpdateComponent, BeltLevelDeleteDialogComponent, BeltLevelDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AzmartialartsBeltLevelModule {}
