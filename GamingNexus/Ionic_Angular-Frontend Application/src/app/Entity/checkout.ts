export class Checkout {

    username: string;
    password: string;
    productList: any[] = [];

    constructor(username?: string, password?: string, productList?: any[]) {
        this.username = username;
        this.password = password;
        this.productList = productList;
    }

}
