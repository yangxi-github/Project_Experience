/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.sql.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;

/**
 *
 * @author jin yichen
 */
@Entity
public class GameAccount implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long gameAccountID;
    @NotNull
    @Size(min = 1, max = 30)
    private String username;
    @NotNull
    @Past
    private Date dateOfCreation;
    @OneToOne(optional = false)
    @JoinColumn(nullable = false)
    private Customer customer;
    @OneToOne(optional = false)
    @JoinColumn(nullable = false)
    private Game game;

    public GameAccount() {
    }

    public GameAccount(String username, Date dateOfCreation, Customer customer, Game game) {
        this();
        this.username = username;
        this.dateOfCreation = dateOfCreation;
        this.customer = customer;
        this.game = game;
    }

    public Long getGameAccountID() {
        return gameAccountID;
    }

    public void setGameAccountID(Long gameAccountID) {
        this.gameAccountID = gameAccountID;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (gameAccountID != null ? gameAccountID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the gameAccountID fields are not set
        if (!(object instanceof GameAccount)) {
            return false;
        }
        GameAccount other = (GameAccount) object;
        if ((this.gameAccountID == null && other.gameAccountID != null) || (this.gameAccountID != null && !this.gameAccountID.equals(other.gameAccountID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ejb.entity.GameAccount[ id=" + gameAccountID + " ]";
    }

    /**
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * @param username the username to set
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * @return the dateOfCreation
     */
    public Date getDateOfCreation() {
        return dateOfCreation;
    }

    /**
     * @param dateOfCreation the dateOfCreation to set
     */
    public void setDateOfCreation(Date dateOfCreation) {
        this.dateOfCreation = dateOfCreation;
    }

    /**
     * @return the customer
     */
    public Customer getCustomer() {
        return customer;
    }

    /**
     * @param customer the customer to set
     */
    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    /**
     * @return the game
     */
    public Game getGame() {
        return game;
    }

    /**
     * @param game the game to set
     */
    public void setGame(Game game) {
        this.game = game;
    }

}
