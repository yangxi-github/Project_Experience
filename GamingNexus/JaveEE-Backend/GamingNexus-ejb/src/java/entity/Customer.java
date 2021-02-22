/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.validation.constraints.Size;

/**
 *
 * @author jinyichen 
 */
@Entity
public class Customer extends User implements Serializable {

    @Size(min = 1, max = 100)
    private String currentGamePlaying;
    private boolean isBanned = false;
    // @Future
    private Date unbanDate;
    @Size(min = 5, max = 100)
    private String securityQuestion;
    @Size(min = 5, max = 100)
    private String securityAnswer;

    private LocalDate birthday;

    private String gender;

    @OneToMany(mappedBy = "customer")
    private List<OwnedItem> ownedItems;
    @OneToMany(mappedBy = "customer")
    private List<Rating> ratings;
    @OneToMany
    private List<Customer> otherCustomers;
    @OneToMany(mappedBy = "customer")
    private List<GameAccount> gameAccounts;
    @OneToMany(mappedBy = "customer")
    private List<Chat> chats;
    @OneToMany(mappedBy = "customer")
    private List<Deliverables> listOfDeliverables;

    @OneToMany(mappedBy = "customer")
    private List<SaleTransaction> saleTransactions;

    public Customer() {
        super();
        ownedItems = new ArrayList<>();
        ratings = new ArrayList<>();
        otherCustomers = new ArrayList<>();
        gameAccounts = new ArrayList<>();
        chats = new ArrayList<>();
        listOfDeliverables = new ArrayList<>();
        saleTransactions = new ArrayList<>();
    }

    public Customer(String phoneNumber, String address, String email, String country, String username, String password, String birthday, String gender) {
        super(phoneNumber, address, email, country, username, password);

        this.birthday = LocalDate.parse(birthday);
        this.gender = gender;
        ownedItems = new ArrayList<>();
        ratings = new ArrayList<>();
        otherCustomers = new ArrayList<>();
        gameAccounts = new ArrayList<>();
        chats = new ArrayList<>();
        listOfDeliverables = new ArrayList<>();
        saleTransactions = new ArrayList<>();
    }

    public Customer(String currentGamePlaying, Date unbanDate, String securityQuestion, String securityAnswer, String phoneNumber,
            String address, String email, String country, String username, String password) {
        super(phoneNumber, address, email, country, username, password);
        this.currentGamePlaying = currentGamePlaying;
        this.unbanDate = unbanDate;
        this.securityQuestion = securityQuestion;
        this.securityAnswer = securityAnswer;
    }

    /**
     * @return the currentGamePlaying
     */
    public String getCurrentGamePlaying() {
        return currentGamePlaying;
    }

    /**
     * @param currentGamePlaying the currentGamePlaying to set
     */
    public void setCurrentGamePlaying(String currentGamePlaying) {
        this.currentGamePlaying = currentGamePlaying;
    }

    /**
     * @return the isBanned
     */
    public boolean isIsBanned() {
        return isBanned;
    }

    /**
     * @param isBanned the isBanned to set
     */
    public void setIsBanned(boolean isBanned) {
        this.isBanned = isBanned;
    }

    /**
     * @return the unbanDate
     */
    public Date getUnbanDate() {
        return unbanDate;
    }

    /**
     * @param unbanDate the unbanDate to set
     */
    public void setUnbanDate(Date unbanDate) {
        this.unbanDate = unbanDate;
    }

    /**
     * @return the securityQuestion
     */
    public String getSecurityQuestion() {
        return securityQuestion;
    }

    /**
     * @param securityQuestion the securityQuestion to set
     */
    public void setSecurityQuestion(String securityQuestion) {
        this.securityQuestion = securityQuestion;
    }

    /**
     * @return the securityAnswer
     */
    public String getSecurityAnswer() {
        return securityAnswer;
    }

    /**
     * @param securityAnswer the securityAnswer to set
     */
    public void setSecurityAnswer(String securityAnswer) {
        this.securityAnswer = securityAnswer;
    }

    /**
     * @return the ratings
     */
    public List<Rating> getRatings() {
        return ratings;
    }

    /**
     * @param ratings the ratings to set
     */
    public void setRatings(List<Rating> ratings) {
        this.ratings = ratings;
    }

    /**
     * @return the ownedItems
     */
    public List<OwnedItem> getOwnedItems() {
        return ownedItems;
    }

    /**
     * @param ownedItems the ownedItems to set
     */
    public void setOwnedItems(List<OwnedItem> ownedItems) {
        this.ownedItems = ownedItems;
    }

    /**
     * @return the otherCustomers
     */
    public List<Customer> getOtherCustomers() {
        return otherCustomers;
    }

    /**
     * @param otherCustomers the otherCustomers to set
     */
    public void setOtherCustomers(List<Customer> otherCustomers) {
        this.otherCustomers = otherCustomers;
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
     * @return the chats
     */
    public List<Chat> getChats() {
        return chats;
    }

    /**
     * @param chats the chats to set
     */
    public void setChats(List<Chat> chats) {
        this.chats = chats;
    }

    /**
     * @return the listOfDeliverables
     */
    public List<Deliverables> getListOfDeliverables() {
        return listOfDeliverables;
    }

    /**
     * @param listOfDeliverables the listOfDeliverables to set
     */
    public void setListOfDeliverables(List<Deliverables> listOfDeliverables) {
        this.listOfDeliverables = listOfDeliverables;
    }

    public List<SaleTransaction> getSaleTransactions() {
        return saleTransactions;
    }

    public void setSaleTransactions(List<SaleTransaction> saleTransactions) {
        this.saleTransactions = saleTransactions;
    }

    /**
     * @return the birthday
     */
    public LocalDate getBirthday() {
        return birthday;
    }

    /**
     * @param birthday the birthday to set
     */
    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }

    /**
     * @return the gender
     */
    public String getGender() {
        return gender;
    }

    /**
     * @param gender the gender to set
     */
    public void setGender(String gender) {
        this.gender = gender;
    }
}
