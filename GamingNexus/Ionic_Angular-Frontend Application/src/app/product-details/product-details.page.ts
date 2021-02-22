import { Component, OnInit } from '@angular/core';
import { CommonService } from '../services/common.service';
import { ActivatedRoute } from '@angular/router';
import { CartService } from '../services/cart.service';
import { StorageService } from '../services/storage.service';

@Component({
  selector: 'app-product-details',
  templateUrl: './product-details.page.html',
  styleUrls: ['./product-details.page.scss'],
})

export class ProductDetailsPage implements OnInit {
  tab: any = "list";
  productId: Number;
  list: any = {};
  config: any = {};
  quantity: any = 1;
  cartNum: any = 0;

  constructor(public common: CommonService, public activatedRoute: ActivatedRoute,
    public cartService: CartService, public storage: StorageService) {
    this.config = this.common.config;
  }


  //Get the shopping cart list from storage
  ngOnInit() {
    var cartList = this.storage.get('cartList');
    if (cartList) {
      this.cartNum = this.cartService.getCartNum(cartList);
    }
    this.activatedRoute.queryParams.subscribe((res) => {
      this.getProductData(res.id);
      console.log(res);
      console.log(this.list);
    })
  }

  //To retrieve the selected product information by passing in the product ID from backend.
  getProductData(id) {

    var api = 'Product/retrieveProductById?productId=' + id;
    this.common.ajaxGet(api).then((response: any) => {
      console.log('********* DONE')
      this.list = response.product;
      console.log(this.list);
    })

  }

  //Increse the quantity by 1 of product to be added in to shopping cart
  incQuantity() {
    this.quantity += 1;
  }

  //Decrease the quantity by 1 of product to be added in to shopping cart
  decQuantity() {

    if (this.quantity > 1) {
      this.quantity -= 1;
    }

  }


  //Add the product into the shopping cart
  addCart() {

    var product_title = this.list.name;
    var product_id = this.list.productId;
    var product_pic = this.list.headerImage;
    var product_price = this.list.price;
    var product_quantity = this.quantity;

    var productJson = {
      product_title,
      product_id,
      product_pic,
      product_price,
      product_quantity,
      checked: true
    }
    console.log(productJson);


    var cartList = this.storage.get('cartList');

    console.log(cartList);
    if (cartList && cartList.length > 0) {

      if (this.cartService.hasData(cartList, productJson)) {

        for (var i = 0; i < cartList.length; i++) {

          if (cartList[i].product_id == productJson.product_id) {

            cartList[i].product_quantity += productJson.product_quantity;
          }
        }
      }
      else {
        cartList.push(productJson);
      }
      this.storage.set('cartList', cartList);

    } else {
      var tempArr: any[] = [];
      tempArr.push(productJson);
      this.storage.set('cartList', tempArr);
    }

    this.cartNum += productJson.product_quantity;

  }


}
