/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.restful.model;

import entity.Category;
import java.util.List;

/**
 *
 * @author 63216
 */
public class RetrieveAllHardwareCategoriesRsp {
    private List<Category> hardwareCategories;

    public RetrieveAllHardwareCategoriesRsp() {
    }

    public RetrieveAllHardwareCategoriesRsp(List<Category> hardwareCategories) {
        this.hardwareCategories = hardwareCategories;
    }

    /**
     * @return the hardwareCategories
     */
    public List<Category> getHardwareCategories() {
        return hardwareCategories;
    }

    /**
     * @param hardwareCategories the hardwareCategories to set
     */
    public void setHardwareCategories(List<Category> hardwareCategories) {
        this.hardwareCategories = hardwareCategories;
    }
    
    
}
