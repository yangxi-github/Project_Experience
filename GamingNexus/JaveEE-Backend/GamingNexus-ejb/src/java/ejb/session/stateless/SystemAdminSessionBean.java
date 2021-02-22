/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.SystemAdmin;
import java.util.List;
;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import util.exception.InvalidLoginCredentialException;
import util.exception.SystemAdminNotFoundException;
import util.exception.SystemAdminUsernameExistException;
import util.exception.UnknownPersistenceException;
import util.security.CryptographicHelper;

/**
 *
 * @author Jin Yichen
 */


@Stateless
public class SystemAdminSessionBean implements SystemAdminSessionBeanLocal {

    @PersistenceContext(unitName = "GamingNexus-ejbPU")
    private EntityManager em;

    @Override
    public SystemAdmin createNewSystemAdmin(SystemAdmin newSystemAdmin) throws SystemAdminUsernameExistException, UnknownPersistenceException {
        try {
            em.persist(newSystemAdmin);
            em.flush();

            return newSystemAdmin;

        } catch (PersistenceException ex) {
            if (ex.getCause() != null && ex.getCause().getClass().getName().equals("org.eclipse.persistence.exceptions.DatabaseException")) {
                if (ex.getCause().getCause() != null && ex.getCause().getCause().getClass().getName().equals("java.sql.SQLIntegrityConstraintViolationException")) {
                    throw new SystemAdminUsernameExistException();
                } else {
                    throw new UnknownPersistenceException(ex.getMessage());
                }
            } else {
                throw new UnknownPersistenceException(ex.getMessage());
            }
        }
    }

    @Override
    public List<SystemAdmin> retrieveAllSystemAdmins() {
        Query query = em.createQuery("SELECT s FROM SystemAdmin s");

        return query.getResultList();
    }

    @Override
    public SystemAdmin retrieveSystemAdminById(Long systemAdminId) throws SystemAdminNotFoundException {
        SystemAdmin systemAdmin = em.find(SystemAdmin.class, systemAdminId);

        if (systemAdmin != null) {
            return systemAdmin;
        } else {
            throw new SystemAdminNotFoundException("SystemAdmin ID " + systemAdminId + " does not exist!");
        }
    }

    @Override
    public SystemAdmin retrieveSystemAdminByUsername(String username) throws SystemAdminNotFoundException {
        Query query = em.createQuery("SELECT s FROM SystemAdmin s WHERE s.username = :inUsername");
        query.setParameter("inUsername", username);

        try {
            return (SystemAdmin) query.getSingleResult();
        } catch (NoResultException | NonUniqueResultException ex) {
            throw new SystemAdminNotFoundException("SystemAdmin Username " + username + " does not exist!");
        }
    }

    @Override
    public SystemAdmin systemAdminLogin(String username, String password) throws InvalidLoginCredentialException {
        try {
            SystemAdmin systemAdmin = retrieveSystemAdminByUsername(username);
            String passwordHash = CryptographicHelper.getInstance().byteArrayToHexString(CryptographicHelper.getInstance().doMD5Hashing(password + systemAdmin.getSalt()));

            if (systemAdmin.getPassword().equals(passwordHash)) {
                return systemAdmin;
            } else {
                throw new InvalidLoginCredentialException("Username does not exist or invalid password!");
            }
        } catch (SystemAdminNotFoundException ex) {
            throw new InvalidLoginCredentialException("Username does not exist or invalid password!");
        }
    }

    @Override
    public void updateSystemAdmin(SystemAdmin systemAdmin) throws SystemAdminNotFoundException {
//        if(systemAdmin != null && systemAdmin. != null)
//        {

        SystemAdmin systemAdminToUpdate = retrieveSystemAdminById(systemAdmin.getUserId());

        systemAdminToUpdate.setAddress(systemAdmin.getAddress());
        systemAdminToUpdate.setCountry(systemAdmin.getCountry());
        systemAdminToUpdate.setEmail(systemAdmin.getEmail());
        systemAdminToUpdate.setLastOnline(systemAdmin.getLastOnline());

        if (systemAdmin.getPassword().length() != 32) {
            systemAdminToUpdate.setUpdatedPassword(CryptographicHelper.getInstance().byteArrayToHexString(CryptographicHelper.getInstance().doMD5Hashing(systemAdmin.getPassword() + systemAdminToUpdate.getSalt())));
        }
//            }
//        else
//        {
//            throw new SystemAdminNotFoundException("SystemAdmin ID not provided for SystemAdmin to be updated");
//        }
    }

    public void changePassword(String password, SystemAdmin systemAdminToUpdate) {
        String salt = systemAdminToUpdate.getSalt();
        System.out.println("This sysadmin to update" + systemAdminToUpdate.getUsername() + "'s salt is " + systemAdminToUpdate.getSalt());

    }

    @Override
    public void deleteSystemAdmin(Long systemAdminId) throws SystemAdminNotFoundException {
        SystemAdmin systemAdminToRemove = retrieveSystemAdminById(systemAdminId);

        em.remove(systemAdminToRemove);

    }

}
