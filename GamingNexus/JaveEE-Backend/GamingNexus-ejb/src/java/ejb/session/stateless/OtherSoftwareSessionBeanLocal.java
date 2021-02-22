/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.OtherSoftware;
import java.util.List;
import javax.ejb.Local;
import util.exception.CategoryNotFoundException;
import util.exception.CompanyNotFoundException;
import util.exception.CreateNewProductException;
import util.exception.InputDataValidationException;
import util.exception.ProductNotFoundException;
import util.exception.ProductSkuCodeExistException;
import util.exception.TagNotFoundException;
import util.exception.UnknownPersistenceException;
import util.exception.UpdateProductException;

/**
 *
 * @author chenli
 */
@Local
public interface OtherSoftwareSessionBeanLocal {

    public OtherSoftware createNewOtherSoftware(OtherSoftware newOtherSoftware, Long categoryId, List<Long> tagIds, Long CompanyId) throws ProductSkuCodeExistException, UnknownPersistenceException, InputDataValidationException, CreateNewProductException, CompanyNotFoundException;

    public List<OtherSoftware> retrieveAllOtherSoftwares();

    public List<OtherSoftware> searchOtherSoftwaresByName(String searchString);

    public OtherSoftware retrieveOtherSoftwareById(Long productId) throws ProductNotFoundException;

    public void updateOtherSoftware(OtherSoftware otherSoftware, Long categoryId, List<Long> tagIds) throws ProductNotFoundException, CategoryNotFoundException, TagNotFoundException, UpdateProductException, InputDataValidationException;
    
}
