import { NgModule } from '@angular/core';

import { AzmartialartsSharedLibsModule, JhiAlertComponent, JhiAlertErrorComponent } from './';

@NgModule({
    imports: [AzmartialartsSharedLibsModule],
    declarations: [JhiAlertComponent, JhiAlertErrorComponent],
    exports: [AzmartialartsSharedLibsModule, JhiAlertComponent, JhiAlertErrorComponent]
})
export class AzmartialartsSharedCommonModule {}
