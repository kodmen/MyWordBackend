import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { MyWordsTestModule } from '../../../test.module';
import { KartComponent } from 'app/entities/kart/kart.component';
import { KartService } from 'app/entities/kart/kart.service';
import { Kart } from 'app/shared/model/kart.model';

describe('Component Tests', () => {
  describe('Kart Management Component', () => {
    let comp: KartComponent;
    let fixture: ComponentFixture<KartComponent>;
    let service: KartService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MyWordsTestModule],
        declarations: [KartComponent],
      })
        .overrideTemplate(KartComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(KartComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(KartService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Kart(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.karts && comp.karts[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
