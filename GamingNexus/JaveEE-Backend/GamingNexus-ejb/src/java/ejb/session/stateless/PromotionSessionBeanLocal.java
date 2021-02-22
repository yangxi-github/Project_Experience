/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.Promotion;
import java.util.List;
import javax.ejb.Local;
import util.exception.CompanyNotFoundException;

/**
 *
 * @author root
 */
@Local
public interface PromotionSessionBeanLocal {

    public Promotion createPromotion(Promotion promotion);

    public void deletePromotion(long promotionID);

    public Promotion retrievePromotionById(long promotionID);

    public List<Promotion> retrivePromotionsByCompanyID(long companyID) throws CompanyNotFoundException;

    public List<Promotion> retrieveAllPromotions();
    
}
