import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { AzmartialartsSharedModule } from 'app/shared';
import {
    StudentComponent,
    StudentDetailComponent,
    StudentUpdateComponent,
    StudentDeletePopupComponent,
    StudentDeleteDialogComponent,
    studentRoute,
    studentPopupRoute
} from './';

import { FormsModule, ReactiveFormsModule } from '@angular/forms';

const ENTITY_STATES = [...studentRoute, ...studentPopupRoute];

@NgModule({
    imports: [AzmartialartsSharedModule, RouterModule.forChild(ENTITY_STATES), FormsModule, ReactiveFormsModule],
    declarations: [
        StudentComponent,
        StudentDetailComponent,
        StudentUpdateComponent,
        StudentDeleteDialogComponent,
        StudentDeletePopupComponent
    ],
    entryComponents: [StudentComponent, StudentUpdateComponent, StudentDeleteDialogComponent, StudentDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AzmartialartsStudentModule {}
