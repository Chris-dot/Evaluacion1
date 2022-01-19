import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { SpecialtieComponent } from './list/specialtie.component';
import { SpecialtieDetailComponent } from './detail/specialtie-detail.component';
import { SpecialtieUpdateComponent } from './update/specialtie-update.component';
import { SpecialtieDeleteDialogComponent } from './delete/specialtie-delete-dialog.component';
import { SpecialtieRoutingModule } from './route/specialtie-routing.module';

@NgModule({
  imports: [SharedModule, SpecialtieRoutingModule],
  declarations: [SpecialtieComponent, SpecialtieDetailComponent, SpecialtieUpdateComponent, SpecialtieDeleteDialogComponent],
  entryComponents: [SpecialtieDeleteDialogComponent],
})
export class SpecialtieModule {}
