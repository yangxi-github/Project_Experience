/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.Product;
import entity.Tag;
import java.util.List;
import javax.ejb.Local;
import util.exception.CreateNewTagException;
import util.exception.DeleteTagException;
import util.exception.InputDataValidationException;
import util.exception.TagNotFoundException;
import util.exception.UpdateTagException;

/**
 *
 * @author 63216
 */
@Local
public interface TagSessionBeanLocal {

    public Tag createNewTagEntity(Tag newTagEntity) throws InputDataValidationException, CreateNewTagException;

    public List<Tag> retrieveAllTags();

    public Tag retrieveTagByTagId(Long tagId) throws TagNotFoundException;

    public void updateTag(Tag tagEntity) throws InputDataValidationException, TagNotFoundException, UpdateTagException;

    public void deleteTag(Long tagId) throws TagNotFoundException, DeleteTagException;

    public List<Tag> retrieveAllGameTags();
    
    public List<Tag> retrieveAllNormalTags();

    
}
