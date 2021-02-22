/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.Company;
import java.util.List;
import javax.ejb.Local;
import util.exception.CompanyNotFoundException;
import util.exception.CompanyUsernameExistException;
import util.exception.InvalidLoginCredentialException;
import util.exception.UnknownPersistenceException;

/**
 *
 * @author jinyichen
 */
@Local
public interface CompanySessionBeanLocal {

    public Company createNewCompany(Company newCompany) throws UnknownPersistenceException, CompanyUsernameExistException;

    public List<Company> retrieveAllCompanies();

    public Company retrieveCompanyById(Long companyId) throws CompanyNotFoundException;

    public Company retrieveCompanyByUsername(String username) throws CompanyNotFoundException;

    public Company companyLogin(String username, String password) throws InvalidLoginCredentialException;

    public void deleteCompany(Long companyId) throws CompanyNotFoundException;

    public void updateCompany(Company company) throws CompanyNotFoundException;
    
}
