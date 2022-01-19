import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ISpecialtie, Specialtie } from '../specialtie.model';
import { SpecialtieService } from '../service/specialtie.service';

@Injectable({ providedIn: 'root' })
export class SpecialtieRoutingResolveService implements Resolve<ISpecialtie> {
  constructor(protected service: SpecialtieService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ISpecialtie> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((specialtie: HttpResponse<Specialtie>) => {
          if (specialtie.body) {
            return of(specialtie.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Specialtie());
  }
}
