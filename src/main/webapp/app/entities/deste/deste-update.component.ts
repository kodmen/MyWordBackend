import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IDeste, Deste } from 'app/shared/model/deste.model';
import { DesteService } from './deste.service';
import { IUser } from 'app/core/user/user.model';
import { UserService } from 'app/core/user/user.service';

@Component({
  selector: 'jhi-deste-update',
  templateUrl: './deste-update.component.html',
})
export class DesteUpdateComponent implements OnInit {
  isSaving = false;
  users: IUser[] = [];

  editForm = this.fb.group({
    id: [],
    renk: [],
    name: [],
    internalUser: [],
  });

  constructor(
    protected desteService: DesteService,
    protected userService: UserService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ deste }) => {
      this.updateForm(deste);

      this.userService.query().subscribe((res: HttpResponse<IUser[]>) => (this.users = res.body || []));
    });
  }

  updateForm(deste: IDeste): void {
    this.editForm.patchValue({
      id: deste.id,
      renk: deste.renk,
      name: deste.name,
      internalUser: deste.internalUser,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const deste = this.createFromForm();
    if (deste.id !== undefined) {
      this.subscribeToSaveResponse(this.desteService.update(deste));
    } else {
      this.subscribeToSaveResponse(this.desteService.create(deste));
    }
  }

  private createFromForm(): IDeste {
    return {
      ...new Deste(),
      id: this.editForm.get(['id'])!.value,
      renk: this.editForm.get(['renk'])!.value,
      name: this.editForm.get(['name'])!.value,
      internalUser: this.editForm.get(['internalUser'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IDeste>>): void {
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

  trackById(index: number, item: IUser): any {
    return item.id;
  }
}
