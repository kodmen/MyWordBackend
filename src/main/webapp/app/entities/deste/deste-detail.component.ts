import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IDeste } from 'app/shared/model/deste.model';

@Component({
  selector: 'jhi-deste-detail',
  templateUrl: './deste-detail.component.html',
})
export class DesteDetailComponent implements OnInit {
  deste: IDeste | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ deste }) => (this.deste = deste));
  }

  previousState(): void {
    window.history.back();
  }
}
