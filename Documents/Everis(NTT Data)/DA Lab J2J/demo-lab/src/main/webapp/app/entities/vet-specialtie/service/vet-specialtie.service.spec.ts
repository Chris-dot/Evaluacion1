import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IVetSpecialtie, VetSpecialtie } from '../vet-specialtie.model';

import { VetSpecialtieService } from './vet-specialtie.service';

describe('VetSpecialtie Service', () => {
  let service: VetSpecialtieService;
  let httpMock: HttpTestingController;
  let elemDefault: IVetSpecialtie;
  let expectedResult: IVetSpecialtie | IVetSpecialtie[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(VetSpecialtieService);
    httpMock = TestBed.inject(HttpTestingController);

    elemDefault = {
      id: 0,
    };
  });

  describe('Service methods', () => {
    it('should find an element', () => {
      const returnedFromService = Object.assign({}, elemDefault);

      service.find(123).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(elemDefault);
    });

    it('should create a VetSpecialtie', () => {
      const returnedFromService = Object.assign(
        {
          id: 0,
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.create(new VetSpecialtie()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a VetSpecialtie', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.update(expected).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a VetSpecialtie', () => {
      const patchObject = Object.assign({}, new VetSpecialtie());

      const returnedFromService = Object.assign(patchObject, elemDefault);

      const expected = Object.assign({}, returnedFromService);

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of VetSpecialtie', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toContainEqual(expected);
    });

    it('should delete a VetSpecialtie', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addVetSpecialtieToCollectionIfMissing', () => {
      it('should add a VetSpecialtie to an empty array', () => {
        const vetSpecialtie: IVetSpecialtie = { id: 123 };
        expectedResult = service.addVetSpecialtieToCollectionIfMissing([], vetSpecialtie);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(vetSpecialtie);
      });

      it('should not add a VetSpecialtie to an array that contains it', () => {
        const vetSpecialtie: IVetSpecialtie = { id: 123 };
        const vetSpecialtieCollection: IVetSpecialtie[] = [
          {
            ...vetSpecialtie,
          },
          { id: 456 },
        ];
        expectedResult = service.addVetSpecialtieToCollectionIfMissing(vetSpecialtieCollection, vetSpecialtie);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a VetSpecialtie to an array that doesn't contain it", () => {
        const vetSpecialtie: IVetSpecialtie = { id: 123 };
        const vetSpecialtieCollection: IVetSpecialtie[] = [{ id: 456 }];
        expectedResult = service.addVetSpecialtieToCollectionIfMissing(vetSpecialtieCollection, vetSpecialtie);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(vetSpecialtie);
      });

      it('should add only unique VetSpecialtie to an array', () => {
        const vetSpecialtieArray: IVetSpecialtie[] = [{ id: 123 }, { id: 456 }, { id: 32719 }];
        const vetSpecialtieCollection: IVetSpecialtie[] = [{ id: 123 }];
        expectedResult = service.addVetSpecialtieToCollectionIfMissing(vetSpecialtieCollection, ...vetSpecialtieArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const vetSpecialtie: IVetSpecialtie = { id: 123 };
        const vetSpecialtie2: IVetSpecialtie = { id: 456 };
        expectedResult = service.addVetSpecialtieToCollectionIfMissing([], vetSpecialtie, vetSpecialtie2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(vetSpecialtie);
        expect(expectedResult).toContain(vetSpecialtie2);
      });

      it('should accept null and undefined values', () => {
        const vetSpecialtie: IVetSpecialtie = { id: 123 };
        expectedResult = service.addVetSpecialtieToCollectionIfMissing([], null, vetSpecialtie, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(vetSpecialtie);
      });

      it('should return initial array if no VetSpecialtie is added', () => {
        const vetSpecialtieCollection: IVetSpecialtie[] = [{ id: 123 }];
        expectedResult = service.addVetSpecialtieToCollectionIfMissing(vetSpecialtieCollection, undefined, null);
        expect(expectedResult).toEqual(vetSpecialtieCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
