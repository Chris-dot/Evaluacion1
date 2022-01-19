import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, ActivatedRoute, Router, convertToParamMap } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { IVetSpecialtie, VetSpecialtie } from '../vet-specialtie.model';
import { VetSpecialtieService } from '../service/vet-specialtie.service';

import { VetSpecialtieRoutingResolveService } from './vet-specialtie-routing-resolve.service';

describe('VetSpecialtie routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let routingResolveService: VetSpecialtieRoutingResolveService;
  let service: VetSpecialtieService;
  let resultVetSpecialtie: IVetSpecialtie | undefined;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: {
            snapshot: {
              paramMap: convertToParamMap({}),
            },
          },
        },
      ],
    });
    mockRouter = TestBed.inject(Router);
    jest.spyOn(mockRouter, 'navigate').mockImplementation(() => Promise.resolve(true));
    mockActivatedRouteSnapshot = TestBed.inject(ActivatedRoute).snapshot;
    routingResolveService = TestBed.inject(VetSpecialtieRoutingResolveService);
    service = TestBed.inject(VetSpecialtieService);
    resultVetSpecialtie = undefined;
  });

  describe('resolve', () => {
    it('should return IVetSpecialtie returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultVetSpecialtie = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultVetSpecialtie).toEqual({ id: 123 });
    });

    it('should return new IVetSpecialtie if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultVetSpecialtie = result;
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultVetSpecialtie).toEqual(new VetSpecialtie());
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse({ body: null as unknown as VetSpecialtie })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultVetSpecialtie = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultVetSpecialtie).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
