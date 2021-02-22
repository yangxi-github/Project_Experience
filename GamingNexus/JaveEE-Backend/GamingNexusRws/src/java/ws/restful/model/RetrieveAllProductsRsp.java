/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.restful.model;

import entity.Product;
import java.util.List;

/**
 *
 * @author jin yichen
 */
public class RetrieveAllProductsRsp {
    private List<Product> products;

    public RetrieveAllProductsRsp() {
    }

    public RetrieveAllProductsRsp(List<Product> products) {
        this.products = products;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }
    
    
}
