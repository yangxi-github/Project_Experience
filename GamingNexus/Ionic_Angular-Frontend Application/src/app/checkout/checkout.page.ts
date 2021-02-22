import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { NavController } from '@ionic/angular';
import { StorageService } from '../services/storage.service';
import { CommonService } from '../services/common.service';
import { CartService } from '../services/cart.service';
import { SessionService } from '../services/session.service';
import { ProductAndQuantity } from '../Entity/product-and-quantity';
import { Checkout } from '../Entity/checkout';
import { CheckoutService } from "../services/checkout.service";


@Component({
  selector: 'app-checkout',
  templateUrl: './checkout.page.html',
  styleUrls: ['./checkout.page.scss'],
})
export class CheckoutPage implements OnInit {

  productList: any[] = [];
  orderProductList: any[] = [];
  config: any = {};
  returnUrl = '';
  username: string;
  password: string;
  login: boolean;
  totalPrice = 0;
  Checkout;
  baseUrl: string;
  infoMessage: string;
  errorMessage: string;

  constructor(public activatedRoute: ActivatedRoute, public navController: NavController,
    public storage: StorageService, public common: CommonService, public cart: CartService,
    public sessionService: SessionService, public checkoutService: CheckoutService) {
    this.baseUrl = "http://localhost:8080/GamingNexusRws/Resources/SaleTransaction/createSaleTransaction"
  }

  ngOnInit() {
    this.login = this.sessionService.getIsLogin();
    this.username = this.sessionService.getUsername();
    this.password = this.sessionService.getPassword();
    this.productList = this.storage.get('checkoutProductList');
    this.activatedRoute.queryParams.subscribe((data: any) => {
      data.returnUrl ? this.returnUrl = data.returnUrl : this.returnUrl = '/tabs/tab3';
    })
    this.totalPrice = this.cart.getAllPrice(this.productList);
  }


  goBack() {

    this.navController.navigateBack('/tabs/tab3');
  }


  //Create an sales transaction of all the selected line items in the shopping cart
  doOrder() {
    this.orderProductList = [];

    //the product is checked, add it into the orderList by using a ProductAndQuantity wrapper class so that RWS can use it more efficiently
    for (var i = 0; i < this.productList.length; i++) {
      if (this.productList[i].checked) {
        this.orderProductList.push(new ProductAndQuantity(this.productList[i].product_id, this.productList[i].product_quantity))
      }
    }

    //Wrap it as a Checkout class containing the customer username, password and orderList
    this.Checkout = new Checkout(this.username, this.password, this.orderProductList);

    //subscribe to the RWS
    this.checkoutService.createSaleTransacntion(this.Checkout).subscribe(
      response => {
        this.infoMessage = 'New Sales Transaction Created ' + response.newBookId;
        this.errorMessage = null;
        this.storage.set('cartList', null);
      },
      error => {
        this.infoMessage = null;
        this.errorMessage = error;
      }
    );
    this.navController.navigateBack('/order-complete');
  }



}
