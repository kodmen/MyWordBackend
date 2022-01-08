import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IKart, Kart } from 'app/shared/model/kart.model';
import { KartService } from './kart.service';
import { KartComponent } from './kart.component';
import { KartDetailComponent } from './kart-detail.component';
import { KartUpdateComponent } from './kart-update.component';

@Injectable({ providedIn: 'root' })
export class KartResolve implements Resolve<IKart> {
  constructor(private service: KartService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IKart> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((kart: HttpResponse<Kart>) => {
          if (kart.body) {
            return of(kart.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Kart());
  }
}

export const kartRoute: Routes = [
  {
    path: '',
    component: KartComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'myWordsApp.kart.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: KartDetailComponent,
    resolve: {
      kart: KartResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'myWordsApp.kart.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: KartUpdateComponent,
    resolve: {
      kart: KartResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'myWordsApp.kart.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: KartUpdateComponent,
    resolve: {
      kart: KartResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'myWordsApp.kart.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
