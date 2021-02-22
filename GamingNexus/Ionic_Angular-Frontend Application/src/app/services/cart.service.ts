import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})

export class CartService {

  constructor() { }
  //To check wether shopping cart contains any product
  hasData(cartList, currentProduct) {

    if (cartList && cartList.length > 0) {

      for (var i = 0; i < cartList.length; i++) {

        if (cartList[i].product_id == currentProduct.product_id && cartList[i].product_attrs == currentProduct.product_attrs) {

          return true;

        }
      }

      return false;
    }
    return false;
  }


  //Get number of line items in the shopping cart 
  getCartNum(cartList) {

    var sum = 0;

    if (cartList && cartList.length > 0) {
      for (var i = 0; i < cartList.length; i++) {

        sum += cartList[i].product_quantity;
      }

      return sum;
    }

    return sum;
  }



  //To calculate total price by passing in the shopping cart line item list

  getAllPrice(cartList) {
    var allPrice = 0;
    if (cartList && cartList.length > 0) {

      for (var i = 0; i < cartList.length; i++) {

        if (cartList[i].checked) {

          allPrice += cartList[i].product_quantity * cartList[i].product_price
        }

      }
    }

    return allPrice;
  }



  //Get number of selected/checked products 
  getCheckedNum(cartList) {
    var num = 0;
    for (var i = 0; i < cartList.length; i++) {

      if (cartList[i].checked) {

        num++;
      }

    }
    return num;
  }
}
