import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { Performance } from 'app/shared/model/performance.model';
import { PerformanceService } from './performance.service';
import { PerformanceComponent } from './performance.component';
import { PerformanceDetailComponent } from './performance-detail.component';
import { PerformanceUpdateComponent } from './performance-update.component';
import { PerformanceDeletePopupComponent } from './performance-delete-dialog.component';
import { IPerformance } from 'app/shared/model/performance.model';

@Injectable({ providedIn: 'root' })
export class PerformanceResolve implements Resolve<IPerformance> {
    constructor(private service: PerformanceService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((performance: HttpResponse<Performance>) => performance.body));
        }
        return of(new Performance());
    }
}

export const performanceRoute: Routes = [
    {
        path: 'performance',
        component: PerformanceComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Performances'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'performance/:id/view',
        component: PerformanceDetailComponent,
        resolve: {
            performance: PerformanceResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Performances'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'performance/new',
        component: PerformanceUpdateComponent,
        resolve: {
            performance: PerformanceResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Performances'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'performance/:id/edit',
        component: PerformanceUpdateComponent,
        resolve: {
            performance: PerformanceResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Performances'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const performancePopupRoute: Routes = [
    {
        path: 'performance/:id/delete',
        component: PerformanceDeletePopupComponent,
        resolve: {
            performance: PerformanceResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Performances'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
