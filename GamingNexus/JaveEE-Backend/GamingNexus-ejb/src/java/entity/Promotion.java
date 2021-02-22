/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.sql.Date;
import java.time.LocalDateTime;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Future;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;

/**
 *
 * @author root
 */
@Entity
public class Promotion implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long promotionID;
    @Size(min = 1, max = 100)
    @NotNull
    private String name;
    @Size(min = 1, max = 3000)
    private String description;
    @Digits(integer = 2, fraction = 2)
    @Max(100)
   // @Min(1)
    private double percentageDiscount;
    @Digits(integer = 1000000000, fraction = 2)
   // @Min(1)
    private double dollarDiscount;
   // @Past
    @NotNull
    private LocalDateTime startDate;
    @NotNull
    @Future
    private LocalDateTime endDate;
    
    @ManyToMany
    private List<Product> products;

    public Promotion() {
    }

    public Promotion(String name, String description, double percentageDiscount, double dollarDiscount, LocalDateTime startDate, LocalDateTime endDate, List<Product> products) {
        this();
        this.name = name;
        this.description = description;
        this.percentageDiscount = percentageDiscount;
        this.dollarDiscount = dollarDiscount;
        this.startDate = startDate;
        this.endDate = endDate;
        this.products = products;
    }

    public Long getPromotionID() {
        return promotionID;
    }

    public void setPromotionID(Long promotionID) {
        this.promotionID = promotionID;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (promotionID != null ? promotionID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the promotionID fields are not set
        if (!(object instanceof Promotion)) {
            return false;
        }
        Promotion other = (Promotion) object;
        if ((this.promotionID == null && other.promotionID != null) || (this.promotionID != null && !this.promotionID.equals(other.promotionID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ejb.entity.Promotion[ id=" + promotionID + " ]";
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return the percentageDiscount
     */
    public double getPercentageDiscount() {
        return percentageDiscount;
    }

    /**
     * @param percentageDiscount the percentageDiscount to set
     */
    public void setPercentageDiscount(double percentageDiscount) {
        this.percentageDiscount = percentageDiscount;
    }

    /**
     * @return the dollarDiscount
     */
    public double getDollarDiscount() {
        return dollarDiscount;
    }

    /**
     * @param dollarDiscount the dollarDiscount to set
     */
    public void setDollarDiscount(double dollarDiscount) {
        this.dollarDiscount = dollarDiscount;
    }

    /**
     * @return the startDate
     */
    public LocalDateTime getStartDate() {
        return startDate;
    }

    /**
     * @param startDate the startDate to set
     */
    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    /**
     * @return the endDate
     */
    public LocalDateTime getEndDate() {
        return endDate;
    }

    /**
     * @param endDate the endDate to set
     */
    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

    /**
     * @return the products
     */
    public List<Product> getProducts() {
        return products;
    }

    /**
     * @param products the products to set
     */
    public void setProducts(List<Product> products) {
        this.products = products;
    }

}
