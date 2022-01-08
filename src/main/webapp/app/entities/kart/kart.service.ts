import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IKart } from 'app/shared/model/kart.model';

type EntityResponseType = HttpResponse<IKart>;
type EntityArrayResponseType = HttpResponse<IKart[]>;

@Injectable({ providedIn: 'root' })
export class KartService {
  public resourceUrl = SERVER_API_URL + 'api/karts';

  constructor(protected http: HttpClient) {}

  create(kart: IKart): Observable<EntityResponseType> {
    return this.http.post<IKart>(this.resourceUrl, kart, { observe: 'response' });
  }

  update(kart: IKart): Observable<EntityResponseType> {
    return this.http.put<IKart>(this.resourceUrl, kart, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IKart>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IKart[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
