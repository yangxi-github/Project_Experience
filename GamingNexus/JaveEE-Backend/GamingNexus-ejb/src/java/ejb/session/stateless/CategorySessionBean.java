/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;


import entity.Category;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import util.exception.CategoryNotFoundException;
import util.exception.CreateNewCategoryException;
import util.exception.DeleteCategoryException;
import util.exception.InputDataValidationException;
import util.exception.UpdateCategoryException;

/**
 *
 * @author 63216
 */
@Stateless
public class CategorySessionBean implements CategorySessionBeanLocal {

    @PersistenceContext(unitName = "GamingNexus-ejbPU")
    private EntityManager em;

    public CategorySessionBean() {
    }

    @Override
    public Category createNewCategoryEntity(Category newCategoryEntity, Long parentCategoryId) throws InputDataValidationException, CreateNewCategoryException {

        try {
            if (parentCategoryId != null) {
                Category parentCategoryEntity = retrieveCategoryByCategoryId(parentCategoryId);

                if (!parentCategoryEntity.getProducts().isEmpty()) {
                    throw new CreateNewCategoryException("Parent category cannot be associated with any product");
                }

                newCategoryEntity.setParentCategory(parentCategoryEntity);
            }

            em.persist(newCategoryEntity);
            em.flush();

            return newCategoryEntity;
        } catch (PersistenceException ex) {
            if (ex.getCause() != null
                    && ex.getCause().getCause() != null
                    && ex.getCause().getCause().getClass().getSimpleName().equals("SQLIntegrityConstraintViolationException")) {
                throw new CreateNewCategoryException("Category with same name already exist");
            } else {
                throw new CreateNewCategoryException("An unexpected error has occurred: " + ex.getMessage());
            }
        } catch (Exception ex) {
            throw new CreateNewCategoryException("An unexpected error has occurred: " + ex.getMessage());
        }
    }

    @Override
    public List<Category> retrieveAllCategories() {
        Query query = em.createQuery("SELECT c FROM Category c ORDER BY c.name ASC");
        List<Category> categoryEntities = query.getResultList();

        for (Category categoryEntity : categoryEntities) {
            categoryEntity.getParentCategory();
            categoryEntity.getSubCategories().size();
            categoryEntity.getProducts().size();
        }

        return categoryEntities;
    }

    @Override
    public List<Category> retrieveAllRootCategories() {
        Query query = em.createQuery("SELECT c FROM Category c WHERE c.parentCategory IS NULL ORDER BY c.name ASC");
        List<Category> rootCategoryEntities = query.getResultList();

        for (Category rootCategoryEntity : rootCategoryEntities) {
            lazilyLoadSubCategories(rootCategoryEntity);

            rootCategoryEntity.getProducts().size();
        }

        return rootCategoryEntities;
    }
    
    @Override
    public List<Category> retrieveAllLeafCategories() {
        Query query = em.createQuery("SELECT c FROM Category c WHERE c.subCategories IS EMPTY ORDER BY c.name ASC");
        List<Category> leafCategoryEntities = query.getResultList();

        for (Category leafCategoryEntity : leafCategoryEntities) {
            leafCategoryEntity.getProducts().size();
        }

        return leafCategoryEntities;
    }
    
    @Override
    public List<Category> retrieveAllSoftwareToolCategories() {
        Query query = em.createQuery("SELECT c FROM Category c WHERE c.parentCategory.name = 'Software' ORDER BY c.name ASC");
        List<Category> leafCategoryEntities = query.getResultList();

        for (Category leafCategoryEntity : leafCategoryEntities) {
            leafCategoryEntity.getProducts().size();
        }
        return leafCategoryEntities;
    }
    
    @Override
    public List<Category> retrieveAllHardwareCategories() {
        Query query = em.createQuery("SELECT c FROM Category c WHERE c.parentCategory.name = 'Hardware' ORDER BY c.name ASC");
        List<Category> leafCategoryEntities = query.getResultList();

        for (Category leafCategoryEntity : leafCategoryEntities) {
            leafCategoryEntity.getProducts().size();
        }
        return leafCategoryEntities;
    }

    @Override
    public List<Category> retrieveAllCategoriesWithoutProduct() {
        Query query = em.createQuery("SELECT c FROM Category c WHERE c.products IS EMPTY ORDER BY c.name ASC");
        List<Category> rootCategoryEntities = query.getResultList();

        for (Category rootCategoryEntity : rootCategoryEntities) {
            rootCategoryEntity.getParentCategory();
        }

        return rootCategoryEntities;
    }

    @Override
    public Category retrieveCategoryByCategoryId(Long categoryId) throws CategoryNotFoundException {
        Category categoryEntity = em.find(Category.class, categoryId);

        if (categoryEntity != null) {
            return categoryEntity;
        } else {
            throw new CategoryNotFoundException("Category ID " + categoryId + " does not exist!");
        }
    }

    @Override
    public void updateCategory(Category categoryEntity, Long parentCategoryId) throws InputDataValidationException, CategoryNotFoundException, UpdateCategoryException {

        if (categoryEntity.getCategoryId() != null) {
            Category categoryEntityToUpdate = retrieveCategoryByCategoryId(categoryEntity.getCategoryId());

            Query query = em.createQuery("SELECT c FROM Category c WHERE c.name = :inName AND c.categoryID <> :inCategoryID");
            query.setParameter("inName", categoryEntity.getName());
            query.setParameter("inCategoryID", categoryEntity.getCategoryId());

            if (!query.getResultList().isEmpty()) {
                throw new UpdateCategoryException("The name of the category to be updated is duplicated");
            }

            categoryEntityToUpdate.setName(categoryEntity.getName());
            categoryEntityToUpdate.setDescription(categoryEntity.getDescription());

            if (parentCategoryId != null) {
                if (categoryEntityToUpdate.getCategoryId().equals(parentCategoryId)) {
                    throw new UpdateCategoryException("Category cannot be its own parent");
                } else if (categoryEntityToUpdate.getParentCategory() == null || (!categoryEntityToUpdate.getParentCategory().getCategoryId().equals(parentCategoryId))) {
                    Category parentCategoryEntityToUpdate = retrieveCategoryByCategoryId(parentCategoryId);

                    if (!parentCategoryEntityToUpdate.getProducts().isEmpty()) {
                        throw new UpdateCategoryException("Parent category cannot have any product associated with it");
                    }

                    categoryEntityToUpdate.setParentCategory(parentCategoryEntityToUpdate);
                }
            } else {
                categoryEntityToUpdate.setParentCategory(null);
            }
        } else {
            throw new CategoryNotFoundException("Category ID not provided for category to be updated");
        }
    }
    
    @Override
    public void deleteCategory(Long categoryId) throws CategoryNotFoundException, DeleteCategoryException
    {
        Category categoryEntityToRemove = retrieveCategoryByCategoryId(categoryId);
        
        if(!categoryEntityToRemove.getSubCategories().isEmpty())
        {
            throw new DeleteCategoryException("Category ID " + categoryId + " is associated with existing sub-categories and cannot be deleted!");
        }
        else if(!categoryEntityToRemove.getProducts().isEmpty())
        {
            throw new DeleteCategoryException("Category ID " + categoryId + " is associated with existing products and cannot be deleted!");
        }
        else
        {
            categoryEntityToRemove.setParentCategory(null);
            
            em.remove(categoryEntityToRemove);
        }                
    }

    private void lazilyLoadSubCategories(Category categoryEntity) {
        for (Category ce : categoryEntity.getSubCategories()) {
            lazilyLoadSubCategories(ce);
        }
    }
}
