/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.restful.model;

/**
 *
 * @author ufoya
 */
public class CreateSaleTransactionRsp {
    private Long newSaleTransactionId;

    public CreateSaleTransactionRsp() {
    }

    public CreateSaleTransactionRsp(Long newSaleTransactionId) {
        this.newSaleTransactionId = newSaleTransactionId;
    }

    public Long getNewSaleTransactionId() {
        return newSaleTransactionId;
    }

    public void setNewSaleTransactionId(Long newSaleTransactionId) {
        this.newSaleTransactionId = newSaleTransactionId;
    }
    
    
    
}
