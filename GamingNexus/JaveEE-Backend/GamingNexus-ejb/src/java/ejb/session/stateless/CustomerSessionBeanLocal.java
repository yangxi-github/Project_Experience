/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.Customer;
import java.util.List;
import javax.ejb.Local;
import util.exception.CustomerNotFoundException;
import util.exception.CustomerUsernameExistException;
import util.exception.InvalidLoginCredentialException;
import util.exception.UnknownPersistenceException;
import util.exception.UpdateCustomerException;

/**
 *
 * @author chenli
 */
@Local
public interface CustomerSessionBeanLocal {

    public Customer createCustomer(Customer newCustomer) throws UnknownPersistenceException, CustomerUsernameExistException;

    public List<Customer> retrieveAllCustomers();

    public Customer retrieveCustomerById(Long customerId) throws CustomerNotFoundException;

    public Customer retrieveCustomerByUsername(String username) throws CustomerNotFoundException;

    public Customer customerLogin(String username, String password) throws InvalidLoginCredentialException;

    public void updateCustomer(Customer customer) throws CustomerNotFoundException;

    public void deleteCustomer(Long customerId) throws CustomerNotFoundException;
    
}
