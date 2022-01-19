import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, ActivatedRoute, Router, convertToParamMap } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { ISpecialtie, Specialtie } from '../specialtie.model';
import { SpecialtieService } from '../service/specialtie.service';

import { SpecialtieRoutingResolveService } from './specialtie-routing-resolve.service';

describe('Specialtie routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let routingResolveService: SpecialtieRoutingResolveService;
  let service: SpecialtieService;
  let resultSpecialtie: ISpecialtie | undefined;

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
    routingResolveService = TestBed.inject(SpecialtieRoutingResolveService);
    service = TestBed.inject(SpecialtieService);
    resultSpecialtie = undefined;
  });

  describe('resolve', () => {
    it('should return ISpecialtie returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultSpecialtie = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultSpecialtie).toEqual({ id: 123 });
    });

    it('should return new ISpecialtie if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultSpecialtie = result;
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultSpecialtie).toEqual(new Specialtie());
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse({ body: null as unknown as Specialtie })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultSpecialtie = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultSpecialtie).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
