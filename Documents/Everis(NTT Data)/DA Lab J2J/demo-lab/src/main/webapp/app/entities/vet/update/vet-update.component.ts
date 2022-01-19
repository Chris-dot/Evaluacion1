import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { IVet, Vet } from '../vet.model';
import { VetService } from '../service/vet.service';

@Component({
  selector: 'app-vet-update',
  templateUrl: './vet-update.component.html',
})
export class VetUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    firstName: [null, [Validators.required, Validators.maxLength(30)]],
    lastName: [null, [Validators.required, Validators.maxLength(30)]],
  });

  constructor(protected vetService: VetService, protected activatedRoute: ActivatedRoute, protected fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ vet }) => {
      this.updateForm(vet);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const vet = this.createFromForm();
    if (vet.id !== undefined) {
      this.subscribeToSaveResponse(this.vetService.update(vet));
    } else {
      this.subscribeToSaveResponse(this.vetService.create(vet));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IVet>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe({
      next: () => this.onSaveSuccess(),
      error: () => this.onSaveError(),
    });
  }

  protected onSaveSuccess(): void {
    this.previousState();
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected updateForm(vet: IVet): void {
    this.editForm.patchValue({
      id: vet.id,
      firstName: vet.firstName,
      lastName: vet.lastName,
    });
  }

  protected createFromForm(): IVet {
    return {
      ...new Vet(),
      id: this.editForm.get(['id'])!.value,
      firstName: this.editForm.get(['firstName'])!.value,
      lastName: this.editForm.get(['lastName'])!.value,
    };
  }
}
