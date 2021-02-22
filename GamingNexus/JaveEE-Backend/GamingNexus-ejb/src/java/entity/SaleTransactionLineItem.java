/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 *
 * @author Jin Yichen 
 */
@Entity
public class SaleTransactionLineItem implements Serializable {

    private static long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long saleTransactionLineItemId;
            
    @ManyToOne(optional = false)
    @JoinColumn(nullable = false)
    private Product product;
    
    //@Column(nullable = false)
    //@NotNull
    //@Min(1)
    private Integer quantity;
    //@Column(nullable = false, precision = 11, scale = 2)
    //@NotNull
    //@DecimalMin("0.00")
    //@Digits(integer = 9, fraction = 2)
    private BigDecimal unitPrice;
    //@Column(nullable = false, precision = 11, scale = 2)
    //@NotNull
    //@DecimalMin("0.00")
    //@Digits(integer = 9, fraction = 2)
    private BigDecimal subTotal;

    public SaleTransactionLineItem() {
    }

    public SaleTransactionLineItem(Product product, Integer quantity, BigDecimal unitPrice, BigDecimal subTotal) {
        this.product = product;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
        this.subTotal = subTotal;
    }
    

    public Long getSaleTransactionLineItemId() {
        return saleTransactionLineItemId;
    }

    public void setSaleTransactionLineItemId(Long saleTransactionLineItemId) {
        this.saleTransactionLineItemId = saleTransactionLineItemId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (saleTransactionLineItemId != null ? saleTransactionLineItemId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the saleTransactionLineItemId fields are not set
        if (!(object instanceof SaleTransactionLineItem)) {
            return false;
        }
        SaleTransactionLineItem other = (SaleTransactionLineItem) object;
        if ((this.saleTransactionLineItemId == null && other.saleTransactionLineItemId != null) || (this.saleTransactionLineItemId != null && !this.saleTransactionLineItemId.equals(other.saleTransactionLineItemId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.SaleTransactionLineItem[ id=" + saleTransactionLineItemId + " ]";
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public static void setSerialVersionUID(long aSerialVersionUID) {
        serialVersionUID = aSerialVersionUID;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    public BigDecimal getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(BigDecimal subTotal) {
        this.subTotal = subTotal;
    }
    
}
