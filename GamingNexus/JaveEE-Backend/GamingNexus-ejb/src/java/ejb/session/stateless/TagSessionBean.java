/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.Tag;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import util.exception.CreateNewTagException;
import util.exception.DeleteTagException;
import util.exception.InputDataValidationException;
import util.exception.TagNotFoundException;
import util.exception.UpdateTagException;

/**
 *
 * @author 63216
 */
@Stateless
public class TagSessionBean implements TagSessionBeanLocal {

    @PersistenceContext(unitName = "GamingNexus-ejbPU")
    private EntityManager em;

    @Override
    public Tag createNewTagEntity(Tag newTagEntity) throws InputDataValidationException, CreateNewTagException {

        try {
            em.persist(newTagEntity);
            em.flush();

            return newTagEntity;
        } catch (PersistenceException ex) {
            if (ex.getCause() != null
                    && ex.getCause().getCause() != null
                    && ex.getCause().getCause().getClass().getSimpleName().equals("SQLIntegrityConstraintViolationException")) {
                throw new CreateNewTagException("Tag with same name already exist");
            } else {
                throw new CreateNewTagException("An unexpected error has occurred: " + ex.getMessage());
            }
        } catch (Exception ex) {
            throw new CreateNewTagException("An unexpected error has occurred: " + ex.getMessage());
        }
    }

    @Override
    public List<Tag> retrieveAllTags() {
        Query query = em.createQuery("SELECT t FROM Tag t ORDER BY t.tagName ASC");
        List<Tag> tagEntities = query.getResultList();

        for (Tag tagEntity : tagEntities) {
            tagEntity.getProducts().size();
        }

        return tagEntities;
    }

    @Override
    public List<Tag> retrieveAllGameTags() {
        Query query = em.createQuery("SELECT t FROM Tag t WHERE t.isGameTag = TRUE ORDER BY t.tagName ASC");
        List<Tag> tagEntities = query.getResultList();

        for (Tag tagEntity : tagEntities) {
            tagEntity.getProducts().size();
        }

        return tagEntities;
    }
    
    @Override
    public List<Tag> retrieveAllNormalTags() {
        Query query = em.createQuery("SELECT t FROM Tag t WHERE t.isGameTag = FALSE ORDER BY t.tagName ASC");
        List<Tag> tagEntities = query.getResultList();

        for (Tag tagEntity : tagEntities) {
            tagEntity.getProducts().size();
        }

        return tagEntities;
    }
    
    @Override
    public Tag retrieveTagByTagId(Long tagId) throws TagNotFoundException {
        Tag tagEntity = em.find(Tag.class, tagId);

        if (tagEntity != null) {
            return tagEntity;
        } else {
            throw new TagNotFoundException("Tag ID " + tagId + " does not exist!");
        }
    }

    
    
    @Override
    public void updateTag(Tag tagEntity) throws InputDataValidationException, TagNotFoundException, UpdateTagException {
        if (tagEntity.getTagId() != null) {
            Tag tag = retrieveTagByTagId(tagEntity.getTagId());

            Query query = em.createQuery("SELECT t FROM Tag t WHERE t.tagName = :inName AND t.tagId <> :inTagId");
            query.setParameter("inName", tag.getTagName());
            query.setParameter("inTagId", tag.getTagId());

            if (!query.getResultList().isEmpty()) {
                throw new UpdateTagException("The name of the tag to be updated is duplicated");
            }

            tag.setTagName(tag.getTagName());
        } else {
            throw new TagNotFoundException("Tag ID not provided for tag to be updated");
        }
    }

    @Override
    public void deleteTag(Long tagId) throws TagNotFoundException, DeleteTagException {
        Tag tagEntityToRemove = retrieveTagByTagId(tagId);

        if (!tagEntityToRemove.getProducts().isEmpty()) {
            throw new DeleteTagException("Tag ID " + tagId + " is associated with existing products and cannot be deleted!");
        } else {
            em.remove(tagEntityToRemove);
        }
    }
}
