import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IBeltLevel } from 'app/shared/model/belt-level.model';

type EntityResponseType = HttpResponse<IBeltLevel>;
type EntityArrayResponseType = HttpResponse<IBeltLevel[]>;

@Injectable({ providedIn: 'root' })
export class BeltLevelService {
    private resourceUrl = SERVER_API_URL + 'api/belt-levels';

    constructor(private http: HttpClient) {}

    create(beltLevel: IBeltLevel): Observable<EntityResponseType> {
        return this.http.post<IBeltLevel>(this.resourceUrl, beltLevel, { observe: 'response' });
    }

    update(beltLevel: IBeltLevel): Observable<EntityResponseType> {
        return this.http.put<IBeltLevel>(this.resourceUrl, beltLevel, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IBeltLevel>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IBeltLevel[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
