import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { MyWordsSharedModule } from 'app/shared/shared.module';
import { KartComponent } from './kart.component';
import { KartDetailComponent } from './kart-detail.component';
import { KartUpdateComponent } from './kart-update.component';
import { KartDeleteDialogComponent } from './kart-delete-dialog.component';
import { kartRoute } from './kart.route';

@NgModule({
  imports: [MyWordsSharedModule, RouterModule.forChild(kartRoute)],
  declarations: [KartComponent, KartDetailComponent, KartUpdateComponent, KartDeleteDialogComponent],
  entryComponents: [KartDeleteDialogComponent],
})
export class MyWordsKartModule {}
