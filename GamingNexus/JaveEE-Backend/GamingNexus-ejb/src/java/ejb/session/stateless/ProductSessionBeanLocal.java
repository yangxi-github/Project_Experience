/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.Product;
import java.util.List;
import javax.ejb.Local;
import util.exception.CategoryNotFoundException;
import util.exception.ProductNotFoundException;
import util.exception.TagNotFoundException;
import util.exception.UpdateProductException;

/**
 *
 * @author root
 */
@Local
public interface ProductSessionBeanLocal {

    public Product retrieveProductById(Long productId) throws ProductNotFoundException;
    
    public List<Product> retrieveAllProducts();

    public void deleteProduct(Product product);

    public List<Product> searchProductsByName(String searchString);

    public List<Product> retrieveProductByCategoryId(Long categoryId);

    public void updateProduct(Product product, Long categoryId, List<Long> tagIds) throws ProductNotFoundException, CategoryNotFoundException, TagNotFoundException, UpdateProductException;

    
}
