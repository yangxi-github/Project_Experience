/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.restful.model;

/**
 *
 * @author chenli
 */
public class CustomerRegisterRsp {
    private Long newCustomerId;

    public CustomerRegisterRsp() {
    }

    public CustomerRegisterRsp(Long newCustomerId) {
        this();
        this.newCustomerId = newCustomerId;
    }

    public Long getNewCustomerId() {
        return newCustomerId;
    }

    public void setNewCustomerId(Long newCustomerId) {
        this.newCustomerId = newCustomerId;
    }
    
    
}
