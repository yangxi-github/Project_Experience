/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.restful.model;

import entity.Customer;
import java.util.List;

/**
 *
 * @author chenli
 */
public class RetrieveAllCustomersRsp {
    private List<Customer> customers;

    public RetrieveAllCustomersRsp() {
    }

    public RetrieveAllCustomersRsp(List<Customer> customers) {
        this.customers = customers;
    }

    public List<Customer> getCustomers() {
        return customers;
    }

    public void setCustomers(List<Customer> customers) {
        this.customers = customers;
    }
    
}
