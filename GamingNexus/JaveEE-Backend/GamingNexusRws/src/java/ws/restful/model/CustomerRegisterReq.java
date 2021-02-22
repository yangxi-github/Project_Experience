/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.restful.model;

import entity.Customer;

/**
 *
 * @author chenli
 */
public class CustomerRegisterReq {
    private Customer newCustomer;

    public CustomerRegisterReq() {
    }

    public CustomerRegisterReq(Customer newCustomer) {
        this();
        this.newCustomer = newCustomer;
    }

    public Customer getNewCustomer() {
        return newCustomer;
    }

    public void setNewCustomer(Customer newCustomer) {
        this.newCustomer = newCustomer;
    }
    
    
}
