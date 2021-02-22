import { Component, OnInit } from '@angular/core';
import { CommonService } from '../services/common.service';
import { NavController, IonContent } from '@ionic/angular';
import { ActivatedRoute } from '@angular/router';


@Component({
  selector: 'app-category',
  templateUrl: './category.page.html',
  styleUrls: ['./category.page.scss'],
})

export class CategoryPage implements OnInit {

  config: any = {}

  categorylist: any[] = [];

  id: number;

  constructor(public navController: NavController, public common: CommonService, public activatedRoute: ActivatedRoute) {
    this.config = this.common.config;
    //Retrieve the parameter from the page directed from
    this.activatedRoute.queryParams.subscribe((data) => {
      this.id = data.id;
    })

  }

  //Retrieve different tags/categories list according to differnt product
  ngOnInit() {
    if (this.id == 1) {
      this.getAllGameTags();
    } else if (this.id == 2) {
      this.getAllHardwareCategories();
    } else if (this.id == 3) {
      this.getAllSoftwareCategories();
    }
  }

  //Retrieve a list of game tags
  getAllGameTags() {
    var api = "Tag/retrieveAllGameTags";
    this.common.ajaxGet(api).then((response: any) => {
      console.log('********* DONE')
      console.log(response.tags)
      this.categorylist = response.tags;
    })
  }

  //Retrieve a list of Software Categories
  getAllSoftwareCategories() {
    var api = "Category/retrieveAllSoftwareToolCategories";
    this.common.ajaxGet(api).then((response: any) => {
      console.log('********* DONE')
      console.log(response.categories)
      this.categorylist = response.categories;
    })
  }

  //Retrieve a list of Hardware Categories
  getAllHardwareCategories() {
    var api = "Category/retrieveAllHardwareCategories";
    this.common.ajaxGet(api).then((response: any) => {
      console.log('********* DONE')
      console.log(response.tags)
      this.categorylist = response.categories;
    })
  }

  //Redirect to tab2 
  goBack() {
    this.navController.navigateBack('/tabs/tab2');
  }

}
