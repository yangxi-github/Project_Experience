/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.Category;
import entity.Company;
import entity.Hardware;
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
public class HardwareSessionBean implements HardwareSessionBeanLocal {

    @EJB(name = "SaleTransactionSessionBeanLocal")
    private SaleTransactionSessionBeanLocal saleTransactionSessionBeanLocal;

    @EJB(name = "CompanySessionBeanLocal")
    private CompanySessionBeanLocal companySessionBeanLocal;

    @EJB(name = "TagSessionBeanLocal")
    private TagSessionBeanLocal tagSessionBeanLocal;

    @EJB(name = "CategorySessionBeanLocal")
    private CategorySessionBeanLocal categorySessionBeanLocal;

    @PersistenceContext(unitName = "GamingNexus-ejbPU")
    private EntityManager em;

    public HardwareSessionBean() {
    }

    @Override
    public Hardware createNewHardware(Hardware newHardware, Long categoryId, List<Long> tagIds, Long companyId) throws ProductSkuCodeExistException, UnknownPersistenceException, InputDataValidationException, CreateNewProductException, CompanyNotFoundException {
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

            em.persist(newHardware);
            newHardware.setCategory(category);
            newHardware.setCompany(company);

            if (tagIds != null && (!tagIds.isEmpty())) {
                for (Long tagId : tagIds) {
                    Tag tag = tagSessionBeanLocal.retrieveTagByTagId(tagId);
                    newHardware.addTag(tag);
                }
            }
            em.flush();
            return newHardware;
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

    public List<Hardware> retrieveAllHardwares() {
        Query query = em.createQuery("SELECT h FROM Hardware h ORDER BY h.averageRating ASC");
        List<Hardware> hardwares = query.getResultList();

        for (Hardware hardware : hardwares) {
            lazyLoadHardware(hardware);
        }

        return hardwares;
    }

    public List<Hardware> searchHardwaresByName(String searchString) {
        Query query = em.createQuery("SELECT h FROM Hardware h WHERE h.name LIKE :inSearchString");
        query.setParameter("inSearchString", "%" + searchString + "%");
        List<Hardware> hardwares = query.getResultList();

        for (Hardware hardware : hardwares) {
            lazyLoadHardware(hardware);
        }

        return hardwares;
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
    public Hardware retrieveHardwareById(Long hardwareId) throws ProductNotFoundException {
        Hardware hardware = em.find(Hardware.class, hardwareId);

        if (hardware != null) {
            hardware.getCategory();
            hardware.getTags().size();

            return hardware;
        } else {
            throw new ProductNotFoundException("Product ID " + hardwareId + " does not exist!");
        }
    }

    @Override
    public void updateHardware(Hardware hardware, Long categoryId, List<Long> tagIds) throws ProductNotFoundException, CategoryNotFoundException, TagNotFoundException, UpdateProductException, InputDataValidationException {
        if (hardware != null && hardware.getProductId() != null) {
            Hardware hardwareToUpdate = retrieveHardwareById(hardware.getProductId());

            if (categoryId != null && (!hardwareToUpdate.getCategory().getCategoryId().equals(categoryId))) {
                Category categoryEntityToUpdate = categorySessionBeanLocal.retrieveCategoryByCategoryId(categoryId);

                if (!categoryEntityToUpdate.getSubCategories().isEmpty()) {
                    throw new UpdateProductException("Selected category for the new product is not a leaf category");
                }

                hardwareToUpdate.setCategory(categoryEntityToUpdate);
            }
            if (tagIds != null && !tagIds.isEmpty()) {
                for (Tag tagEntity : hardwareToUpdate.getTags()) {
                    tagEntity.getProducts().remove(hardwareToUpdate);
                }
                hardwareToUpdate.getTags().clear();
                for (Long tagId : tagIds) {
                    Tag tagEntity = tagSessionBeanLocal.retrieveTagByTagId(tagId);
                    hardwareToUpdate.addTag(tagEntity);
                }
            }
            hardwareToUpdate.setName(hardware.getName());
            hardwareToUpdate.setDescription(hardware.getDescription());
            hardwareToUpdate.setComputerRequirements(hardware.getComputerRequirements());
            hardwareToUpdate.setPrice(hardware.getPrice());
            hardwareToUpdate.setCompany(hardware.getCompany());
            hardwareToUpdate.setAverageRating((hardware.getAverageRating()));
            hardwareToUpdate.setDeliverables(hardware.getDeliverables());
            hardwareToUpdate.setManufacturingCountry(hardware.getManufacturingCountry());
            hardwareToUpdate.setOwnedItems(hardware.getOwnedItems());
            hardwareToUpdate.setOwnedItems(hardware.getOwnedItems());
            hardwareToUpdate.setPromotions(hardware.getPromotions());
            hardwareToUpdate.setRatings(hardware.getRatings());
            hardwareToUpdate.setTags(hardware.getTags());
            hardwareToUpdate.setTechnicalspecification(hardware.getTechnicalspecification());
            hardwareToUpdate.setWarrantyDescription(hardware.getWarrantyDescription());

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
    
    public void lazyLoadHardware(Hardware hardware) {
        hardware.getCompany();
        hardware.getCategory();
        hardware.getTags().size();
        hardware.getPromotions().size();
        hardware.getRatings().size();
        hardware.getOwnedItems().size();
        hardware.getForums().size();
        hardware.getDeliverables().size();
    }
}
