package ejb.session.stateless;

import entity.Category;
import entity.Company;
import entity.Game;
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
public class GameSessionBean implements GameSessionBeanLocal {
    
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
    
    public GameSessionBean() {
        
    }
    
    @Override
    public Game createNewGame(Game newGame, Long categoryId, List<Long> tagIds, Long companyId) throws ProductSkuCodeExistException, UnknownPersistenceException, InputDataValidationException, CreateNewProductException, CompanyNotFoundException {
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
            
            
            em.persist(newGame);
            newGame.setCategory(category);
            category.getProducts().add(newGame);
            newGame.setCompany(company);
            
            if (tagIds != null && (!tagIds.isEmpty())) {
                for (Long tagId : tagIds) {
                    Tag tag = tagSessionBeanLocal.retrieveTagByTagId(tagId);
                    newGame.addTag(tag);
                    tag.getProducts().add(newGame);
                }
            }
            em.flush();
            return newGame;
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
    public Game createNewGame(Game newGame, Long categoryId, List<Long> tagIds, Long CompanyId, boolean parentAdvisory, String headerImage) throws ProductSkuCodeExistException, UnknownPersistenceException, InputDataValidationException, CreateNewProductException, CompanyNotFoundException {
        try {
            if (categoryId == null) {
                throw new CreateNewProductException("The new product must be associated a leaf category");
            }
            Category category = categorySessionBeanLocal.retrieveCategoryByCategoryId(categoryId);
            
            if (!category.getSubCategories().isEmpty()) {
                throw new CreateNewProductException("Selected category for the new product is not a leaf category");
            }
            
            if (CompanyId == null) {
                throw new CreateNewProductException("The new product must be associated a company");
            }
            Company company = companySessionBeanLocal.retrieveCompanyById(categoryId);
            
            em.persist(newGame);
            newGame.setCategory(category);
            newGame.setCompany(company);
            newGame.setParentAdvisory(parentAdvisory);
            newGame.setHeaderImage(headerImage);
            
            if (tagIds != null && (!tagIds.isEmpty())) {
                for (Long tagId : tagIds) {
                    Tag tag = tagSessionBeanLocal.retrieveTagByTagId(tagId);
                    newGame.addTag(tag);
                }
            }
            em.flush();
            return newGame;
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
    public List<Game> retrieveAllGames() {
        Query query = em.createQuery("SELECT g FROM Game g ORDER BY g.averageRating ASC");
        List<Game> games = query.getResultList();
        
        for (Game game : games) {
            lazyLoadGame(game);
        }
        
        return games;
    }
    
    @Override
    public List<Game> searchGamesByName(String searchString) {
        Query query = em.createQuery("SELECT g FROM Game g WHERE g.name LIKE :inSearchString");
        query.setParameter("inSearchString", "%" + searchString + "%");
        List<Game> games = query.getResultList();
        
        for (Game game : games) {
            lazyLoadGame(game);
        }
        
        return games;
    }
    
   
    @Override
    public List<Game> filterGamesByTags(List<Long> tagIds, String condition) {
        List<Game> games = new ArrayList<>();
        
        if (tagIds == null || tagIds.isEmpty() || (!condition.equals("AND") && !condition.equals("OR"))) {
            return games;
        } else {
            if (condition.equals("OR")) {
                Query query = em.createQuery("SELECT DISTINCT ge FROM Game ge, IN (ge.tags) te WHERE te.tagID IN :inTagIDs ORDER BY ge.productId");
                query.setParameter("inTagIDs", tagIds);
                games = query.getResultList();
            } else // AND
            {
                String selectClause = "SELECT ge FROM Game ge";
                String whereClause = "";
                Boolean firstTag = true;
                Integer tagCount = 1;
                
                for (Long tagId : tagIds) {
                    selectClause += ", IN (ge.tags) te" + tagCount;
                    
                    if (firstTag) {
                        whereClause = "WHERE te1.tagId = " + tagId;
                        firstTag = false;
                    } else {
                        whereClause += " AND te" + tagCount + ".tagId = " + tagId;
                    }                
                    tagCount++;
                }
                
                String jpql = selectClause + " " + whereClause + " ORDER BY ge.productId";
                Query query = em.createQuery(jpql);
                games = query.getResultList();
            }
            
            for (Game game : games) {
                game.getCategory();
                game.getTags().size();
            }
            
            Collections.sort(games, new Comparator<Game>() {
                public int compare(Game ge1, Game ge2) {
                    return ge1.getProductId().compareTo(ge2.getProductId());
                }
            });
            
            return games;
        }
    }
    
    @Override
    public Game retrieveGamebyId(Long gameId) throws ProductNotFoundException {
        Game game = em.find(Game.class, gameId);
        
        if (game != null) {
            lazyLoadGame(game);
            
            return game;
        } else {
            throw new ProductNotFoundException("Game ID " + game + " does not exist!");
        }
    }
    
    @Override
    public void updateGame(Game game, Long categoryId, List<Long> tagIds) throws ProductNotFoundException, CategoryNotFoundException, TagNotFoundException, UpdateProductException, InputDataValidationException {
        if (game != null && game.getProductId() != null) {
            Game gameToBeUpdated = retrieveGamebyId(game.getProductId());
            
            if (categoryId != null && (!gameToBeUpdated.getCategory().getCategoryId().equals(categoryId))) {
                System.out.println("GameSessionBean: Entered category update block");
                Category categoryEntityToUpdate = categorySessionBeanLocal.retrieveCategoryByCategoryId(categoryId);
                
                if (!categoryEntityToUpdate.getSubCategories().isEmpty()) {
                    throw new UpdateProductException("Selected category for the new product is not a leaf category");
                }
                
                gameToBeUpdated.setCategory(categoryEntityToUpdate);
            }
            if (tagIds != null && !tagIds.isEmpty()) {
                System.out.println("GameSessionBean: Entered tag update block");
                
                for (Tag tagEntity : gameToBeUpdated.getTags()) {
                    tagEntity.getProducts().remove(gameToBeUpdated);
                }
                gameToBeUpdated.getTags().clear();
                for (Long tagId : tagIds) {
                    Tag tagEntity = tagSessionBeanLocal.retrieveTagByTagId(tagId);
                    gameToBeUpdated.addTag(tagEntity);
                }
            }
            gameToBeUpdated.setName(game.getName());
            gameToBeUpdated.setDescription(game.getDescription());
            gameToBeUpdated.setComputerRequirements(game.getComputerRequirements());
            gameToBeUpdated.setPrice(game.getPrice());
            gameToBeUpdated.setCompany(game.getCompany());
            gameToBeUpdated.setAverageRating((game.getAverageRating()));
            //  gameToBeUpdated.setCategory(game.getCategory());
            gameToBeUpdated.setForums(game.getForums());
            gameToBeUpdated.setGameAccounts(game.getGameAccounts());
            gameToBeUpdated.setGamePicturesURLs(game.getGamePicturesURLs());
            gameToBeUpdated.setOwnedItems(game.getOwnedItems());
            gameToBeUpdated.setParentAdvisory(game.getParentAdvisory());
            gameToBeUpdated.setPromotions(game.getPromotions());
            gameToBeUpdated.setRatings(game.getRatings());
            // gameToBeUpdated.setTags(game.getTags());

        } else {
            throw new ProductNotFoundException("Game ID not provided for product to be updated");
        }
    }

    /*
    public void deleteProduct(Long productId) throws ProductNotFoundException, DeleteProductException {
        ProductEntity productEntityToRemove = retrieveProductByProductId(productId);

        List<SaleTransactionLineItemEntity> saleTransactionLineItemEntities = saleTransactionEntitySessionBeanLocal.retrieveSaleTransactionLineItemsByProductId(productId);

        if (saleTransactionLineItemEntities.isEmpty()) {
            entityManager.remove(productEntityToRemove);
        } else {
            throw new DeleteProductException("Game ID " + productId + " is associated with existing sale transaction line item(s) and cannot be deleted!");
        }
    }
     */
   
    
    public void lazyLoadGame(Game game) {
        game.getCompany();
        game.getCategory();
        game.getTags().size();
        game.getPromotions().size();
        game.getRatings().size();
        game.getOwnedItems().size();
        game.getForums().size();
        game.getGameAccounts().size();
    }

         

   
}
