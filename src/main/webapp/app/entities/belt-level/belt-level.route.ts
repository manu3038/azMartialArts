import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { BeltLevel } from 'app/shared/model/belt-level.model';
import { BeltLevelService } from './belt-level.service';
import { BeltLevelComponent } from './belt-level.component';
import { BeltLevelDetailComponent } from './belt-level-detail.component';
import { BeltLevelUpdateComponent } from './belt-level-update.component';
import { BeltLevelDeletePopupComponent } from './belt-level-delete-dialog.component';
import { IBeltLevel } from 'app/shared/model/belt-level.model';

@Injectable({ providedIn: 'root' })
export class BeltLevelResolve implements Resolve<IBeltLevel> {
    constructor(private service: BeltLevelService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((beltLevel: HttpResponse<BeltLevel>) => beltLevel.body));
        }
        return of(new BeltLevel());
    }
}

export const beltLevelRoute: Routes = [
    {
        path: 'belt-level',
        component: BeltLevelComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'BeltLevels'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'belt-level/:id/view',
        component: BeltLevelDetailComponent,
        resolve: {
            beltLevel: BeltLevelResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'BeltLevels'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'belt-level/new',
        component: BeltLevelUpdateComponent,
        resolve: {
            beltLevel: BeltLevelResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'BeltLevels'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'belt-level/:id/edit',
        component: BeltLevelUpdateComponent,
        resolve: {
            beltLevel: BeltLevelResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'BeltLevels'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const beltLevelPopupRoute: Routes = [
    {
        path: 'belt-level/:id/delete',
        component: BeltLevelDeletePopupComponent,
        resolve: {
            beltLevel: BeltLevelResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'BeltLevels'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
