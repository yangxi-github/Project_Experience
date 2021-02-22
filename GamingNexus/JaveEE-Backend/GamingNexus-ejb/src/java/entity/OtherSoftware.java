/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 *
 * @author jinyichen
 */
@Entity
public class OtherSoftware extends Product implements Serializable {


    public OtherSoftware() {
        super();
    }
    
    public OtherSoftware(String name, String description, String computerRequirements, double price, double averageRating, LocalDate releaseDate, long sales, String headerImage, String videoLink) {
        super(name, description, computerRequirements, price, averageRating, releaseDate, sales, headerImage, videoLink);
    }
   
    
}
