/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.restful.model;

import entity.Product;

/**
 *
 * @author Yang Xi
 */
public class RetrieveOneProductRsp {
    
    private Product product;

    public RetrieveOneProductRsp() {
    }

    public RetrieveOneProductRsp(Product product) {
        this.product = product;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
    
    
    
}
