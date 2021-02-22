/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.restful.model;

import entity.Category;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author chenli
 */
public class RetrieveAllCategoriesRsp {
    private List<Category> categories;

    public RetrieveAllCategoriesRsp() {
    }

    public RetrieveAllCategoriesRsp(List<Category> categories) {
        this.categories = categories;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }
    
    
    
}
