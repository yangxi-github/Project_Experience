import { Component, OnInit } from '@angular/core';
import { NavController, IonContent } from '@ionic/angular';
import { CommonService } from '../services/common.service';
import { ActivatedRoute } from '@angular/router';
import { AlertController } from '@ionic/angular';
import { Router } from '@angular/router';

@Component({
  selector: 'app-product-list',
  templateUrl: './product-list.page.html',
  styleUrls: ['./product-list.page.scss'],
})

export class ProductListPage implements OnInit {


  productList: any[] = [];
  tagId: number;
  categoryId: number;
  config: any = {};
  page: any = 1;
  subHeaderList: any[] = [];
  subHeaderSelected: any = 1;
  sort: any = 1;
  selectedTagId: number;

  constructor(public navController: NavController, public common: CommonService,
    public activatedRoute: ActivatedRoute, public alertController: AlertController,
    public router: Router) {
    this.activatedRoute.queryParams.subscribe((data) => {
      this.tagId = data.tid;
      this.categoryId = data.cid;
    })
  }

  ngOnInit() {
    this.getProductList();
  }

  getProductList() {
    if (this.tagId != null) {
      var api = "Game/filterGamesByTags?tagId=" + this.tagId;
      this.common.ajaxGet(api).then((response: any) => {
        console.log('********* DONE')
        console.log(response.games)
        this.productList = response.games;
      })
    } else if (this.categoryId != null) {
      var api = "Product/retrieveProductsByCategoryId?categoryId=" + this.categoryId;
      this.common.ajaxGet(api).then((response: any) => {
        console.log('********* DONE')
        console.log(response.products)
        this.productList = response.products;
      })
    }
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



  //Check if the tapped product has parentAdvisory. 
  //It will prompt an alert to confirm age, will only direct to the product details page when user tap confirm.
  async parentAdvisoryConfirm(productId: Number, parentAdvisory: Boolean) {

    if (parentAdvisory == true) {

      const alert = await this.alertController.create({
        header: 'WARNING!',
        message: 'THIS GAME MAY CONTAIN CONTENT NOT APPROPRIATE FOR ALL AGES. ARE YOU ABOVE AGE 21?',
        buttons: [
          {
            text: 'No',
            role: 'cancel',
            cssClass: 'secondary',
            handler: (blah) => {
              console.log('Confirm Cancel: blah');
            }
          }, {
            text: 'Yes',
            handler: () => {
              console.log(productId);
              console.log('Confirm Okay');
              this.router.navigate(['/product-details'], {
                queryParams: { id: productId }
              });
            }
          }
        ]
      });

      await alert.present();
    } else {
      this.router.navigate(['/product-details'], {
        queryParams: { id: productId }
      });
    }
  }


  goBack() {
    this.navController.back();
  }
}
