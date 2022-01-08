import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'deste',
        loadChildren: () => import('./deste/deste.module').then(m => m.MyWordsDesteModule),
      },
      {
        path: 'kart',
        loadChildren: () => import('./kart/kart.module').then(m => m.MyWordsKartModule),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class MyWordsEntityModule {}
