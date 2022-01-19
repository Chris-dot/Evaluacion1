import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IVetSpecialtie } from '../vet-specialtie.model';
import { VetSpecialtieService } from '../service/vet-specialtie.service';

@Component({
  templateUrl: './vet-specialtie-delete-dialog.component.html',
})
export class VetSpecialtieDeleteDialogComponent {
  vetSpecialtie?: IVetSpecialtie;

  constructor(protected vetSpecialtieService: VetSpecialtieService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.vetSpecialtieService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
