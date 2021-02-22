/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.SystemAdmin;
import java.util.List;
import javax.ejb.Local;
import util.exception.InvalidLoginCredentialException;
import util.exception.SystemAdminNotFoundException;
import util.exception.SystemAdminUsernameExistException;
import util.exception.UnknownPersistenceException;

/**
 *
 * @author ufoya
 */
@Local
public interface SystemAdminSessionBeanLocal {

    public SystemAdmin createNewSystemAdmin(SystemAdmin newSystemAdmin) throws SystemAdminUsernameExistException, UnknownPersistenceException;

    public List<SystemAdmin> retrieveAllSystemAdmins();

    public SystemAdmin retrieveSystemAdminById(Long systemAdminId) throws SystemAdminNotFoundException;

    public SystemAdmin retrieveSystemAdminByUsername(String username) throws SystemAdminNotFoundException;

    public SystemAdmin systemAdminLogin(String username, String password) throws InvalidLoginCredentialException;

    public void updateSystemAdmin(SystemAdmin systemAdmin) throws SystemAdminNotFoundException;

    public void deleteSystemAdmin(Long systemAdminId) throws SystemAdminNotFoundException;
    
}
