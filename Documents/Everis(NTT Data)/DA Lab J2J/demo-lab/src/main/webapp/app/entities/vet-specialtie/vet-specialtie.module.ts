import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { VetSpecialtieComponent } from './list/vet-specialtie.component';
import { VetSpecialtieDetailComponent } from './detail/vet-specialtie-detail.component';
import { VetSpecialtieUpdateComponent } from './update/vet-specialtie-update.component';
import { VetSpecialtieDeleteDialogComponent } from './delete/vet-specialtie-delete-dialog.component';
import { VetSpecialtieRoutingModule } from './route/vet-specialtie-routing.module';

@NgModule({
  imports: [SharedModule, VetSpecialtieRoutingModule],
  declarations: [VetSpecialtieComponent, VetSpecialtieDetailComponent, VetSpecialtieUpdateComponent, VetSpecialtieDeleteDialogComponent],
  entryComponents: [VetSpecialtieDeleteDialogComponent],
})
export class VetSpecialtieModule {}
