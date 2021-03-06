/* To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.managedbean;

import ejb.session.stateless.SystemAdminSessionBeanLocal;
import entity.SystemAdmin;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import util.exception.SystemAdminNotFoundException;
import util.exception.SystemAdminUsernameExistException;
import util.exception.UnknownPersistenceException;

/**
 *
 * @author Jin yichen
 */
@Named(value = "systemAdminManagementManagedBean")
@ViewScoped
public class SystemAdminManagementManagedBean implements Serializable {

    @EJB
    private SystemAdminSessionBeanLocal systemAdminSessionBeanLocal;

    private List<SystemAdmin> systemAdmins;
    private List<SystemAdmin> filteredSystemAdmins;

    private String updatedPassword;
    
    @Inject
    private ViewSystemAdminManagedBean viewSystemAdminManagedBean;

    private SystemAdmin systemAdminToView;

    private SystemAdmin newSystemAdmin;
    private SystemAdmin selectedSystemAdminToUpdate;
    
    private SystemAdmin currentSystemAdmin;

    public SystemAdminManagementManagedBean() {
        newSystemAdmin = new SystemAdmin();
        systemAdminToView = new SystemAdmin();
    }

    @PostConstruct
    public void postConstruct() {
        setSystemAdmins(systemAdminSessionBeanLocal.retrieveAllSystemAdmins());
    }

    public void viewSystemAdminDetails(ActionEvent event) throws IOException {
        Long systemAdminIdToView = (Long) event.getComponent().getAttributes().get("systemAdminId");
        FacesContext.getCurrentInstance().getExternalContext().getFlash().put("systemAdminIdToView", systemAdminIdToView);
        FacesContext.getCurrentInstance().getExternalContext().redirect("viewSystemAdminDetails.xhtml");
    }

    public SystemAdmin getSystemAdminToView() {
        return systemAdminToView;
    }

    public void createNewSystemAdmin(ActionEvent event) throws SystemAdminUsernameExistException {
        System.out.println("************Entered createNewSystemAdmin Method");
        try {
            SystemAdmin admin = systemAdminSessionBeanLocal.createNewSystemAdmin(newSystemAdmin);
            systemAdmins.add(admin);

            if (filteredSystemAdmins != null) {
                filteredSystemAdmins.add(admin);
            }

            newSystemAdmin = new SystemAdmin();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "New System Admin created successfully (Admin ID: " + admin.getUserId() + ")", null));
        } catch (UnknownPersistenceException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occurred while creating a new system admin" + ex.getMessage(), null));
        }
    }

    public void doUpdateSystemAdmin(ActionEvent event) {
        selectedSystemAdminToUpdate = (SystemAdmin) event.getComponent().getAttributes().get("systemAdminToUpdate");

    }

    public void updateSystemAdmin(ActionEvent event) {

        try {
            selectedSystemAdminToUpdate.setUpdatedPassword(updatedPassword);
            
            systemAdminSessionBeanLocal.updateSystemAdmin(selectedSystemAdminToUpdate);
            setSelectedSystemAdminToUpdate(new SystemAdmin());
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "System Admin updated successfully", null));
        } catch (SystemAdminNotFoundException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occurred while updating system admin: " + ex.getMessage(), null));
        } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An unexpected error has occurred: " + ex.getMessage(), null));
        }
    }

    public void deleteSystemAdmin(ActionEvent event) {
        try {
            SystemAdmin systemAdminToDelete = (SystemAdmin) event.getComponent().getAttributes().get("systemAdminToDelete");
            systemAdminSessionBeanLocal.deleteSystemAdmin(systemAdminToDelete.getUserId());
            systemAdmins.remove(systemAdminToDelete);

            if (filteredSystemAdmins != null) {
                filteredSystemAdmins.remove(systemAdminToDelete);
            }

            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "System Admin deleted successfully", null));
        } catch (SystemAdminNotFoundException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occurred while deleting product: " + ex.getMessage(), null));
        } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An unexpected error has occurred: " + ex.getMessage(), null));
        }
    }

    /**
     * @return the filteredSystemAdmins
     */
    public List<SystemAdmin> getFilteredSystemAdmins() {
        return filteredSystemAdmins;
    }

    /**
     * @param filteredSystemAdmins the filteredSystemAdmins to set
     */
    public void setFilteredSystemAdmins(List<SystemAdmin> filteredSystemAdmins) {
        this.filteredSystemAdmins = filteredSystemAdmins;
    }

    /**
     * @return the newSystemAdmin
     */
    public SystemAdmin getNewSystemAdmin() {
        return newSystemAdmin;
    }

    /**
     * @param newSystemAdmin the newSystemAdmin to set
     */
    public void setNewSystemAdmin(SystemAdmin newSystemAdmin) {
        this.newSystemAdmin = newSystemAdmin;
    }

    /**
     * @return the selectedSystemAdminToUpdate
     */
    public SystemAdmin getSelectedSystemAdminToUpdate() {
        return selectedSystemAdminToUpdate;
    }

    /**
     * @param selectedSystemAdminToUpdate the selectedSystemAdminToUpdate to set
     */
    public void setSelectedSystemAdminToUpdate(SystemAdmin selectedSystemAdminToUpdate) {
        this.selectedSystemAdminToUpdate = selectedSystemAdminToUpdate;
    }

    /**
     * @return the systemAdmins
     */
    public List<SystemAdmin> getSystemAdmins() {
        return systemAdmins;
    }

    /**
     * @param systemAdmins the systemAdmins to set
     */
    public void setSystemAdmins(List<SystemAdmin> systemAdmins) {
        this.systemAdmins = systemAdmins;
    }

    public SystemAdminSessionBeanLocal getSystemAdminSessionBeanLocal() {
        return systemAdminSessionBeanLocal;
    }

    public void setSystemAdminSessionBeanLocal(SystemAdminSessionBeanLocal systemAdminSessionBeanLocal) {
        this.systemAdminSessionBeanLocal = systemAdminSessionBeanLocal;
    }

    public void setSystemAdminToView(SystemAdmin systemAdminToView) {
        this.systemAdminToView = systemAdminToView;
    }

    public ViewSystemAdminManagedBean getViewSystemAdminManagedBean() {
        return viewSystemAdminManagedBean;
    }

    public void setViewSystemAdminManagedBean(ViewSystemAdminManagedBean viewSystemAdminManagedBean) {
        this.viewSystemAdminManagedBean = viewSystemAdminManagedBean;
    }

    public String getUpdatedPassword() {
        return updatedPassword;
    }

    public void setUpdatedPassword(String updatedPassword) {
        this.updatedPassword = updatedPassword;
    }
}
