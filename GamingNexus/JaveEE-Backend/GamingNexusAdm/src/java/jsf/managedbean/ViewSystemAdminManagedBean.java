/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.managedbean;

import entity.SystemAdmin;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.inject.Named;
import javax.faces.view.ViewScoped;

/**
 *
 * @author jin yichen
 */
@Named(value = "viewSystemAdminManagedBean")
@ViewScoped
public class ViewSystemAdminManagedBean implements Serializable {

    private SystemAdmin systemAdminToView;

    public ViewSystemAdminManagedBean() {
        systemAdminToView = new SystemAdmin();
    }

    @PostConstruct
    public void postConstruct() {
    }

    public SystemAdmin getSystemAdminToView() {
        return systemAdminToView;
    }

    public void setSystemAdminToView(SystemAdmin systemAdminToView) {
        System.out.println(systemAdminToView.getUserId());
        
        this.systemAdminToView = systemAdminToView;
    }

}
