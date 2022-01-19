import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { PetService } from '../service/pet.service';
import { IPet, Pet } from '../pet.model';
import { IOwner } from 'app/entities/owner/owner.model';
import { OwnerService } from 'app/entities/owner/service/owner.service';
import { IType } from 'app/entities/type/type.model';
import { TypeService } from 'app/entities/type/service/type.service';

import { PetUpdateComponent } from './pet-update.component';

describe('Pet Management Update Component', () => {
  let comp: PetUpdateComponent;
  let fixture: ComponentFixture<PetUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let petService: PetService;
  let ownerService: OwnerService;
  let typeService: TypeService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [PetUpdateComponent],
      providers: [
        FormBuilder,
        {
          provide: ActivatedRoute,
          useValue: {
            params: from([{}]),
          },
        },
      ],
    })
      .overrideTemplate(PetUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(PetUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    petService = TestBed.inject(PetService);
    ownerService = TestBed.inject(OwnerService);
    typeService = TestBed.inject(TypeService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Owner query and add missing value', () => {
      const pet: IPet = { id: 456 };
      const owner: IOwner = { id: 79982 };
      pet.owner = owner;

      const ownerCollection: IOwner[] = [{ id: 14362 }];
      jest.spyOn(ownerService, 'query').mockReturnValue(of(new HttpResponse({ body: ownerCollection })));
      const additionalOwners = [owner];
      const expectedCollection: IOwner[] = [...additionalOwners, ...ownerCollection];
      jest.spyOn(ownerService, 'addOwnerToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ pet });
      comp.ngOnInit();

      expect(ownerService.query).toHaveBeenCalled();
      expect(ownerService.addOwnerToCollectionIfMissing).toHaveBeenCalledWith(ownerCollection, ...additionalOwners);
      expect(comp.ownersSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Type query and add missing value', () => {
      const pet: IPet = { id: 456 };
      const type: IType = { id: 99385 };
      pet.type = type;

      const typeCollection: IType[] = [{ id: 78495 }];
      jest.spyOn(typeService, 'query').mockReturnValue(of(new HttpResponse({ body: typeCollection })));
      const additionalTypes = [type];
      const expectedCollection: IType[] = [...additionalTypes, ...typeCollection];
      jest.spyOn(typeService, 'addTypeToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ pet });
      comp.ngOnInit();

      expect(typeService.query).toHaveBeenCalled();
      expect(typeService.addTypeToCollectionIfMissing).toHaveBeenCalledWith(typeCollection, ...additionalTypes);
      expect(comp.typesSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const pet: IPet = { id: 456 };
      const owner: IOwner = { id: 68173 };
      pet.owner = owner;
      const type: IType = { id: 61340 };
      pet.type = type;

      activatedRoute.data = of({ pet });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(pet));
      expect(comp.ownersSharedCollection).toContain(owner);
      expect(comp.typesSharedCollection).toContain(type);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Pet>>();
      const pet = { id: 123 };
      jest.spyOn(petService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ pet });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: pet }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(petService.update).toHaveBeenCalledWith(pet);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Pet>>();
      const pet = new Pet();
      jest.spyOn(petService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ pet });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: pet }));
      saveSubject.complete();

      // THEN
      expect(petService.create).toHaveBeenCalledWith(pet);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Pet>>();
      const pet = { id: 123 };
      jest.spyOn(petService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ pet });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(petService.update).toHaveBeenCalledWith(pet);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Tracking relationships identifiers', () => {
    describe('trackOwnerById', () => {
      it('Should return tracked Owner primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackOwnerById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });

    describe('trackTypeById', () => {
      it('Should return tracked Type primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackTypeById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });
  });
});
