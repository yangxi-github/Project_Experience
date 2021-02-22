/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.restful.model;

import entity.Product;
import entity.SaleTransaction;
import java.util.List;

/**
 *
 * @author jin yichen
 */
public class RetrieveAllSaleTransactionByUsernameAndPasswordRsp {
    private List<SaleTransaction> saleTransactions;

    public RetrieveAllSaleTransactionByUsernameAndPasswordRsp() {
    }
    
    public RetrieveAllSaleTransactionByUsernameAndPasswordRsp(List<SaleTransaction> saleTransactions) {
        this.saleTransactions = saleTransactions;
    }

    public List<SaleTransaction> getSaleTransactions() {
        return saleTransactions;
    }

    public void setSaleTransactions(List<SaleTransaction> saleTransactions) {
        this.saleTransactions = saleTransactions;
    }

    

    
}
