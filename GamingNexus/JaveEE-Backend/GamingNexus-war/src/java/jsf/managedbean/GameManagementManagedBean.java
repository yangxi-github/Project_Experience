/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.managedbean;

import ejb.session.stateless.CategorySessionBeanLocal;
import ejb.session.stateless.GameSessionBeanLocal;
import ejb.session.stateless.TagSessionBeanLocal;
import entity.Game;
import entity.Tag;
import entity.Category;
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
import javax.inject.Inject;
import util.exception.CompanyNotFoundException;
import util.exception.CreateNewProductException;
import util.exception.DeleteProductException;
import util.exception.InputDataValidationException;
import util.exception.ProductNotFoundException;
import util.exception.ProductSkuCodeExistException;
import util.exception.UnknownPersistenceException;

/**
 *
 * @author JYC
 */
@Named(value = "gameManagementManagedBean")
@ViewScoped

public class GameManagementManagedBean implements Serializable{

    @EJB
    private TagSessionBeanLocal tagSessionBeanLocal;

    @EJB
    private CategorySessionBeanLocal categorySessionBeanLocal;

    @EJB
    private GameSessionBeanLocal gameSessionBeanLocal;
    
    @Inject
    private ViewGameManagedBean viewGameManagedBean;
    
    private List<Game> games;
    private List<Game> filteredGames;
    
    private Game newGame;
    private Long categoryIdNew;
    private Long companyIdNew;
    private List<Long> tagIdsNew;
    private List<Category> categoryEntities;
    private List<Tag> tagEntities;    
    
    private Game selectedGameToUpdate;
    private Long categoryIdUpdate;
    private List<Long> tagIdsUpdate;

    
    public GameManagementManagedBean() {
        newGame = new Game();
    }

    @PostConstruct
    public void postConstruct()
    {
        games = gameSessionBeanLocal.retrieveAllGames();
        categoryEntities = categorySessionBeanLocal.retrieveAllLeafCategories();
        tagEntities = tagSessionBeanLocal.retrieveAllTags();
    }
    
    
    
    public void viewGameDetails(ActionEvent event) throws IOException
    {
        Long gameIdToView = (Long)event.getComponent().getAttributes().get("productId");
        FacesContext.getCurrentInstance().getExternalContext().getFlash().put("gameIdToView", gameIdToView);
        FacesContext.getCurrentInstance().getExternalContext().redirect("viewGameDetails.xhtml");
    }
    
    
    
    public void createNewGame(ActionEvent event)
    {        
        if(categoryIdNew == 0)
        {
            categoryIdNew = null;
        }                
        
        try
        {
            Game pe = gameSessionBeanLocal.createNewGame(newGame, categoryIdNew, tagIdsNew, companyIdNew);
            games.add(pe);
            
            if(filteredGames != null)
            {
                filteredGames.add(pe);
            }
            
            newGame = new Game();
            categoryIdNew = null;
            tagIdsNew = null;
            

            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "New Game created successfully (Product ID: " + pe.getProductId() + ")", null));
        }
        catch(InputDataValidationException | CreateNewProductException | ProductSkuCodeExistException | UnknownPersistenceException | CompanyNotFoundException ex)
        {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occurred while creating the new product: " + ex.getMessage(), null));
        }
    }
    
    
    
    public void doUpdateGame(ActionEvent event)
    {
        selectedGameToUpdate = (Game)event.getComponent().getAttributes().get("productEntityToUpdate");
        
        categoryIdUpdate = selectedGameToUpdate.getCategory().getCategoryId();
        tagIdsUpdate = new ArrayList<>();

        for(Tag tagEntity:selectedGameToUpdate.getTags())
        {
            tagIdsUpdate.add(tagEntity.getTagId());
        }
    }
    
    
    
    public void updateGame(ActionEvent event)
    {        
        if(categoryIdUpdate  == 0)
        {
            categoryIdUpdate = null;
        }                
        
        try
        {
         //   gameSessionBeanLocal.updateProduct(selectedGameToUpdate, categoryIdUpdate, tagIdsUpdate);
                        
            for(Category ce:categoryEntities)
            {
                if(ce.getCategoryId().equals(categoryIdUpdate))
                {
                    selectedGameToUpdate.setCategory(ce);
                    break;
                }                
            }
            
            selectedGameToUpdate.getTags().clear();
            
            for(Tag te:tagEntities)
            {
                if(tagIdsUpdate.contains(te.getTagId()))
                {
                    selectedGameToUpdate.getTags().add(te);
                }                
            }

            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Product updated successfully", null));
        }
//        catch(ProductNotFoundException ex)
//        {
//            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occurred while updating product: " + ex.getMessage(), null));
//        }
        catch(Exception ex)
        {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An unexpected error has occurred: " + ex.getMessage(), null));
        }
    }
    
    
    
//    public void deleteProduct(ActionEvent event)
//    {
//        try
//        {
//            ProductEntity productEntityToDelete = (ProductEntity)event.getComponent().getAttributes().get("productEntityToDelete");
//            productEntitySessionBeanLocal.deleteProduct(productEntityToDelete.getProductId());
//            
//            productEntities.remove(productEntityToDelete);
//            
//            if(filteredProductEntities != null)
//            {
//                filteredProductEntities.remove(productEntityToDelete);
//            }
//
//            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Product deleted successfully", null));
//        }
//        catch(ProductNotFoundException | DeleteProductException ex)
//        {
//            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occurred while deleting product: " + ex.getMessage(), null));
//        }
//        catch(Exception ex)
//        {
//            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An unexpected error has occurred: " + ex.getMessage(), null));
//        }
//    }
    

    public ViewGameManagedBean getViewGameManagedBean() {
        return viewGameManagedBean;
    }

    public void setViewGameManagedBean(ViewGameManagedBean viewGameManagedBean) {
        this.viewGameManagedBean = viewGameManagedBean;
    }

    public List<Game> getGames() {
        return games;
    }

    public void setGames(List<Game> games) {
        this.games = games;
    }

    public List<Game> getFilteredGames() {
        return filteredGames;
    }

    public void setFilteredGames(List<Game> filteredGames) {
        this.filteredGames = filteredGames;
    }

    public Game getNewGame() {
        return newGame;
    }

    public void setNewGame(Game newGame) {
        this.newGame = newGame;
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

    public Game getSelectedGameToUpdate() {
        return selectedGameToUpdate;
    }

    public void setSelectedGameToUpdate(Game selectedGameToUpdate) {
        this.selectedGameToUpdate = selectedGameToUpdate;
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
    
    
    
}
