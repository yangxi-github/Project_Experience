/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.User;
import javax.ejb.Local;
import javax.persistence.NoResultException;
import util.exception.InvalidLoginCredentialException;
import util.exception.UserNotFoundException;

/**
 *
 * @author root
 */
@Local
public interface UserSessionBeanLocal {

     public User retrieveUserByUsername(String username) throws UserNotFoundException;
     public User userLogin(String username, String password) throws InvalidLoginCredentialException;
    
}
