import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { SpecialtieComponent } from '../list/specialtie.component';
import { SpecialtieDetailComponent } from '../detail/specialtie-detail.component';
import { SpecialtieUpdateComponent } from '../update/specialtie-update.component';
import { SpecialtieRoutingResolveService } from './specialtie-routing-resolve.service';

const specialtieRoute: Routes = [
  {
    path: '',
    component: SpecialtieComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: SpecialtieDetailComponent,
    resolve: {
      specialtie: SpecialtieRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: SpecialtieUpdateComponent,
    resolve: {
      specialtie: SpecialtieRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: SpecialtieUpdateComponent,
    resolve: {
      specialtie: SpecialtieRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(specialtieRoute)],
  exports: [RouterModule],
})
export class SpecialtieRoutingModule {}
