import { Component } from '@angular/core';
import { NavController } from '@ionic/angular';
import { CommonService } from '../services/common.service';

@Component({
  selector: 'app-tab2',
  templateUrl: 'tab2.page.html',
  styleUrls: ['tab2.page.scss']
})
export class Tab2Page {

  flag = true;

  categories: any[] = [];

  public config: any = {}

  subCategories: any[] = [];

  tags: any[] = [];

  public selectedId: any = '';

  constructor(public navController: NavController, public common: CommonService) {
    this.config = this.common.config;
  }

  //Redirect to search page
  goToSearchPage() {
    this.navController.navigateForward('/search');
  }

}
