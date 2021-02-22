/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.validation.constraints.Size;

/**
 * this
 *
 * @author root
 */
@Entity
public class Hardware extends Product implements Serializable {

    @Size(min = 0, max = 5000)
    private String warrantyDescription;
    @Size(min = 0, max = 5000)
    private String technicalspecification;
    @Size(min = 0, max = 100)
    private String manufacturingCountry;
    
    private int stock;
    
    
    @OneToMany(mappedBy = "hardware")
    private List<Deliverables> deliverables;

    public Hardware() {
        super();
        deliverables = new ArrayList<>();
    }

    public Hardware(String warrantyDescription, String technicalspecification, String manufacturingCountry, String name, String description, 
            double price, double averageRating, LocalDate releaseDate, long sales, String headerImage, String videoLink, int stock) {
        
        super(name, description, price, averageRating, releaseDate, sales, headerImage, videoLink);
        this.warrantyDescription = warrantyDescription;
        this.technicalspecification = technicalspecification;
        this.manufacturingCountry = manufacturingCountry;
        this.stock = stock;
    }
    
    /**
     * @return the technicalspecification
     */
    public String getTechnicalspecification() {
        return technicalspecification;
    }

    /**
     * @param technicalspecification the technicalspecification to set
     */
    public void setTechnicalspecification(String technicalspecification) {
        this.technicalspecification = technicalspecification;
    }



    /**
     * @return the deliverables
     */
    public List<Deliverables> getDeliverables() {
        return deliverables;
    }

    /**
     * @param deliverables the deliverables to set
     */
    public void setDeliverables(List<Deliverables> deliverables) {
        this.deliverables = deliverables;
    }

    /**
     * @return the warrantyDescription
     */
    public String getWarrantyDescription() {
        return warrantyDescription;
    }

    /**
     * @param warrantyDescription the warrantyDescription to set
     */
    public void setWarrantyDescription(String warrantyDescription) {
        this.warrantyDescription = warrantyDescription;
    }

    /**
     * @return the manufacturingCountry
     */
    public String getManufacturingCountry() {
        return manufacturingCountry;
    }

    /**
     * @param manufacturingCountry the manufacturingCountry to set
     */
    public void setManufacturingCountry(String manufacturingCountry) {
        this.manufacturingCountry = manufacturingCountry;
    }

    /**
     * @return the stock
     */
    public int getStock() {
        return stock;
    }

    /**
     * @param stock the stock to set
     */
    public void setStock(int stock) {
        this.stock = stock;
    }

}
