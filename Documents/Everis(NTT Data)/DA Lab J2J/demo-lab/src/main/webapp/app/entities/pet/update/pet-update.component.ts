import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { IPet, Pet } from '../pet.model';
import { PetService } from '../service/pet.service';
import { IOwner } from 'app/entities/owner/owner.model';
import { OwnerService } from 'app/entities/owner/service/owner.service';
import { IType } from 'app/entities/type/type.model';
import { TypeService } from 'app/entities/type/service/type.service';

@Component({
  selector: 'app-pet-update',
  templateUrl: './pet-update.component.html',
})
export class PetUpdateComponent implements OnInit {
  isSaving = false;

  ownersSharedCollection: IOwner[] = [];
  typesSharedCollection: IType[] = [];

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.required, Validators.maxLength(30)]],
    birthDate: [null, [Validators.required]],
    owner: [null, Validators.required],
    type: [null, Validators.required],
  });

  constructor(
    protected petService: PetService,
    protected ownerService: OwnerService,
    protected typeService: TypeService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ pet }) => {
      this.updateForm(pet);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const pet = this.createFromForm();
    if (pet.id !== undefined) {
      this.subscribeToSaveResponse(this.petService.update(pet));
    } else {
      this.subscribeToSaveResponse(this.petService.create(pet));
    }
  }

  trackOwnerById(index: number, item: IOwner): number {
    return item.id!;
  }

  trackTypeById(index: number, item: IType): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPet>>): void {
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

  protected updateForm(pet: IPet): void {
    this.editForm.patchValue({
      id: pet.id,
      name: pet.name,
      birthDate: pet.birthDate,
      owner: pet.owner,
      type: pet.type,
    });

    this.ownersSharedCollection = this.ownerService.addOwnerToCollectionIfMissing(this.ownersSharedCollection, pet.owner);
    this.typesSharedCollection = this.typeService.addTypeToCollectionIfMissing(this.typesSharedCollection, pet.type);
  }

  protected loadRelationshipsOptions(): void {
    this.ownerService
      .query()
      .pipe(map((res: HttpResponse<IOwner[]>) => res.body ?? []))
      .pipe(map((owners: IOwner[]) => this.ownerService.addOwnerToCollectionIfMissing(owners, this.editForm.get('owner')!.value)))
      .subscribe((owners: IOwner[]) => (this.ownersSharedCollection = owners));

    this.typeService
      .query()
      .pipe(map((res: HttpResponse<IType[]>) => res.body ?? []))
      .pipe(map((types: IType[]) => this.typeService.addTypeToCollectionIfMissing(types, this.editForm.get('type')!.value)))
      .subscribe((types: IType[]) => (this.typesSharedCollection = types));
  }

  protected createFromForm(): IPet {
    return {
      ...new Pet(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      birthDate: this.editForm.get(['birthDate'])!.value,
      owner: this.editForm.get(['owner'])!.value,
      type: this.editForm.get(['type'])!.value,
    };
  }
}
