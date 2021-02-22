/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.Category;
import entity.Company;
import entity.OtherSoftware;
import entity.Product;
import entity.Tag;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
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
 * @author jinyichen
 */
@Stateless
public class OtherSoftwareSessionBean implements OtherSoftwareSessionBeanLocal {

    @EJB
    private SaleTransactionSessionBeanLocal saleTransactionSessionBeanLocal;

    @EJB
    private CompanySessionBeanLocal companySessionBeanLocal;

    @EJB(name = "TagSessionBeanLocal")
    private TagSessionBeanLocal tagSessionBeanLocal;

    @EJB(name = "CategorySessionBeanLocal")
    private CategorySessionBeanLocal categorySessionBeanLocal;

    @PersistenceContext(unitName = "GamingNexus-ejbPU")
    private EntityManager em;

    public OtherSoftwareSessionBean() {
    }

    @Override
    public OtherSoftware createNewOtherSoftware(OtherSoftware newOtherSoftware, Long categoryId, List<Long> tagIds, Long companyId) throws ProductSkuCodeExistException, UnknownPersistenceException, InputDataValidationException, CreateNewProductException, CompanyNotFoundException {
        try {
            if (categoryId == null) {
                throw new CreateNewProductException("The new product must be associated a leaf category");
            }
            Category category = categorySessionBeanLocal.retrieveCategoryByCategoryId(categoryId);

            if (!category.getSubCategories().isEmpty()) {
                throw new CreateNewProductException("Selected category for the new product is not a leaf category");
            }

            if (companyId == null) {
                throw new CreateNewProductException("The new product must be associated a company");
            }
            Company company = companySessionBeanLocal.retrieveCompanyById(companyId);

            em.persist(newOtherSoftware);
            newOtherSoftware.setCategory(category);
            newOtherSoftware.setCompany(company);

            if (tagIds != null && (!tagIds.isEmpty())) {
                for (Long tagId : tagIds) {
                    Tag tag = tagSessionBeanLocal.retrieveTagByTagId(tagId);
                    newOtherSoftware.addTag(tag);
                }
            }
            em.flush();
            return newOtherSoftware;
        } catch (PersistenceException ex) {
            if (ex.getCause() != null && ex.getCause().getClass().getName().equals("org.eclipse.persistence.exceptions.DatabaseException")) {
                if (ex.getCause().getCause() != null && ex.getCause().getCause().getClass().getName().equals("java.sql.SQLIntegrityConstraintViolationException")) {
                    throw new ProductSkuCodeExistException();
                } else {
                    throw new UnknownPersistenceException(ex.getMessage());
                }
            } else {
                throw new UnknownPersistenceException(ex.getMessage());
            }
        } catch (CategoryNotFoundException | TagNotFoundException ex) {
            throw new CreateNewProductException("An error has occurred while creating the new product: " + ex.getMessage());
        }
    }

    @Override
    public List<OtherSoftware> retrieveAllOtherSoftwares() {
        Query query = em.createQuery("SELECT o FROM OtherSoftware o ORDER BY o.averageRating");
        List<OtherSoftware> otherSoftwares = query.getResultList();

        for (OtherSoftware otherSoftware : otherSoftwares) {
            lazyLoadOtherSoftware(otherSoftware);
        }

        return otherSoftwares;
    }

    @Override
    public List<OtherSoftware> searchOtherSoftwaresByName(String searchString) {
        Query query = em.createQuery("SELECT o FROM OtherSoftware o WHERE o.name LIKE :inSearchString");
        query.setParameter("inSearchString", "%" + searchString + "%");
        List<OtherSoftware> otherSoftwares = query.getResultList();

        for (OtherSoftware otherSoftware : otherSoftwares) {
            lazyLoadOtherSoftware(otherSoftware);
        }

        return otherSoftwares;
    }

    public List<Product> filterProductsByCategory(Long categoryId) throws CategoryNotFoundException {
        List<Product> productEntities = new ArrayList<>();
        Category categoryEntity = categorySessionBeanLocal.retrieveCategoryByCategoryId(categoryId);

        if (categoryEntity.getSubCategories().isEmpty()) {
            productEntities = categoryEntity.getProducts();
        } else {
            for (Category subCategoryEntity : categoryEntity.getSubCategories()) {
                productEntities.addAll(addSubCategoryProducts(subCategoryEntity));
            }
        }

        for (Product productEntity : productEntities) {
            productEntity.getCategory();
            productEntity.getTags().size();
        }

        Collections.sort(productEntities, new Comparator<Product>() {
            public int compare(Product pe1, Product pe2) {
                return pe1.getProductId().compareTo(pe2.getProductId());
            }
        });

        return productEntities;
    }

    public List<Product> filterProductsByTags(List<Long> tagIds, String condition) {
        List<Product> productEntities = new ArrayList<>();

        if (tagIds == null || tagIds.isEmpty() || (!condition.equals("AND") && !condition.equals("OR"))) {
            return productEntities;
        } else {
            if (condition.equals("OR")) {
                Query query = em.createQuery("SELECT DISTINCT pe FROM Product pe, IN (pe.tags) te WHERE te.tagID IN :inTagIDs ORDER BY pe.productID ASC");
                query.setParameter("inTagIDs", tagIds);
                productEntities = query.getResultList();
            } else // AND
            {
                String selectClause = "SELECT pe FROM ProductEntity pe";
                String whereClause = "";
                Boolean firstTag = true;
                Integer tagCount = 1;

                for (Long tagId : tagIds) {
                    selectClause += ", IN (pe.tagEntities) te" + tagCount;

                    if (firstTag) {
                        whereClause = "WHERE te1.tagId = " + tagId;
                        firstTag = false;
                    } else {
                        whereClause += " AND te" + tagCount + ".tagId = " + tagId;
                    }

                    tagCount++;
                }

                String jpql = selectClause + " " + whereClause + " ORDER BY pe.skuCode ASC";
                Query query = em.createQuery(jpql);
                productEntities = query.getResultList();
            }

            for (Product productEntity : productEntities) {
                productEntity.getCategory();
                productEntity.getTags().size();
            }

            Collections.sort(productEntities, new Comparator<Product>() {
                public int compare(Product pe1, Product pe2) {
                    return pe1.getProductId().compareTo(pe2.getProductId());
                }
            });

            return productEntities;
        }
    }

    @Override
    public OtherSoftware retrieveOtherSoftwareById(Long productId) throws ProductNotFoundException {
        OtherSoftware otherSoftware = em.find(OtherSoftware.class, productId);

        if (otherSoftware != null) {
            otherSoftware.getCategory();
            otherSoftware.getTags().size();

            return otherSoftware;
        } else {
            throw new ProductNotFoundException("Product ID " + productId + " does not exist!");
        }
    }

    @Override
    public void updateOtherSoftware(OtherSoftware otherSoftware, Long categoryId, List<Long> tagIds) throws ProductNotFoundException, CategoryNotFoundException, TagNotFoundException, UpdateProductException, InputDataValidationException {
        if (otherSoftware != null && otherSoftware.getProductId() != null) {
            Product otherSoftwareEntityToUpdate = this.retrieveOtherSoftwareById(otherSoftware.getProductId());

            if (categoryId != null && (!otherSoftwareEntityToUpdate.getCategory().getCategoryId().equals(categoryId))) {
                Category categoryEntityToUpdate = categorySessionBeanLocal.retrieveCategoryByCategoryId(categoryId);

                if (!categoryEntityToUpdate.getSubCategories().isEmpty()) {
                    throw new UpdateProductException("Selected category for the new product is not a leaf category");
                }

                otherSoftwareEntityToUpdate.setCategory(categoryEntityToUpdate);
            }
            if (tagIds != null && !tagIds.isEmpty()) {
                for (Tag tagEntity : otherSoftwareEntityToUpdate.getTags()) {
                    tagEntity.getProducts().remove(otherSoftwareEntityToUpdate);
                }
                otherSoftwareEntityToUpdate.getTags().clear();
                for (Long tagId : tagIds) {
                    Tag tagEntity = tagSessionBeanLocal.retrieveTagByTagId(tagId);
                    otherSoftwareEntityToUpdate.addTag(tagEntity);
                }
            }
            otherSoftwareEntityToUpdate.setName(otherSoftware.getName());
            otherSoftwareEntityToUpdate.setDescription(otherSoftware.getDescription());
            otherSoftwareEntityToUpdate.setComputerRequirements(otherSoftware.getComputerRequirements());
            otherSoftwareEntityToUpdate.setPrice(otherSoftware.getPrice());
            otherSoftwareEntityToUpdate.setCompany(otherSoftware.getCompany());
            otherSoftwareEntityToUpdate.setAverageRating((otherSoftware.getAverageRating()));
            otherSoftwareEntityToUpdate.setOwnedItems(otherSoftware.getOwnedItems());
            otherSoftwareEntityToUpdate.setPromotions(otherSoftware.getPromotions());
            otherSoftwareEntityToUpdate.setRatings(otherSoftware.getRatings());
            otherSoftwareEntityToUpdate.setTags(otherSoftware.getTags());

        } else {
            throw new ProductNotFoundException("Product ID not provided for product to be updated");
        }
    }

    /*
    public void deleteProduct(Long productId) throws ProductNotFoundException, DeleteProductException {
        ProductEntity productEntityToRemove = retrieveProductByProductId(productId);

        List<SaleTransactionLineItemEntity> saleTransactionLineItemEntities = saleTransactionEntitySessionBeanLocal.retrieveSaleTransactionLineItemsByProductId(productId);

        if (saleTransactionLineItemEntities.isEmpty()) {
            entityManager.remove(productEntityToRemove);
        } else {
            throw new DeleteProductException("Product ID " + productId + " is associated with existing sale transaction line item(s) and cannot be deleted!");
        }
    }
     */
    private List<Product> addSubCategoryProducts(Category categoryEntity) {
        List<Product> productEntities = new ArrayList<>();

        if (categoryEntity.getSubCategories().isEmpty()) {
            return categoryEntity.getProducts();
        } else {
            for (Category subCategoryEntity : categoryEntity.getSubCategories()) {
                productEntities.addAll(addSubCategoryProducts(subCategoryEntity));
            }

            return productEntities;
        }
    }

    public void lazyLoadOtherSoftware(OtherSoftware otherSoftware) {
        otherSoftware.getCompany();
        otherSoftware.getCategory();
        otherSoftware.getTags().size();
        otherSoftware.getPromotions().size();
        otherSoftware.getRatings().size();
        otherSoftware.getOwnedItems().size();
        otherSoftware.getForums().size();

    }
}
