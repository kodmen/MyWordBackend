import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { MyWordsTestModule } from '../../../test.module';
import { KartUpdateComponent } from 'app/entities/kart/kart-update.component';
import { KartService } from 'app/entities/kart/kart.service';
import { Kart } from 'app/shared/model/kart.model';

describe('Component Tests', () => {
  describe('Kart Management Update Component', () => {
    let comp: KartUpdateComponent;
    let fixture: ComponentFixture<KartUpdateComponent>;
    let service: KartService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MyWordsTestModule],
        declarations: [KartUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(KartUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(KartUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(KartService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Kart(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new Kart();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
