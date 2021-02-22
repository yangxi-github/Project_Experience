import { Component, OnInit } from '@angular/core';
import { NavController } from '@ionic/angular';
import { StorageService } from '../services/storage.service';
import { CommonService } from '../services/common.service';
import { CartService } from '../services/cart.service';
import { Location } from '@angular/common';

@Component({
  selector: 'app-cart',
  templateUrl: './cart.page.html',
  styleUrls: ['./cart.page.scss'],
})

export class CartPage implements OnInit {

  list: any = [];

  config: any = {};

  allPrice: any = 0;

  isCheckedAll = true;

  isEdit = false;

  hasData = false;

  constructor(public storage: StorageService, public common: CommonService,
    public cartService: CartService, public navController: NavController, public location: Location) {

    this.config = this.common.config;

  }


  ngOnInit() {
  }


  //To simulate the Line Item check and uncheck events
  checkboxChange() {

    this.isCheckAllFn();

    this.allPrice = this.cartService.getAllPrice(this.list);
  }

  //Fired when the component routing to has finished animating, so get the shopping cart data from storage
  ionViewDidEnter(): void {
    this.getCartsData();

    this.isCheckAllFn();
  }

  //Retrieve all the line items in the shopping cart
  getCartsData() {
    var cartList = this.storage.get('cartList');
    if (cartList && cartList.length > 0) {
      this.list = cartList;
      this.hasData = true;

    } else {
      this.list = [];

      this.hasData = false;
    }

    this.allPrice = this.cartService.getAllPrice(this.list);
  }


  //Decrese the quantity of the selected product by 1 in the shopping cart
  decCount(item: any) {

    if (item.product_quantity > 1) {

      item.product_quantity--
    }

    this.allPrice = this.cartService.getAllPrice(this.list);
  }

  //Increase the quantity of the selected product by 1 in the shopping cart
  incCount(item: any) {
    item.product_quantity++;

    this.allPrice = this.cartService.getAllPrice(this.list);
  }

  //To check if all line items in the shopping cart are checked/ticked
  isCheckAllFn() {

    if (this.cartService.getCheckedNum(this.list) == this.list.length) {
      this.isCheckedAll = true;
    } else {
      this.isCheckedAll = false;
    }
  }



  //To determine whether all line items in the shopping cart are checked
  checkAll() {

    console.log('CheckAll')

    if (this.isCheckedAll) {
      for (var i = 0; i < this.list.length; i++) {

        this.list[i].checked = false;
      }

      this.isCheckedAll = false;

    } else {
      for (var i = 0; i < this.list.length; i++) {

        this.list[i].checked = true;
      }
      this.isCheckedAll = true;
    }

  }

  //Delete the selected/checked line item in the shopping cart
  doDelete() {

    let noCheckedCartList = [];

    for (var i = 0; i < this.list.length; i++) {

      if (!this.list[i].checked) {
        noCheckedCartList.push(this.list[i]);
      }
    }

    // console.log(noCheckedCartList);

    this.list = noCheckedCartList;
    this.list.length > 0 ? this.hasData = true : this.hasData = false;
    this.storage.set('cartList', this.list);

  }


  //Checkout the shopping cart, saved the list into storage and redirect to payment page
  doCheckout() {

    let tempArr = [];
    for (var i = 0; i < this.list.length; i++) {
      if (this.list[i].checked) {
        tempArr.push(this.list[i]);

      }
    }

    if (tempArr.length > 0) {
      this.storage.set('checkoutProductList', tempArr);
      this.navController.navigateForward(['/checkout'], {
        queryParams: {
          returnUrl: '/cart'
        }
      });
    } else {
      alert('You have not select any product')
    }
  }

  //Fired when the component routing from is about to animate, hence save the shopping cart
  ionViewWillLeave() {
    this.storage.set('cartList', this.list);
  }

  //Redirect to the previous page
  goBack() {
    this.location.back();
  }
}


