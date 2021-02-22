
//Wrapper class for Line Items in the order 
export class ProductAndQuantity {

    productId: number;
    quantity: number;

    constructor(productId?: number, quantity?: number) {
        this.productId = productId;
        this.quantity = quantity;
    }

}
