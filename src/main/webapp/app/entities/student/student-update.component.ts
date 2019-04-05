import { Component, OnInit, ElementRef } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService, JhiDataUtils } from 'ng-jhipster';

import { IStudent } from 'app/shared/model/student.model';
import { StudentService } from './student.service';
import { ITeacher } from 'app/shared/model/teacher.model';
import { TeacherService } from 'app/entities/teacher';
import { IBeltLevel } from 'app/shared/model/belt-level.model';
import { BeltLevelService } from 'app/entities/belt-level';
import { IPerformance } from 'app/shared/model/performance.model';
import { PerformanceService } from 'app/entities/performance';
import { ILocation } from 'app/shared/model/location.model';
import { LocationService } from 'app/entities/location';

@Component({
    selector: 'jhi-student-update',
    templateUrl: './student-update.component.html'
})
export class StudentUpdateComponent implements OnInit {
    private _student: IStudent;
    isSaving: boolean;

    teachers: ITeacher[];

    beltlevels: IBeltLevel[];

    performances: IPerformance[];

    locations: ILocation[];

    constructor(
        private dataUtils: JhiDataUtils,
        private jhiAlertService: JhiAlertService,
        private studentService: StudentService,
        private teacherService: TeacherService,
        private beltLevelService: BeltLevelService,
        private performanceService: PerformanceService,
        private locationService: LocationService,
        private elementRef: ElementRef,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ student }) => {
            this.student = student;
        });
        this.teacherService.query().subscribe(
            (res: HttpResponse<ITeacher[]>) => {
                this.teachers = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.beltLevelService.query().subscribe(
            (res: HttpResponse<IBeltLevel[]>) => {
                this.beltlevels = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.performanceService.query().subscribe(
            (res: HttpResponse<IPerformance[]>) => {
                this.performances = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.locationService.query().subscribe(
            (res: HttpResponse<ILocation[]>) => {
                this.locations = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    byteSize(field) {
        return this.dataUtils.byteSize(field);
    }

    openFile(contentType, field) {
        return this.dataUtils.openFile(contentType, field);
    }

    setFileData(event, entity, field, isImage) {
        this.dataUtils.setFileData(event, entity, field, isImage);
    }

    clearInputImage(field: string, fieldContentType: string, idInput: string) {
        this.dataUtils.clearInputImage(this.student, this.elementRef, field, fieldContentType, idInput);
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.student.id !== undefined) {
            this.subscribeToSaveResponse(this.studentService.update(this.student));
        } else {
            this.subscribeToSaveResponse(this.studentService.create(this.student));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IStudent>>) {
        result.subscribe((res: HttpResponse<IStudent>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }

    trackTeacherById(index: number, item: ITeacher) {
        return item.id;
    }

    trackBeltLevelById(index: number, item: IBeltLevel) {
        return item.id;
    }

    trackPerformanceById(index: number, item: IPerformance) {
        return item.id;
    }

    trackLocationById(index: number, item: ILocation) {
        return item.id;
    }
    get student() {
        return this._student;
    }

    set student(student: IStudent) {
        this._student = student;
    }
}
