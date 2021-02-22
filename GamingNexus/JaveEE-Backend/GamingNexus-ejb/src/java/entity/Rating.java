/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.sql.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;

/**
 *
 * @author jin yichen
 */
@Entity
public class Rating implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ratingId;
    @NotNull
    @Min(0)
    @Max(5)
    @Digits(integer = 1, fraction = 0)
    private int rating;
    @NotNull
    @Size(min = 0, max = 5000)
    private String Review;
    @NotNull
    @Past
    private Date dateOfReview;
    
    @ManyToOne
    private Product product;
    @ManyToOne
    @NotNull
    private Customer customer;

    public Rating() {
    }

    public Rating(int rating, String Review, Date dateOfReview, Product product,Customer customer) {
        this();
        this.rating = rating;
        this.Review = Review;
        this.dateOfReview = dateOfReview;
        this.product = product;
        this.customer = customer;
    }

    public Long getRatingId() {
        return ratingId;
    }

    public void setRatingId(Long ratingId) {
        this.ratingId = ratingId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (ratingId != null ? ratingId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the ratingId fields are not set
        if (!(object instanceof Rating)) {
            return false;
        }
        Rating other = (Rating) object;
        if ((this.ratingId == null && other.ratingId != null) || (this.ratingId != null && !this.ratingId.equals(other.ratingId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ejb.entity.Rating[ id=" + ratingId + " ]";
    }

    /**
     * @return the rating
     */
    public int getRating() {
        return rating;
    }

    /**
     * @param rating the rating to set
     */
    public void setRating(int rating) {
        this.rating = rating;
    }

    /**
     * @return the Review
     */
    public String getReview() {
        return Review;
    }

    /**
     * @param Review the Review to set
     */
    public void setReview(String Review) {
        this.Review = Review;
    }

    /**
     * @return the dateOfReview
     */
    public Date getDateOfReview() {
        return dateOfReview;
    }

    /**
     * @param dateOfReview the dateOfReview to set
     */
    public void setDateOfReview(Date dateOfReview) {
        this.dateOfReview = dateOfReview;
    }

    /**
     * @return the product
     */
    public Product getProduct() {
        return product;
    }

    /**
     * @param product the product to set
     */
    public void setProduct(Product product) {
        this.product = product;
    }

    /**
     * @return the customer
     */
    public Customer getCustomer() {
        return customer;
    }

    /**
     * @param customer the customer to set
     */
    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

}
