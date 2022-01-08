import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IKart } from 'app/shared/model/kart.model';

@Component({
  selector: 'jhi-kart-detail',
  templateUrl: './kart-detail.component.html',
})
export class KartDetailComponent implements OnInit {
  kart: IKart | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ kart }) => (this.kart = kart));
  }

  previousState(): void {
    window.history.back();
  }
}
