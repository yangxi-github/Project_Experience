/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.Company;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import util.exception.CompanyNotFoundException;
import util.exception.UnknownPersistenceException;
import util.exception.CompanyUsernameExistException;
import util.exception.InvalidLoginCredentialException;
import util.security.CryptographicHelper;

/**
 *
 * @author jinyichen
 */
@Stateless
public class CompanySessionBean implements CompanySessionBeanLocal {

    @PersistenceContext(unitName = "GamingNexus-ejbPU")
    private EntityManager em;

    public CompanySessionBean() {

    }
    
    @Override
    public Company createNewCompany(Company newCompany) throws UnknownPersistenceException, CompanyUsernameExistException {
        try {
            em.persist(newCompany);
            em.flush();

            return newCompany;
        } catch (PersistenceException ex) {
            if (ex.getCause() != null && ex.getCause().getClass().getName().equals("org.eclipse.persistence.exceptions.DatabaseException")) {
                if (ex.getCause().getCause() != null && ex.getCause().getCause().getClass().getName().equals("java.sql.SQLIntegrityConstraintViolationException")) {
                    throw new CompanyUsernameExistException();
                } else {
                    throw new UnknownPersistenceException(ex.getMessage());
                }
            } else {
                throw new UnknownPersistenceException(ex.getMessage());
            }
        }
    }

    @Override
    public List<Company> retrieveAllCompanies() {
        Query query = em.createQuery("SELECT c FROM Company c");

        return query.getResultList();
    }

    @Override
    public Company retrieveCompanyById(Long companyId) throws CompanyNotFoundException {
        Company company = em.find(Company.class, companyId);

        if (company != null) {
            return company;
        } else {
            throw new CompanyNotFoundException("Company ID " + companyId + " does not exist!");
        }
    }

    @Override
    public Company retrieveCompanyByUsername(String username) throws CompanyNotFoundException {
        Query query = em.createQuery("SELECT c FROM Company c WHERE c.username = :inUsername");
        query.setParameter("inUsername", username);

        try {
            return (Company) query.getSingleResult();
        } catch (NoResultException | NonUniqueResultException ex) {
            throw new CompanyNotFoundException("Companu Username " + username + " does not exist!");
        }
    }

    @Override
    public Company companyLogin(String username, String password) throws InvalidLoginCredentialException {
        try {
            Company company = retrieveCompanyByUsername(username);
            String passwordHash = CryptographicHelper.getInstance().byteArrayToHexString(CryptographicHelper.getInstance().doMD5Hashing(password + company.getSalt()));

            if (company.getPassword().equals(passwordHash)) {
                return company;
            } else {
                throw new InvalidLoginCredentialException("Username does not exist or invalid password!");
            }
        } catch (CompanyNotFoundException ex) {
            throw new InvalidLoginCredentialException("Username does not exist or invalid password!");
        }
    }

    @Override
    public void updateCompany(Company company) throws CompanyNotFoundException {
        if (company != null && company.getUserId() != null) {

            Company companyToUpdate = retrieveCompanyById(company.getUserId());

            companyToUpdate.setAddress(company.getAddress());
            companyToUpdate.setPhoneNumber(company.getPhoneNumber());
            companyToUpdate.setEmail(company.getEmail());
            companyToUpdate.setProducts(company.getProducts());
        } else {
            throw new CompanyNotFoundException("Company ID not provided for SystemAdmin to be updated");
        }
    }

    @Override
    public void deleteCompany(Long companyId) throws CompanyNotFoundException {
        Company companyToRemove = retrieveCompanyById(companyId);

        em.remove(companyToRemove);

    }

}
