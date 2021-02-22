/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.managedbean;

import ejb.session.stateless.CategorySessionBeanLocal;
import ejb.session.stateless.ProductSessionBeanLocal;
import ejb.session.stateless.TagSessionBeanLocal;
import entity.Product;
import entity.Category;
import entity.Tag;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import util.exception.ProductNotFoundException;

/**
 *
 * @author Yang Xi
 */
@Named(value = "productManagementManagedBean")
@ViewScoped
public class ProductManagementManagedBean implements Serializable{

    @EJB(name = "TagSessionBeanLocal")
    private TagSessionBeanLocal tagSessionBeanLocal;

    @EJB(name = "CategorySessionBeanLocal")
    private CategorySessionBeanLocal categorySessionBeanLocal;

    @EJB(name = "ProductSessionBeanLocal")
    private ProductSessionBeanLocal productSessionBeanLocal;
    
    private List<Product> productEntities;
    private List<Product> filteredProductEntities;
    
    private Product newProductEntity;
    private Long categoryIdNew;
    private List<Long> tagIdsNew;
    private List<Category> categoryEntities;
    private List<Tag> tagEntities;    
    private List<Tag> normalTagEntities;
    
    private Product selectedProductEntityToUpdate;
    private Product productEntityToView;
    private Long categoryIdUpdate;
    private List<Long> tagIdsUpdate;
  
    /**
     * Creates a new instance of ProductManagementManagedBean
     */
    public ProductManagementManagedBean() {
    }
    
     @PostConstruct
    public void postConstruct()
    {
        productEntities = productSessionBeanLocal.retrieveAllProducts();
        categoryEntities = categorySessionBeanLocal.retrieveAllLeafCategories();
        tagEntities = tagSessionBeanLocal.retrieveAllTags();
        normalTagEntities = tagSessionBeanLocal.retrieveAllNormalTags();
    }
    
    
    
    public void viewProductDetails(ActionEvent event) throws IOException
    {
        Long productIdToView = (Long)event.getComponent().getAttributes().get("productId");
        FacesContext.getCurrentInstance().getExternalContext().getFlash().put("productIdToView", productIdToView);
        FacesContext.getCurrentInstance().getExternalContext().redirect("viewProductDetails.xhtml");
    }
    
    
    public void doUpdateProduct(ActionEvent event)
    {
        selectedProductEntityToUpdate = (Product)event.getComponent().getAttributes().get("productEntityToUpdate");
        
        categoryIdUpdate = selectedProductEntityToUpdate.getCategory().getCategoryId();
        tagIdsUpdate = new ArrayList<>();

        for(Tag tag: selectedProductEntityToUpdate.getNormalTags())
        {
            tagIdsUpdate.add(tag.getTagId());
        }
    }
    
    

    
    
    public void updateProduct(ActionEvent event)
    {        

        try
        {
            productSessionBeanLocal.
                    updateProduct(selectedProductEntityToUpdate, selectedProductEntityToUpdate.getCategory().getCategoryId(), tagIdsUpdate);
                             
            selectedProductEntityToUpdate.clearNormalTags();
            
            for(Tag te: normalTagEntities)
            {
                if(tagIdsUpdate.contains(te.getTagId()))
                {
                    selectedProductEntityToUpdate.getTags().add(te);
                }                
            }

            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Product updated successfully", null));
        }
        catch(ProductNotFoundException ex)
        {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occurred while updating product: " + ex.getMessage(), null));
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An unexpected error has occurred: " + ex.getMessage(), null));
        }
    }
    
    

    public List<Product> getProductEntities() {
        return productEntities;
    }

    public void setProductEntities(List<Product> productEntities) {
        this.productEntities = productEntities;
    }

    public List<Product> getFilteredProductEntities() {
        return filteredProductEntities;
    }

    public void setFilteredProductEntities(List<Product> filteredProductEntities) {
        this.filteredProductEntities = filteredProductEntities;
    }

    public Product getNewProductEntity() {
        return newProductEntity;
    }

    public void setNewProductEntity(Product newProductEntity) {
        this.newProductEntity = newProductEntity;
    }
    
    public Long getCategoryIdNew() {
        return categoryIdNew;
    }

    public void setCategoryIdNew(Long categoryIdNew) {
        this.categoryIdNew = categoryIdNew;
    }

    public List<Long> getTagIdsNew() {
        return tagIdsNew;
    }

    public void setTagIdsNew(List<Long> tagIdsNew) {
        this.tagIdsNew = tagIdsNew;
    }

    public List<Category> getCategoryEntities() {
        return categoryEntities;
    }

    public void setCategoryEntities(List<Category> categoryEntities) {
        this.categoryEntities = categoryEntities;
    }

    public List<Tag> getTagEntities() {
        return tagEntities;
    }

    public void setTagEntities(List<Tag> tagEntities) {
        this.tagEntities = tagEntities;
    }
    
    public Product getSelectedProductEntityToUpdate() {
        return selectedProductEntityToUpdate;
    }

    public void setSelectedProductEntityToUpdate(Product selectedProductEntityToUpdate) {
        this.selectedProductEntityToUpdate = selectedProductEntityToUpdate;
    }

    public Long getCategoryIdUpdate() {
        return categoryIdUpdate;
    }

    public void setCategoryIdUpdate(Long categoryIdUpdate) {
        this.categoryIdUpdate = categoryIdUpdate;
    }

    public List<Long> getTagIdsUpdate() {
        return tagIdsUpdate;
    }

    public void setTagIdsUpdate(List<Long> tagIdsUpdate) {
        this.tagIdsUpdate = tagIdsUpdate;
    } 

    public Product getProductEntityToView() {
        return productEntityToView;
    }

    public void setProductEntityToView(Product productEntityToView) {
        this.productEntityToView = productEntityToView;
    }

    public List<Tag> getNormalTagEntities() {
        return normalTagEntities;
    }

    public void setNormalTagEntities(List<Tag> normalTagEntities) {
        this.normalTagEntities = normalTagEntities;
    }


    
}
