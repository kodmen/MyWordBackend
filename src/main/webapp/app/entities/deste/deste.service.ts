import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IDeste } from 'app/shared/model/deste.model';

type EntityResponseType = HttpResponse<IDeste>;
type EntityArrayResponseType = HttpResponse<IDeste[]>;

@Injectable({ providedIn: 'root' })
export class DesteService {
  public resourceUrl = SERVER_API_URL + 'api/destes';

  constructor(protected http: HttpClient) {}

  create(deste: IDeste): Observable<EntityResponseType> {
    return this.http.post<IDeste>(this.resourceUrl, deste, { observe: 'response' });
  }

  update(deste: IDeste): Observable<EntityResponseType> {
    return this.http.put<IDeste>(this.resourceUrl, deste, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IDeste>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IDeste[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
