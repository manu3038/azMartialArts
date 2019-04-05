import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { AzmartialartsStudentModule } from './student/student.module';
import { AzmartialartsPerformanceModule } from './performance/performance.module';
import { AzmartialartsBeltLevelModule } from './belt-level/belt-level.module';
import { AzmartialartsTeacherModule } from './teacher/teacher.module';
import { AzmartialartsLocationModule } from './location/location.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    // prettier-ignore
    imports: [
        AzmartialartsStudentModule,
        AzmartialartsPerformanceModule,
        AzmartialartsBeltLevelModule,
        AzmartialartsTeacherModule,
        AzmartialartsLocationModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AzmartialartsEntityModule {}
