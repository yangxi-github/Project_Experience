/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.Rating;
import java.util.List;
import javax.ejb.Local;
import util.exception.RatingNotFoundException;

/**
 *
 * @author 63216
 */
@Local
public interface RatingSessionBeanLocal {

    public Rating createRating(Rating rating);

    public List<Rating> retrieveRatingByProductId(Long productId);

    public List<Rating> retrieveAllRatings();

    public List<Rating> retrieveRatingByProductName(String productName);

    public List<Rating> retrieveRatingByCustomerId(Long customerId);

    public List<Rating> retrieveRatingByCustomerName(String customerName);

    public Rating retrieveRatingByRatingId(Long ratingId) throws RatingNotFoundException;

    public void deleteRating(Long ratingId) throws RatingNotFoundException;

    public void updateRating(Rating rating) throws RatingNotFoundException;
    
}
