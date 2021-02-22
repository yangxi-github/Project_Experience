package jsf.managedbean;

import ejb.session.stateless.CompanySessionBeanLocal;
import ejb.session.stateless.SystemAdminSessionBeanLocal;
import ejb.session.stateless.UserSessionBeanLocal;
import entity.Company;
import entity.SystemAdmin;
import entity.User;
import java.io.IOException;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.persistence.NoResultException;
import javax.servlet.http.HttpSession;
import util.exception.InvalidLoginCredentialException;

@Named(value = "loginManagedBean")
@RequestScoped

public class LoginManagedBean {

    @EJB
    private UserSessionBeanLocal userSessionBean;

    @EJB
    private CompanySessionBeanLocal companySessionBeanLocal;
    @EJB
    private SystemAdminSessionBeanLocal systemAdminSessionBeanLocal;

    private String username;
    private String password;

    public LoginManagedBean() {
    }

    public void login(ActionEvent event) throws IOException {
        SystemAdmin currentSystemAdmin = null;
        Company currentCompany = null;
        User user;
        
        try{
        user = userSessionBean.userLogin(username, password);
        if (user == null) {
            System.out.println("**********************************did you catch error???");
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                            "Sorry you have entered the wrong username or password", null));      
            return;
        }
    

        FacesContext.getCurrentInstance().getExternalContext().getSession(true);
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("isLogin", true);
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("user", user);

        if (user instanceof SystemAdmin) {
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("systemAdmin", user);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("successfully logged in as system admin"));
            FacesContext.getCurrentInstance().getExternalContext().redirect(FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath() + "/index.xhtml");
        } else if (user instanceof Company) {
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("company", user);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("successfully logged in as company: " + user.getUsername()));
            FacesContext.getCurrentInstance().getExternalContext().redirect(FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath() + "/index.xhtml");
        }
       }catch(InvalidLoginCredentialException ex)
        {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Invalid login credential: " + ex.getMessage(), null));
        }
        
       

    }

    public void logout(ActionEvent event) throws IOException {
        ((HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true)).invalidate();
        FacesContext.getCurrentInstance().getExternalContext().redirect(FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath() + "/index.xhtml");
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
