/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.managedbean;

import ejb.session.stateless.TagSessionBeanLocal;
import entity.Tag;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import util.exception.CreateNewTagException;
import util.exception.InputDataValidationException;
import util.exception.TagNotFoundException;

/**
 *
 * @author jinyichen
 */
@Named(value = "tagManagedBean")
@ViewScoped
public class TagManagedBean implements Serializable {

    @EJB(name = "TagSessionBeanLocal")
    private TagSessionBeanLocal tagSessionBeanLocal;

    private Tag newTag;
    private Tag selectedTagToUpdate;
    private List<Tag> tags;
    private List<Tag> filteredTags;
    private Long tagIdNew;
    private List<Long> tagIdsNew;

    public TagManagedBean() {
        newTag = new Tag();
    }

    @PostConstruct
    public void postConstruct() {
        tags = tagSessionBeanLocal.retrieveAllTags();
    }

    public void createNewTag(ActionEvent event) {
        try {
            Tag tag = tagSessionBeanLocal.createNewTagEntity(newTag);
            tags.add(tag);

            if (filteredTags != null) {
                filteredTags.add(tag);
            }

            newTag = new Tag();
            tagIdNew = null;
            tagIdsNew = null;

            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "New tag created successfully (Product ID: " + tag.getTagId()+ ")", null));
        } catch (CreateNewTagException | InputDataValidationException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occurred while creating the new tag: " + ex.getMessage(), null));
        }
    }

    public void doUpdateTag(ActionEvent event) {
        selectedTagToUpdate = (Tag) event.getComponent().getAttributes().get("tagToUpdate");
        System.out.println("Now the tage : " + selectedTagToUpdate.getTagName() + " is going to be updated");
    }
    
    public void updateTag(ActionEvent event) {

        try {
            tagSessionBeanLocal.updateTag(selectedTagToUpdate);

            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Tag updated successfully", null));
        } catch (TagNotFoundException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occurred while updating Tag : " + ex.getMessage(), null));
        } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An unexpected error has occurred: " + ex.getMessage(), null));
        }
    }

    public void deleteTag(ActionEvent event) {
        try {
            Tag tagToDelete = (Tag) event.getComponent().getAttributes().get("tagToDelete");
            tagSessionBeanLocal.deleteTag(tagToDelete.getTagId());
            tags.remove(tagToDelete);

            if (filteredTags != null) {
                filteredTags.remove(tagToDelete);
            }

            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Tag deleted successfully", null));
        } catch (TagNotFoundException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occurred while deleting Tag: " + ex.getMessage(), null));
        } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An unexpected error has occurred: " + ex.getMessage(), null));
        }
    }
    
    public Tag getNewTag() {
        return newTag;
    }

    public void setNewTag(Tag newTag) {
        this.newTag = newTag;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

    public List<Tag> getFilteredTags() {
        return filteredTags;
    }

    public void setFilteredTags(List<Tag> filteredTags) {
        this.filteredTags = filteredTags;
    }

    public Long getTagIdNew() {
        return tagIdNew;
    }

    public void setTagIdNew(Long tagIdNew) {
        this.tagIdNew = tagIdNew;
    }

    public List<Long> getTagIdsNew() {
        return tagIdsNew;
    }

    public void setTagIdsNew(List<Long> tagIdsNew) {
        this.tagIdsNew = tagIdsNew;
    }

    public Tag getSelectedTagToUpdate() {
        return selectedTagToUpdate;
    }

    public void setSelectedTagToUpdate(Tag selectedTagToUpdate) {
        this.selectedTagToUpdate = selectedTagToUpdate;
    }

}
