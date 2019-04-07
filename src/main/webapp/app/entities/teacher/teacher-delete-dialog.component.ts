import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ITeacher } from 'app/shared/model/teacher.model';
import { TeacherService } from './teacher.service';
import { StudentService } from 'app/entities/student/student.service';
import { HttpResponse } from '@angular/common/http';

@Component({
    selector: 'jhi-teacher-delete-dialog',
    templateUrl: './teacher-delete-dialog.component.html'
})
export class TeacherDeleteDialogComponent {
    teacher: ITeacher;
    canDelete: boolean;

    constructor(private teacherService: TeacherService, public activeModal: NgbActiveModal, private eventManager: JhiEventManager,private studentService: StudentService,) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.studentService.teacherDelete(id).subscribe(
            (res:HttpResponse<any>) =>{ 
                if(res.body.length == 0){
                    this.teacherService.delete(id).subscribe(response => {
                        this.eventManager.broadcast({
                            name: 'teacherListModification',
                            content: 'Deleted an teacher'
                        });
                        this.activeModal.dismiss(true);
                    });
                }else{
                   this.canDelete= true;
                }
            }
        );
    }
}

@Component({
    selector: 'jhi-teacher-delete-popup',
    template: ''
})
export class TeacherDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal,private studentService: StudentService,) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ teacher }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(TeacherDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.teacher = teacher;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate([{ outlets: { popup: null } }], { replaceUrl: true, queryParamsHandling: 'merge' });
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate([{ outlets: { popup: null } }], { replaceUrl: true, queryParamsHandling: 'merge' });
                        this.ngbModalRef = null;
                    }
                );
            }, 0);
        });
    }

    ngOnDestroy() {
        this.ngbModalRef = null;
    }
}
