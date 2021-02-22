/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.Company;
import entity.Product;
import entity.Promotion;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import util.exception.CompanyNotFoundException;

/**
 *
 * @author root
 */
@Stateless
public class PromotionSessionBean implements PromotionSessionBeanLocal {

    @EJB
    private CompanySessionBeanLocal companySessionBean;

    @EJB
    private ProductSessionBeanLocal productSessionBean;

    @PersistenceContext(unitName = "GamingNexus-ejbPU")
    private EntityManager em;

    @Override
    public Promotion createPromotion(Promotion promotion) {

        em.persist(promotion);
        em.flush();

        return promotion;
    }

    @Override
    public Promotion retrievePromotionById(long promotionID) {
        Promotion retrievedPromotion = em.find(Promotion.class, promotionID);
        return retrievedPromotion;
    }

    public List<Promotion> retrieveAllPromotions() {
        Query query = em.createQuery("SELECT p FROM Promotion p ");
        List<Promotion> promotions = query.getResultList();
        
        for(Promotion p : promotions) {
            lazyLoadPromotion(p);
        }
        
        return promotions;
    }
    
    @Override
    public List<Promotion> retrivePromotionsByCompanyID(long companyID) throws CompanyNotFoundException {
        System.out.println("***********Entered Promotion Session Bean retrieve Promotions By Company ID");
        Company company = null;
        try {
            company = companySessionBean.retrieveCompanyById(companyID);
        } catch (CompanyNotFoundException ex) {
            throw ex;
        }
        List<Product> companyProducts = company.getProducts();
        Set<Promotion> retrievedPromotions = new HashSet<>();
        companyProducts.forEach(product -> {

            retrievedPromotions.addAll(product.getPromotions());
        });
        System.out.println("Company : " + company.getUsername());
        companyProducts.forEach(product -> {
            System.out.println("Product name: " + product.getName());
        });
        retrievedPromotions.forEach(promotion -> {
            System.out.println("Promotion Name: " + promotion.getName());
        });
        return new ArrayList<>(retrievedPromotions);
    }

    @Override
    public void deletePromotion(long promotionID) {
        Promotion promotionToBeDeleted = this.retrievePromotionById(promotionID);
        em.remove(promotionToBeDeleted);
        em.flush();
    }

    public void lazyLoadPromotion(Promotion promotion) {
        promotion.getProducts().size();
    }
}
