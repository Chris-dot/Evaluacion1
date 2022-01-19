import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { ISpecialtie } from '../specialtie.model';
import { SpecialtieService } from '../service/specialtie.service';

@Component({
  templateUrl: './specialtie-delete-dialog.component.html',
})
export class SpecialtieDeleteDialogComponent {
  specialtie?: ISpecialtie;

  constructor(protected specialtieService: SpecialtieService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.specialtieService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
