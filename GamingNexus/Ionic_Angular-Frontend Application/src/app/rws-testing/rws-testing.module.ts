import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

import { IonicModule } from '@ionic/angular';

import { RwsTestingPageRoutingModule } from './rws-testing-routing.module';

import { RwsTestingPage } from './rws-testing.page';

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    IonicModule,
    RwsTestingPageRoutingModule
  ],
  declarations: [RwsTestingPage]
})
export class RwsTestingPageModule {}
