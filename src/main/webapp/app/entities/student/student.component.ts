import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpHeaders, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiParseLinks, JhiAlertService, JhiDataUtils } from 'ng-jhipster';

import { IStudent } from 'app/shared/model/student.model';
import { Principal } from 'app/core';

import { ITEMS_PER_PAGE } from 'app/shared';
import { StudentService } from './student.service';

import { LocationService } from 'app/entities/location';
import { ILocation } from 'app/shared/model/location.model';

import { IBeltLevel } from 'app/shared/model/belt-level.model';
import { BeltLevelService } from 'app/entities/belt-level';
import { FormGroup, FormBuilder } from '@angular/forms';

@Component({
    selector: 'jhi-student',
    templateUrl: './student.component.html'
})
export class StudentComponent implements OnInit, OnDestroy {
    students: IStudent[];
    currentAccount: any;
    eventSubscriber: Subscription;
    itemsPerPage: number;
    links: any;
    page: any;
    predicate: any;
    queryCount: any;
    reverse: any;
    totalItems: number;
    locations: ILocation[];
    beltlevels: IBeltLevel[];
    searchFilterForm: FormGroup;

    constructor(
        private studentService: StudentService,
        private jhiAlertService: JhiAlertService,
        private dataUtils: JhiDataUtils,
        private eventManager: JhiEventManager,
        private parseLinks: JhiParseLinks,
        private principal: Principal,
        private fb: FormBuilder,
        private beltLevelService: BeltLevelService,
        private locationService: LocationService
    ) {
        this.students = [];
        this.itemsPerPage = ITEMS_PER_PAGE;
        this.page = 0;
        this.links = {
            last: 0
        };
        this.predicate = 'id';
        this.reverse = true;
    }

    loadAll() {
        this.studentService
            .query({
                page: this.page,
                size: this.itemsPerPage,
                sort: this.sort()
            })
            .subscribe(
                (res: HttpResponse<IStudent[]>) => this.paginateStudents(res.body, res.headers),
                (res: HttpErrorResponse) => this.onError(res.message)
            );
    }

    reset() {
        this.page = 0;
        this.students = [];
        this.loadAll();
    }

    loadPage(page) {
        this.page = page;
        this.loadAll();
    }

    ngOnInit() {
        this.loadAll();
        this.principal.identity().then(account => {
            this.currentAccount = account;
        });
        this.locationService.query().subscribe(
            (res: HttpResponse<ILocation[]>) => {
                this.locations = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.beltLevelService.query().subscribe(
            (res: HttpResponse<IBeltLevel[]>) => {
                this.beltlevels = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.searchFilterForm = this.fb.group({
            locationSearch: [],
            beltSearch: []
        });
        this.registerChangeInStudents();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IStudent) {
        return item.id;
    }

    byteSize(field) {
        return this.dataUtils.byteSize(field);
    }

    openFile(contentType, field) {
        return this.dataUtils.openFile(contentType, field);
    }

    registerChangeInStudents() {
        this.eventSubscriber = this.eventManager.subscribe('studentListModification', response => this.reset());
    }

    sort() {
        const result = [this.predicate + ',' + (this.reverse ? 'asc' : 'desc')];
        if (this.predicate !== 'id') {
            result.push('id');
        }
        return result;
    }

    onSearchSubmit(searchObject: object) {
        console.log(this.searchFilterForm.getRawValue());
        const copy = this.searchFilterForm.getRawValue();
        this.studentService
            .searchStudents(copy)
            .subscribe(
                (res: HttpResponse<IStudent[]>) => this.paginateStudents(res.body, res.headers),
                (res: HttpErrorResponse) => this.onError(res.message)
            );
    }

    private paginateStudents(data: IStudent[], headers: HttpHeaders) {
        this.links = this.parseLinks.parse(headers.get('link'));
        this.totalItems = parseInt(headers.get('X-Total-Count'), 10);
        for (let i = 0; i < data.length; i++) {
            this.students.push(data[i]);
        }
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
