/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.managedbean;

import ejb.session.stateless.CategorySessionBeanLocal;
import ejb.session.stateless.GameSessionBeanLocal;
import ejb.session.stateless.HardwareSessionBeanLocal;
import ejb.session.stateless.OtherSoftwareSessionBeanLocal;
import ejb.session.stateless.ProductSessionBeanLocal;
import ejb.session.stateless.TagSessionBeanLocal;
import entity.Category;
import entity.Company;
import entity.Game;
import entity.Hardware;
import entity.OtherSoftware;
import entity.Product;
import entity.Tag;
import java.io.IOException;
import javax.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import util.exception.CategoryNotFoundException;
import util.exception.CompanyNotFoundException;
import util.exception.CreateNewProductException;
import util.exception.InputDataValidationException;
import util.exception.ProductNotFoundException;
import util.exception.ProductSkuCodeExistException;
import util.exception.SystemAdminUsernameExistException;
import util.exception.TagNotFoundException;
import util.exception.UnknownPersistenceException;
import util.exception.UpdateProductException;

/**
 *
 * @author root
 */
@Named(value = "companyProductManagedBean")
@ViewScoped
public class CompanyProductManagedBean implements Serializable {

    @EJB
    private HardwareSessionBeanLocal hardwareSessionBean;

    @EJB
    private OtherSoftwareSessionBeanLocal otherSoftwareSessionBean;

    @EJB
    private ProductSessionBeanLocal productSessionBean;

    @EJB
    private GameSessionBeanLocal gameSessionBean;

    @EJB
    private CategorySessionBeanLocal categorySessionBean;

    @EJB
    private TagSessionBeanLocal tagSessionBean;

    @Inject
    private ViewProductManagedBean viewProductManagedBean;
    
    private Game newGame, gameToBeUpdated, gameToViewInDetails = null;
    private Product productToViewInDetails, selectedProductToUpdate;
    private Hardware newHardware, hardwareToViewInDetails = null;
    private OtherSoftware newOtherSoftware, otherSoftwareToViewInDetails = null;
    private List<Product> products, filteredProducts;
    private List<Category> categories;
    private List<Tag> tags;
    private Company company;
    private Long categoryIdUpdate;
    private List<Long> tagIdsUpdate;

    public CompanyProductManagedBean() {
        newGame = new Game();
        gameToBeUpdated = new Game();
    }

    @PostConstruct
    public void postConstruct() {
        Map<String, Object> sessionMap = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
        setCompany((Company) sessionMap.get("company"));

        products = getCompany().getProducts();
        newOtherSoftware = new OtherSoftware();
        newOtherSoftware.setTags(new ArrayList<>());
        setNewHardware(new Hardware());
        getNewHardware().setTags(new ArrayList<>());
        categories = categorySessionBean.retrieveAllCategories();
        tags = tagSessionBean.retrieveAllTags();
    }

    public void viewProductDetailsMethod(ActionEvent event) throws IOException {
        Long productIdToView = (Long) event.getComponent().getAttributes().get("productId");
        FacesContext.getCurrentInstance().getExternalContext().getFlash().put("productIdToView", productIdToView);

        //   FacesContext.getCurrentInstance().getExternalContext().redirect("viewProductDetails.xhtml");
    }

    public void createNewProduct(ActionEvent event) throws SystemAdminUsernameExistException {

        String buttonID = event.getComponent().getId();

        switch (buttonID) {
            case "AddGameButton":
                try {
                    List<Long> tagIds = new ArrayList<>();
                    newGame.getTags().forEach(tag -> {
                        tagIds.add(tag.getTagId());
                    });
                    Game game = gameSessionBean.createNewGame(newGame, newGame.getCategory().getCategoryId(), tagIds, getCompany().getUserId());
                    products.add((Product) game);

                    FacesContext.getCurrentInstance().addMessage(null,
                            new FacesMessage(FacesMessage.SEVERITY_INFO, "New Game " + newGame.getName() + " added successfully "
                                    + "(ID: " + game.getProductId() + ")", null));
                    newGame = new Game();

                } catch (UnknownPersistenceException | ProductSkuCodeExistException | InputDataValidationException | CreateNewProductException
                        | CompanyNotFoundException ex) {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                            "An error has occurred while creating a new game " + ex.getMessage(), null));
                }
                break;
            case "AddOtherSoftwareButton":
                try {
                    List<Long> tagIds = new ArrayList<>();
                    getNewOtherSoftware().getTags().forEach(tag -> {
                        tagIds.add(tag.getTagId());
                    });
                    OtherSoftware otherSoftware = otherSoftwareSessionBean.createNewOtherSoftware(newOtherSoftware,
                            newOtherSoftware.getCategory().getCategoryId(), tagIds, getCompany().getUserId());
                    products.add((Product) otherSoftware);

                    FacesContext.getCurrentInstance().addMessage(null,
                            new FacesMessage(FacesMessage.SEVERITY_INFO, "New Game " + newGame.getName() + " added successfully "
                                    + "(ID: " + otherSoftware.getProductId() + ")", null));
                    newOtherSoftware = new OtherSoftware();

                } catch (UnknownPersistenceException | ProductSkuCodeExistException | InputDataValidationException | CreateNewProductException
                        | CompanyNotFoundException ex) {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                            "An error has occurred while creating a new other software  " + ex.getMessage(), null));
                }
                break;
            case "AddHardwareButton":
                try {
                    List<Long> tagIds = new ArrayList<>();
                    getNewHardware().getTags().forEach(tag -> {
                        tagIds.add(tag.getTagId());
                    });
                    Hardware hardware = hardwareSessionBean.createNewHardware(newHardware,
                            newHardware.getCategory().getCategoryId(), tagIds, getCompany().getUserId());
                    products.add((Product) hardware);

                    FacesContext.getCurrentInstance().addMessage(null,
                            new FacesMessage(FacesMessage.SEVERITY_INFO, "New Game " + newGame.getName() + " added successfully "
                                    + "(ID: " + newHardware.getProductId() + ")", null));
                    newHardware = new Hardware();

                } catch (UnknownPersistenceException | ProductSkuCodeExistException | InputDataValidationException | CreateNewProductException
                        | CompanyNotFoundException ex) {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                            "An error has occurred while creating a new other software  " + ex.getMessage(), null));
                }
                break;
            default:
                break;

        }

    }
//
//    public void doUpdateProduct(ActionEvent event) {
//        // setSelectedProductToUpdate((Product)event.getComponent().getAttributes().get("productEntityToUpdate"));
//
//        setCategoryIdUpdate(viewProductManagedBean.getProductToViewInDetails().getCategory().getCategoryId());
//        setTagIdsUpdate(new ArrayList<>());
//
//        for (Tag tag : selectedProductToUpdate.getTags()) {
//            getTagIdsUpdate().add(tag.getTagId());
//        }
//    }

    public void updateProduct(ActionEvent event) {

        Product productToBeUpdated = viewProductManagedBean.getProductToViewInDetails();

        Hardware hardwareEntityFragment = viewProductManagedBean.getHardwareToViewInDetails();
        OtherSoftware otherSoftwareEntityFragment = viewProductManagedBean.getOtherSoftwareToViewInDetails();
        Game gameEntityFragment = viewProductManagedBean.getGameToViewInDetails();
        try {
            System.out.println("---------------");
            tagIdsUpdate.forEach(id -> {
                System.out.println("tag id: " + id);
            }
            );
            System.out.println("---------------");

        } catch (NullPointerException ex) {
            System.err.println("Nullpointer exception occured.");
        }
        categoryIdUpdate = viewProductManagedBean.getProductToViewInDetails().getCategory().getCategoryId();
        try {
            if (gameToBeUpdated != null
                    && otherSoftwareEntityFragment == null
                    && hardwareEntityFragment == null) {
                Game gameToBeUpdated = (Game) productToBeUpdated;
                gameToBeUpdated.setParentAdvisory(gameEntityFragment.getParentAdvisory());
                gameToBeUpdated.setGamePicturesURLs(gameEntityFragment.getGamePicturesURLs());
                gameToBeUpdated.setGameTrailersURLS(gameEntityFragment.getGameTrailersURLS());
                gameToBeUpdated.setForums(gameEntityFragment.getForums());
                gameSessionBean.updateGame(gameToBeUpdated, getCategoryIdUpdate(), getTagIdsUpdate());
            } else if (viewProductManagedBean.getGameToViewInDetails() == null
                    && otherSoftwareEntityFragment != null
                    && hardwareEntityFragment == null) {
                OtherSoftware otherSoftwareToBeUpdated = (OtherSoftware) productToBeUpdated;
                otherSoftwareSessionBean.updateOtherSoftware(otherSoftwareToBeUpdated, getCategoryIdUpdate(), getTagIdsUpdate());
            } else if (viewProductManagedBean.getGameToViewInDetails() == null
                    && otherSoftwareEntityFragment == null
                    && hardwareEntityFragment != null) {
                Hardware hardwareToBeUpdated = (Hardware) productToBeUpdated;
                hardwareToBeUpdated.setDeliverables(hardwareEntityFragment.getDeliverables());
                hardwareToBeUpdated.setManufacturingCountry(hardwareEntityFragment.getManufacturingCountry());
                hardwareToBeUpdated.setTechnicalspecification(hardwareEntityFragment.getTechnicalspecification());
                hardwareToBeUpdated.setWarrantyDescription(hardwareEntityFragment.getWarrantyDescription());
                hardwareSessionBean.updateHardware(hardwareToBeUpdated, getCategoryIdUpdate(), getTagIdsUpdate());
            }

            for (Category ce : categories) {
                if (ce.getCategoryId().equals(getCategoryIdUpdate())) {
                    viewProductManagedBean.getProductToViewInDetails().setCategory(ce);
                    break;
                }
            }

            productToBeUpdated.getTags().clear();
            if (!tagIdsUpdate.isEmpty()) {
                System.out.println("!tagidsupdate.isempty block entered.");
                tags.forEach(tag -> {
                    if (getTagIdsUpdate().contains(tag.getTagId())) {
                        viewProductManagedBean.getProductToViewInDetails().getTags().add(tag);
                    }
                });
            }

        } catch (ProductNotFoundException | CategoryNotFoundException | TagNotFoundException
                | UpdateProductException | InputDataValidationException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Error for updating productEntity (ID: " + viewProductManagedBean.getProductToViewInDetails().getProductId() + ") "
                    + "Error Message: " + ex.getMessage(), null));
        }

        FacesContext.getCurrentInstance()
                .addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Product updated successfully", null));
        //   viewProductManagedBean.resetManageBean();
    }

    public void deleteProduct(ActionEvent event) {
        try {
            Product productToBeDeleted = (Product) event.getComponent().getAttributes().get("productToBeDeleted");
            productSessionBean.deleteProduct(productToBeDeleted);
            products.remove(productToBeDeleted);

            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
                    "Product deleted successfully ID: " + productToBeDeleted.getProductId(), null));
        } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "An unexpected error has occurred: " + ex.getMessage(), null));
        }
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
     * @return the categories
     */
    public List<Category> getCategories() {
        return categories;
    }

    /**
     * @param categories the categories to set
     */
    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    /**
     * @return the tags
     */
    public List<Tag> getTags() {
        return tags;
    }

    /**
     * @param tags the tags to set
     */
    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

    /**
     * @return the newGame
     */
    public Game getNewGame() {
        return newGame;
    }

    /**
     * @param newGame the newGame to set
     */
    public void setNewGame(Game newGame) {
        this.newGame = newGame;
    }

    /**
     * @return the gameToBeUpdated
     */
    public Game getGameToBeUpdated() {
        return gameToBeUpdated;
    }

    /**
     * @param gameToBeUpdated the gameToBeUpdated to set
     */
    public void setGameToBeUpdated(Game gameToBeUpdated) {
        this.gameToBeUpdated = gameToBeUpdated;
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
     * @return the productToViewInDetails
     */
    public Product getProductToViewInDetails() {
        return productToViewInDetails;
    }

    /**
     * @param productToViewInDetails the productToViewInDetails to set
     */
    public void setProductToViewInDetails(Product productToViewInDetails) {
        this.productToViewInDetails = productToViewInDetails;
    }

    /**
     * @return the gameToViewInDetails
     */
    public Game getGameToViewInDetails() {
        return gameToViewInDetails;
    }

    /**
     * @param gameToViewInDetails the gameToViewInDetails to set
     */
    public void setGameToViewInDetails(Game gameToViewInDetails) {
        this.gameToViewInDetails = gameToViewInDetails;
    }

    /**
     * @return the hardwareToViewInDetails
     */
    public Hardware getHardwareToViewInDetails() {
        return hardwareToViewInDetails;
    }

    /**
     * @param hardwareToViewInDetails the hardwareToViewInDetails to set
     */
    public void setHardwareToViewInDetails(Hardware hardwareToViewInDetails) {
        this.hardwareToViewInDetails = hardwareToViewInDetails;
    }

    /**
     * @return the otherSoftwareToViewInDetails
     */
    public OtherSoftware getOtherSoftwareToViewInDetails() {
        return otherSoftwareToViewInDetails;
    }

    /**
     * @param otherSoftwareToViewInDetails the otherSoftwareToViewInDetails to
     * set
     */
    public void setOtherSoftwareToViewInDetails(OtherSoftware otherSoftwareToViewInDetails) {
        this.otherSoftwareToViewInDetails = otherSoftwareToViewInDetails;
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
     * @return the newOtherSoftware
     */
    public OtherSoftware getNewOtherSoftware() {
        return newOtherSoftware;
    }

    /**
     * @param newOtherSoftware the newOtherSoftware to set
     */
    public void setNewOtherSoftware(OtherSoftware newOtherSoftware) {
        this.newOtherSoftware = newOtherSoftware;
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
     * @return the newHardware
     */
    public Hardware getNewHardware() {
        return newHardware;
    }

    /**
     * @param newHardware the newHardware to set
     */
    public void setNewHardware(Hardware newHardware) {
        this.newHardware = newHardware;
    }

    /**
     * @return the selectedProductToUpdate
     */
    public Product getSelectedProductToUpdate() {
        return selectedProductToUpdate;
    }

    /**
     * @param selectedProductToUpdate the selectedProductToUpdate to set
     */
    public void setSelectedProductToUpdate(Product selectedProductToUpdate) {
        this.selectedProductToUpdate = selectedProductToUpdate;
    }

    /**
     * @return the categoryIdUpdate
     */
    public Long getCategoryIdUpdate() {
        return categoryIdUpdate;
    }

    /**
     * @param categoryIdUpdate the categoryIdUpdate to set
     */
    public void setCategoryIdUpdate(Long categoryIdUpdate) {
        this.categoryIdUpdate = categoryIdUpdate;
    }

    /**
     * @return the tagIdsUpdate
     */
    public List<Long> getTagIdsUpdate() {
        return tagIdsUpdate;
    }

    /**
     * @param tagIdsUpdate the tagIdsUpdate to set
     */
    public void setTagIdsUpdate(List<Long> tagIdsUpdate) {
        this.tagIdsUpdate = tagIdsUpdate;
    }

}
