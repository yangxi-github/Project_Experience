/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.Rating;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import util.exception.RatingNotFoundException;

/**
 *
 * @author 63216
 */
@Stateless
public class RatingSessionBean implements RatingSessionBeanLocal {

    @PersistenceContext(unitName = "GamingNexus-ejbPU")
    private EntityManager em;

    public RatingSessionBean() {
    }

    @Override
    public Rating createRating(Rating rating) {
        em.persist(rating);
        em.flush();
        return rating;
    }
    
    @Override
    public List<Rating> retrieveAllRatings(){
        Query query = em.createQuery("SELECT * FROM Rating r");
        List<Rating> ratings = query.getResultList();
        
        return ratings;
    }
    
    @Override
    public Rating retrieveRatingByRatingId(Long ratingId) throws RatingNotFoundException{
        Rating rating = em.find(Rating.class,ratingId);
        if (rating != null) {
            return rating;
        } else {
            throw new RatingNotFoundException("Rating " + ratingId + " does not exist!");
        }
    }
    
    @Override
    public List<Rating> retrieveRatingByProductId(Long productId){
        Query query = em.createQuery("SELECT r FROM Rating r WHERE r.product.productId = :inProductId");
        query.setParameter("inProductId", productId);
        List<Rating> ratings = query.getResultList();

        return ratings;
    }
    
    @Override
    public List<Rating> retrieveRatingByProductName(String productName){
        Query query = em.createQuery("SELECT r FROM Rating r WHERE r.product.name = :inProductName");
        query.setParameter("inProductName", productName);
        List<Rating> ratings = query.getResultList();

        return ratings;
    }
    
    @Override
    public List<Rating> retrieveRatingByCustomerId(Long customerId){
        Query query = em.createQuery("SELECT r FROM Rating r WHERE r.customer.userId = :inCustomerId");
        query.setParameter("inCustomerId", customerId);
        List<Rating> ratings = query.getResultList();

        return ratings;
    }
    
    @Override
    public List<Rating> retrieveRatingByCustomerName(String customerName){
        Query query = em.createQuery("SELECT r FROM Rating r WHERE r.customer.name = :inCustomerName");
        query.setParameter("inCustomerName", customerName);
        List<Rating> ratings = query.getResultList();

        return ratings;
    }
    
    @Override
    public void updateRating(Rating rating) throws RatingNotFoundException {
        if (rating != null && rating.getCustomer()!= null) {

            Rating ratingToUpdate = retrieveRatingByRatingId(rating.getRatingId());
            
            ratingToUpdate.setRating(rating.getRating());
            ratingToUpdate.setReview(rating.getReview());
            ratingToUpdate.setDateOfReview(rating.getDateOfReview());
            
        } else {
            throw new RatingNotFoundException("Rating Not Found");
        }
    }
    
    
    
    
    @Override
    public void deleteRating(Long ratingId) throws RatingNotFoundException{
        Rating ratingToRemove = retrieveRatingByRatingId(ratingId);
        em.remove(ratingToRemove);
    }
}
