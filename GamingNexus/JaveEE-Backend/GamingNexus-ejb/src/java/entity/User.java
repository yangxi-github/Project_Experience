/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import util.security.CryptographicHelper;

/**
 *
 * @author root
 */
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class User implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long userId;
    @NotNull
    protected String phoneNumber;
    @Size(min = 5, max = 200)
    @NotNull
    protected String address;
    @NotNull
    @Size(min = 6, max = 100)
    @Column(unique = true)
    protected String email;
    @NotNull
    @Size(min = 1, max = 50)
    protected String country;
    @Size(min = 1, max = 100)
    @NotNull
    @Column(unique = true)
    protected String username;
    @Column(columnDefinition = "CHAR(32) NOT NULL")
    @NotNull
    @Size(min = 6)
    protected String password;
    @Column(columnDefinition = "CHAR(32) NOT NULL")
    private String salt;
    @Column(columnDefinition = "CHAR(32)")
    @Size(min = 6)
    private String updatedPassword;
    
    protected String profilePictureURL;//https://stackoverflow.com/questions/29208007/what-is-the-data-type-for-images-in-java
    protected LocalDateTime lastOnline;

    

    public User() {
        this.salt = CryptographicHelper.getInstance().generateRandomString(32);
  
        this.lastOnline = LocalDateTime.now();
    }

    public User(String phoneNumber, String address, String email, String country, String username, String password) {
        this();
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.email = email;
        this.country = country;
        this.username = username;
        this.password = password;
        setPassword(password);
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (userId != null ? userId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the userId fields are not set
        if (!(object instanceof User)) {
            return false;
        }
        User other = (User) object;
        if ((this.userId == null && other.userId != null) || (this.userId != null && !this.userId.equals(other.userId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ejb.entity.User[ id=" + userId + " ]";
    }

    /**
     * @return the phoneNumber
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * @param phoneNumber the phoneNumber to set
     */
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    /**
     * @return the address
     */
    public String getAddress() {
        return address;
    }

    /**
     * @param address the address to set
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
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
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        if (password != null) {
            this.password = CryptographicHelper.getInstance().byteArrayToHexString(CryptographicHelper.getInstance().doMD5Hashing(password + this.salt));
        } else {
            this.password = null;
        }
        System.out.println(this.salt);
    }

    /**
     * @return the lastOnline
     */
    public LocalDateTime getLastOnline() {
        return lastOnline;
    }

    /**
     * @param lastOnline the lastOnline to set
     */
    public void setLastOnline(LocalDateTime lastOnline) {
        this.lastOnline = lastOnline;
    }

    /**
     * @return the country
     */
    public String getCountry() {
        return country;
    }

    /**
     * @param country the country to set
     */
    public void setCountry(String country) {
        this.country = country;
    }

    /**
     * @return the profilePictureURL
     */
    public String getProfilePictureURL() {
        return profilePictureURL;
    }

    /**
     * @param profilePictureURL the profilePictureURL to set
     */
    public void setProfilePictureURL(String profilePictureURL) {
        this.profilePictureURL = profilePictureURL;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public String getUpdatedPassword() {
        return updatedPassword;
    }

    public void setUpdatedPassword(String updatedPassword) {
        this.password = updatedPassword;
    }

}
