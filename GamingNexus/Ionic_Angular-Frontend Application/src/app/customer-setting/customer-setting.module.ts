import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

import { IonicModule } from '@ionic/angular';

import { CustomerSettingPageRoutingModule } from './customer-setting-routing.module';

import { CustomerSettingPage } from './customer-setting.page';

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    IonicModule,
    CustomerSettingPageRoutingModule
  ],
  declarations: [CustomerSettingPage]
})
export class CustomerSettingPageModule {}
