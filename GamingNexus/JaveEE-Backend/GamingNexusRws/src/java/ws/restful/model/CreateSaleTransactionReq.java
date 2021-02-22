/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.restful.model;

import java.util.List;
import ws.restful.helperClass.ProductAndQuantity;

/**
 *
 * @author Yang Xi
 */
public class CreateSaleTransactionReq {
    
    private String username;
    private String password;
    private List<ProductAndQuantity> productList;

    public CreateSaleTransactionReq() {
    }

    public CreateSaleTransactionReq(String username, String password, List<ProductAndQuantity> productList) {
        this.username = username;
        this.password = password;
        this.productList = productList;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<ProductAndQuantity> getProductList() {
        return productList;
    }

    public void setProductList(List<ProductAndQuantity> productList) {
        this.productList = productList;
    }
    
    

}

