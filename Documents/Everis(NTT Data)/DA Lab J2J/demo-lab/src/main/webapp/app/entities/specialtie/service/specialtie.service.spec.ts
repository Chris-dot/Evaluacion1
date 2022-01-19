import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { ISpecialtie, Specialtie } from '../specialtie.model';

import { SpecialtieService } from './specialtie.service';

describe('Specialtie Service', () => {
  let service: SpecialtieService;
  let httpMock: HttpTestingController;
  let elemDefault: ISpecialtie;
  let expectedResult: ISpecialtie | ISpecialtie[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(SpecialtieService);
    httpMock = TestBed.inject(HttpTestingController);

    elemDefault = {
      id: 0,
      name: 'AAAAAAA',
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

    it('should create a Specialtie', () => {
      const returnedFromService = Object.assign(
        {
          id: 0,
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.create(new Specialtie()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a Specialtie', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          name: 'BBBBBB',
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.update(expected).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a Specialtie', () => {
      const patchObject = Object.assign(
        {
          name: 'BBBBBB',
        },
        new Specialtie()
      );

      const returnedFromService = Object.assign(patchObject, elemDefault);

      const expected = Object.assign({}, returnedFromService);

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of Specialtie', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          name: 'BBBBBB',
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

    it('should delete a Specialtie', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addSpecialtieToCollectionIfMissing', () => {
      it('should add a Specialtie to an empty array', () => {
        const specialtie: ISpecialtie = { id: 123 };
        expectedResult = service.addSpecialtieToCollectionIfMissing([], specialtie);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(specialtie);
      });

      it('should not add a Specialtie to an array that contains it', () => {
        const specialtie: ISpecialtie = { id: 123 };
        const specialtieCollection: ISpecialtie[] = [
          {
            ...specialtie,
          },
          { id: 456 },
        ];
        expectedResult = service.addSpecialtieToCollectionIfMissing(specialtieCollection, specialtie);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a Specialtie to an array that doesn't contain it", () => {
        const specialtie: ISpecialtie = { id: 123 };
        const specialtieCollection: ISpecialtie[] = [{ id: 456 }];
        expectedResult = service.addSpecialtieToCollectionIfMissing(specialtieCollection, specialtie);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(specialtie);
      });

      it('should add only unique Specialtie to an array', () => {
        const specialtieArray: ISpecialtie[] = [{ id: 123 }, { id: 456 }, { id: 51496 }];
        const specialtieCollection: ISpecialtie[] = [{ id: 123 }];
        expectedResult = service.addSpecialtieToCollectionIfMissing(specialtieCollection, ...specialtieArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const specialtie: ISpecialtie = { id: 123 };
        const specialtie2: ISpecialtie = { id: 456 };
        expectedResult = service.addSpecialtieToCollectionIfMissing([], specialtie, specialtie2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(specialtie);
        expect(expectedResult).toContain(specialtie2);
      });

      it('should accept null and undefined values', () => {
        const specialtie: ISpecialtie = { id: 123 };
        expectedResult = service.addSpecialtieToCollectionIfMissing([], null, specialtie, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(specialtie);
      });

      it('should return initial array if no Specialtie is added', () => {
        const specialtieCollection: ISpecialtie[] = [{ id: 123 }];
        expectedResult = service.addSpecialtieToCollectionIfMissing(specialtieCollection, undefined, null);
        expect(expectedResult).toEqual(specialtieCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
