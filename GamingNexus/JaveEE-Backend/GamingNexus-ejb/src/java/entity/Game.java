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

/**
 *
 * @author jin yichen
 */
@Entity
public class Game extends Product implements Serializable {

    
    private Boolean parentAdvisory;
    private List<String> gamePicturesURLs;
    private List<String> gameTrailersURLS;
    


    @OneToMany(mappedBy = "game")
    private List<GameAccount> gameAccounts;

    public Game() {
        super();
        gameAccounts = new ArrayList<>();
    }
    
    public Game(Boolean parentAdvisory, String headerImage, String videoLink, String name, String description, String computerRequirements, double price, 
            double averageRating, LocalDate releaseDate, long sales) {
        
        super(name, description, computerRequirements, price, averageRating, releaseDate, sales, headerImage, videoLink);
        this.parentAdvisory = parentAdvisory;
    }


    /**
     * @return the gamePicturesURLs
     */
    public List<String> getGamePicturesURLs() {
        return gamePicturesURLs;
    }

    /**
     * @param gamePicturesURLs the gamePicturesURLs to set
     */
    public void setGamePicturesURLs(List<String> gamePicturesURLs) {
        this.gamePicturesURLs = gamePicturesURLs;
    }

    /**
     * @return the gameTrailersURLS
     */
    public List<String> getGameTrailersURLS() {
        return gameTrailersURLS;
    }

    /**
     * @param gameTrailersURLS the gameTrailersURLS to set
     */
    public void setGameTrailersURLS(List<String> gameTrailersURLS) {
        this.gameTrailersURLS = gameTrailersURLS;
    }

    /**
     * @return the gameAccounts
     */
    public List<GameAccount> getGameAccounts() {
        return gameAccounts;
    }

    /**
     * @param gameAccounts the gameAccounts to set
     */
    public void setGameAccounts(List<GameAccount> gameAccounts) {
        this.gameAccounts = gameAccounts;
    }



    /**
     * @return the parentAdvisory
     */
    public Boolean getParentAdvisory() {
        return parentAdvisory;
    }

    /**
     * @param parentAdvisory the parentAdvisory to set
     */
    public void setParentAdvisory(Boolean parentAdvisory) {
        this.parentAdvisory = parentAdvisory;
    }



}
