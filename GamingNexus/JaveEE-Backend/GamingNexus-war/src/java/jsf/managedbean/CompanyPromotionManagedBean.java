/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.managedbean;

import ejb.session.stateless.GameSessionBeanLocal;
import ejb.session.stateless.HardwareSessionBeanLocal;
import ejb.session.stateless.OtherSoftwareSessionBeanLocal;
import ejb.session.stateless.ProductSessionBeanLocal;
import ejb.session.stateless.PromotionSessionBeanLocal;
import entity.Company;
import entity.Product;
import entity.Promotion;
import javax.inject.Named;
import java.io.Serializable;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.ActionListener;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import util.exception.CompanyNotFoundException;

/**
 *
 * @author root
 */
@Named(value = "companyPromotionManagedBean")
@ViewScoped
public class CompanyPromotionManagedBean implements Serializable {

    @EJB
    private OtherSoftwareSessionBeanLocal otherSoftwareSessionBean;

    @EJB
    private HardwareSessionBeanLocal hardwareSessionBean;

    @EJB
    private GameSessionBeanLocal gameSessionBean;

    @EJB
    private ProductSessionBeanLocal productSessionBean;

    @EJB
    private PromotionSessionBeanLocal promotionSessionBean;

    @Inject
    private ViewProductManagedBean viewProductManagedBean;

    private Promotion newPromotion = null, promotionToBeUpdated = null;
    private Date today = null;
    private Date startDateToBeUpdated=null,endDateToBeUpdated=null;
    private List<Date> newDateRange = null;
    private List<Promotion> promotions, filteredPromotions;
    private List<Product> products, filteredProducts, productsToBeUpdated, promotionsProductsToBeViewed;
    private Company company;

    public CompanyPromotionManagedBean() {
        newPromotion = new Promotion();
        today = Date.from(LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant());
        newDateRange = new ArrayList<>();
        promotionsProductsToBeViewed = new ArrayList<>();
    }

    @PostConstruct
    public void postConstruct() {
        System.out.println("**************Entered postConstruct of Company Promotion Managed Bean");
        Map<String, Object> sessionMap
                = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
        setCompany((Company) sessionMap.get("company"));
        setProducts(getCompany().getProducts());
        try {
            promotions = promotionSessionBean.retrivePromotionsByCompanyID(company.getUserId());

            System.out.println("********** promotions: " + promotions.size());

        } catch (CompanyNotFoundException ex) {

        }
        if (promotions.isEmpty()) {
            System.out.println("**********promotions is empty");
        }
    }

    public void createNewPromotion(ActionEvent event) {
        newPromotion.setStartDate(newDateRange.get(0).toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());
        newPromotion.setEndDate(newDateRange.get(newDateRange.size() - 1).toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());
        Promotion promotion = promotionSessionBean.createPromotion(newPromotion);
        promotions.add(promotion);

        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_INFO, "New Game " + newPromotion.getName() + " added successfully "
                        + "(ID: " + newPromotion.getPromotionID() + ")", null));
        newPromotion = new Promotion();
        newDateRange = new ArrayList<>();
    }

    public void viewPromotionsProducts(ActionListener event){
        
    }
    
    public void doUpdatePromotion(ActionEvent event) {
        promotionToBeUpdated = (Promotion) event.getComponent().getAttributes().get("promotionToBeUpdatedFaceletAtribute");
        setProductsToBeUpdated(promotionToBeUpdated.getProducts());
        Instant instantStartdate = this.promotionToBeUpdated.getStartDate().toInstant(ZoneOffset.UTC);
        Date startDate = Date.from(instantStartdate);
        setStartDateToBeUpdated(startDate);
        setEndDateToBeUpdated(Timestamp.valueOf( promotionToBeUpdated.getEndDate()));
        
//        ChronoLocalDateTime endPointer = promotionToBeUpdated.getEndDate();
//        System.out.println("Start pointer: " + promotionToBeUpdated.getStartDate());
//        System.out.println("End Pointer: " + promotionToBeUpdated.getEndDate());
//        for (currentPointer = promotionToBeUpdated.getStartDate();
//                currentPointer.compareTo(endPointer) < 1;
//                currentPointer.plusDays(1)) {
//            System.out.println("currentPointer.compareTo: "+currentPointer.compareTo(endPointer));
//            System.out.println("    CurrentPOinter: "+currentPointer.toString());
//            Date date = Timestamp.valueOf(currentPointer);
//            dateRangeToBeUpdated.add(date);
//        }

    }

    public void updatePromotion(ActionEvent event) {
        setProductsToBeUpdated(null);
        setStartDateToBeUpdated(null);
        setEndDateToBeUpdated(null);
    }

    public void deletePromotion(ActionEvent event) {
        Promotion promotionToBeDeleted
                = (Promotion) event.getComponent().getAttributes().get("promotionToBeDeleted");
        promotionSessionBean.deletePromotion(promotionToBeDeleted.getPromotionID());
        promotions.remove(promotionToBeDeleted);
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
                "Prmotion deleted successfully. ID: " + promotionToBeDeleted.getPromotionID(), null));
    }

    /**
     * @return the company
     */
    public Company getCompany() {
        return company;
    }

    /**
     * @param company the company to set
     */
    public void setCompany(Company company) {
        this.company = company;
    }

    /**
     * @return the otherSoftwareSessionBean
     */
    public OtherSoftwareSessionBeanLocal getOtherSoftwareSessionBean() {
        return otherSoftwareSessionBean;
    }

    /**
     * @param otherSoftwareSessionBean the otherSoftwareSessionBean to set
     */
    public void setOtherSoftwareSessionBean(OtherSoftwareSessionBeanLocal otherSoftwareSessionBean) {
        this.otherSoftwareSessionBean = otherSoftwareSessionBean;
    }

    /**
     * @return the hardwareSessionBean
     */
    public HardwareSessionBeanLocal getHardwareSessionBean() {
        return hardwareSessionBean;
    }

    /**
     * @param hardwareSessionBean the hardwareSessionBean to set
     */
    public void setHardwareSessionBean(HardwareSessionBeanLocal hardwareSessionBean) {
        this.hardwareSessionBean = hardwareSessionBean;
    }

    /**
     * @return the gameSessionBean
     */
    public GameSessionBeanLocal getGameSessionBean() {
        return gameSessionBean;
    }

    /**
     * @param gameSessionBean the gameSessionBean to set
     */
    public void setGameSessionBean(GameSessionBeanLocal gameSessionBean) {
        this.gameSessionBean = gameSessionBean;
    }

    /**
     * @return the productSessionBean
     */
    public ProductSessionBeanLocal getProductSessionBean() {
        return productSessionBean;
    }

    /**
     * @param productSessionBean the productSessionBean to set
     */
    public void setProductSessionBean(ProductSessionBeanLocal productSessionBean) {
        this.productSessionBean = productSessionBean;
    }

    /**
     * @return the promotionSessionBean
     */
    public PromotionSessionBeanLocal getPromotionSessionBean() {
        return promotionSessionBean;
    }

    /**
     * @param promotionSessionBean the promotionSessionBean to set
     */
    public void setPromotionSessionBean(PromotionSessionBeanLocal promotionSessionBean) {
        this.promotionSessionBean = promotionSessionBean;
    }

    /**
     * @return the viewProductManagedBean
     */
    public ViewProductManagedBean getViewProductManagedBean() {
        return viewProductManagedBean;
    }

    /**
     * @param viewProductManagedBean the viewProductManagedBean to set
     */
    public void setViewProductManagedBean(ViewProductManagedBean viewProductManagedBean) {
        this.viewProductManagedBean = viewProductManagedBean;
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

    /**
     * @return the filteredProducts
     */
    public List<Product> getFilteredProducts() {
        return filteredProducts;
    }

    /**
     * @param filteredProducts the filteredProducts to set
     */
    public void setFilteredProducts(List<Product> filteredProducts) {
        this.filteredProducts = filteredProducts;
    }

    /**
     * @return the promotions
     */
    public List<Promotion> getPromotions() {
        return promotions;
    }

    /**
     * @param promotions the promotions to set
     */
    public void setPromotions(List<Promotion> promotions) {
        this.promotions = promotions;
    }

    /**
     * @return the filteredPromotions
     */
    public List<Promotion> getFilteredPromotions() {
        return filteredPromotions;
    }

    /**
     * @param filteredPromotions the filteredPromotions to set
     */
    public void setFilteredPromotions(List<Promotion> filteredPromotions) {
        this.filteredPromotions = filteredPromotions;
    }

    /**
     * @return the newPromotion
     */
    public Promotion getNewPromotion() {
        return newPromotion;
    }

    /**
     * @param newPromotion the newPromotion to set
     */
    public void setNewPromotion(Promotion newPromotion) {
        this.newPromotion = newPromotion;
    }

    /**
     * @return the today
     */
    public Date getToday() {
        return today;
    }

    /**
     * @param today the today to set
     */
    public void setToday(Date today) {
        this.today = today;
    }

    /**
     * @return the newDateRange
     */
    public List<Date> getNewDateRange() {
        return newDateRange;
    }

    /**
     * @param newDateRange the newDateRange to set
     */
    public void setNewDateRange(List<Date> newDateRange) {
        this.newDateRange = newDateRange;
    }


    /**
     * @return the promotionToBeUpdated
     */
    public Promotion getPromotionToBeUpdated() {
        return promotionToBeUpdated;
    }

    /**
     * @param promotionToBeUpdated the promotionToBeUpdated to set
     */
    public void setPromotionToBeUpdated(Promotion promotionToBeUpdated) {
        this.promotionToBeUpdated = promotionToBeUpdated;
    }

    /**
     * @return the productsToBeUpdated
     */
    public List<Product> getProductsToBeUpdated() {
        return productsToBeUpdated;
    }

    /**
     * @param productsToBeUpdated the productsToBeUpdated to set
     */
    public void setProductsToBeUpdated(List<Product> productsToBeUpdated) {
        this.productsToBeUpdated = productsToBeUpdated;
    }

    /**
     * @return the startDateToBeUpdated
     */
    public Date getStartDateToBeUpdated() {
        return startDateToBeUpdated;
    }

    /**
     * @param startDateToBeUpdated the startDateToBeUpdated to set
     */
    public void setStartDateToBeUpdated(Date startDateToBeUpdated) {
        this.startDateToBeUpdated = startDateToBeUpdated;
    }

    /**
     * @return the endDateToBeUpdated
     */
    public Date getEndDateToBeUpdated() {
        return endDateToBeUpdated;
    }

    /**
     * @param endDateToBeUpdated the endDateToBeUpdated to set
     */
    public void setEndDateToBeUpdated(Date endDateToBeUpdated) {
        this.endDateToBeUpdated = endDateToBeUpdated;
    }

    /**
     * @return the promotionsProductsToBeViewed
     */
    public List<Product> getPromotionsProductsToBeViewed() {
        return promotionsProductsToBeViewed;
    }

    /**
     * @param promotionsProductsToBeViewed the promotionsProductsToBeViewed to set
     */
    public void setPromotionsProductsToBeViewed(List<Product> promotionsProductsToBeViewed) {
        this.promotionsProductsToBeViewed = promotionsProductsToBeViewed;
    }


};
