import { Component, OnInit } from '@angular/core';

import { NavController } from '@ionic/angular'
import { CommonService } from '../services/common.service';
import { StorageService } from '../services/storage.service';

@Component({
  selector: 'app-search',
  templateUrl: './search.page.html',
  styleUrls: ['./search.page.scss'],
})
export class SearchPage implements OnInit {

  flag = true;
  keywords: any = '';
  productList = [];
  historyList: any[] = [];  //SearchHistoryRecord
  sort: any = 1;

  constructor(public navController: NavController, public storage: StorageService, public common: CommonService) {

  }


  //Retrieve a list of history search record
  ngOnInit() {
    this.getHistoryList();
  }


  goBack() {
    this.navController.back();
  }


  //Search the keyword by sending it to the backend to invoke the sessionbean method
  doSearch() {
    this.saveHistory();
    this.flag = false;
    var api = "Product/searchProductsByName?name=" + this.keywords;
    this.common.ajaxGet(api).then((response: any) => {
      console.log('********* DONE')
      console.log(response.products)
      this.productList = response.products;
    })
  }


  //Search the keyword passed in
  goSearch(keywords) {
    this.keywords = keywords;
    this.doSearch();
  }


  //Sort the products by price in the current page. Both low-high and high-to-low.
  sortProductListByPrice() {
    if (this.sort == 1) {
      this.productList.sort((a, b) => (a.price > b.price) ? 1 : -1);
      this.sort = -1;
    } else if (this.sort == -1) {
      this.productList.sort((a, b) => (a.price < b.price) ? 1 : -1);
      this.sort = 1;
    }
  }


  //Sort the products by release date from most recent in the current page.
  sortProductListByReleaseDate() {
    this.productList.sort((a, b) => (Date.parse(a.releaseDate) > Date.parse(a.releaseDate)) ? 1 : -1);
  }


  // Sort the products by sales from high to low in the current page.
  sortProductListBySales() {

    this.productList.sort((a, b) => (a.sales < b.sales) ? 1 : -1);
  }


  //Save search records into local storage.
  saveHistory() {

    var historyList = this.storage.get('historylist');

    //If historyList exists
    if (historyList) {

      if (historyList.indexOf(this.keywords) == -1) {

        historyList.push(this.keywords);
      }

      this.storage.set('historylist', historyList);

      //If historyList does not exist
    } else {

      historyList = [];
      historyList.push(this.keywords);
      this.storage.set('historylist', historyList);

    }

  }

  //To retrieve a list of history search records
  getHistoryList() {
    var historyList = this.storage.get('historylist');
    if (historyList) {
      this.historyList = historyList;
    }
  }


  //Remove the passed in item from the search history list
  removeHistory(item) {

    const index: number = this.historyList.indexOf(item);
    if (index != 1) {
      this.historyList.splice(index, 1);
    }

    this.storage.set('historylist', this.historyList);
  }



}
