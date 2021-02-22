/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import javax.persistence.Entity;

/**
 *
 * @author Jin Yichen
 */
@Entity
public class SystemAdmin extends User implements Serializable {

    public SystemAdmin() {
    }

    public SystemAdmin(String phoneNumber, String address, String email, String country, String username, String password) {
        super(phoneNumber, address, email, country, username, password);
    }

}
