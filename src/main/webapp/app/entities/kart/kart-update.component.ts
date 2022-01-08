import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IKart, Kart } from 'app/shared/model/kart.model';
import { KartService } from './kart.service';
import { IDeste } from 'app/shared/model/deste.model';
import { DesteService } from 'app/entities/deste/deste.service';

@Component({
  selector: 'jhi-kart-update',
  templateUrl: './kart-update.component.html',
})
export class KartUpdateComponent implements OnInit {
  isSaving = false;
  destes: IDeste[] = [];

  editForm = this.fb.group({
    id: [],
    onYuz: [],
    arkaYuz: [],
    onemSira: [],
    tekDeste: [],
  });

  constructor(
    protected kartService: KartService,
    protected desteService: DesteService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ kart }) => {
      this.updateForm(kart);

      this.desteService.query().subscribe((res: HttpResponse<IDeste[]>) => (this.destes = res.body || []));
    });
  }

  updateForm(kart: IKart): void {
    this.editForm.patchValue({
      id: kart.id,
      onYuz: kart.onYuz,
      arkaYuz: kart.arkaYuz,
      onemSira: kart.onemSira,
      tekDeste: kart.tekDeste,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const kart = this.createFromForm();
    if (kart.id !== undefined) {
      this.subscribeToSaveResponse(this.kartService.update(kart));
    } else {
      this.subscribeToSaveResponse(this.kartService.create(kart));
    }
  }

  private createFromForm(): IKart {
    return {
      ...new Kart(),
      id: this.editForm.get(['id'])!.value,
      onYuz: this.editForm.get(['onYuz'])!.value,
      arkaYuz: this.editForm.get(['arkaYuz'])!.value,
      onemSira: this.editForm.get(['onemSira'])!.value,
      tekDeste: this.editForm.get(['tekDeste'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IKart>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }

  trackById(index: number, item: IDeste): any {
    return item.id;
  }
}
