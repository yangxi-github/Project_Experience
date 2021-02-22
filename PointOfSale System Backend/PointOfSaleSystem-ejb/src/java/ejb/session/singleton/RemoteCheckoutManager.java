package ejb.session.singleton;

import datamodel.ws.RemoteCheckoutController;
import ejb.session.stateful.CheckoutSessionBeanLocal;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.UUID;
import javax.ejb.Singleton;
import javax.ejb.LocalBean;
import javax.ejb.Lock;
import javax.ejb.LockType;
import javax.ejb.Schedule;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import util.exception.RemoteCheckoutControllerNotFoundException;



@Singleton
@LocalBean

public class RemoteCheckoutManager 
{
    private List<RemoteCheckoutController> remoteCheckoutControllers;

    
    
    public RemoteCheckoutManager() 
    {
        remoteCheckoutControllers = new ArrayList<>();
    }
    
    
    
    @Lock(LockType.WRITE)
    public String createNewRemoteCheckoutController()
    {        
        CheckoutSessionBeanLocal checkoutControllerLocal = lookupCheckoutSessionBeanLocal();
        
        if(checkoutControllerLocal != null)
        {
            String sessionKey = UUID.randomUUID().toString();
            Date expiryDateTime = new Date();
            GregorianCalendar gregorianCalendar = new GregorianCalendar();
            gregorianCalendar.setTime(expiryDateTime);
            gregorianCalendar.add(GregorianCalendar.MINUTE, 5);
            expiryDateTime = gregorianCalendar.getTime();
            RemoteCheckoutController remoteCheckoutController = new RemoteCheckoutController(sessionKey, checkoutControllerLocal, expiryDateTime);
            remoteCheckoutControllers.add(remoteCheckoutController);
            
            System.out.println("********** RemoteCheckoutManager.createNewRemoteCheckoutController(): " + sessionKey);
            
            return sessionKey;
        }
        else
        {
            return null;
        }
    }
    
    
    
    @Lock(LockType.READ)
    public CheckoutSessionBeanLocal retrieveRemoteCheckoutController(String sessionKey) throws RemoteCheckoutControllerNotFoundException
    {
        for(RemoteCheckoutController remoteCheckoutController:remoteCheckoutControllers)
        {
            if(remoteCheckoutController.getSessionKey().equals(sessionKey))
            {
                return remoteCheckoutController.getCheckoutSessionBeanLocal();
            }
        }
        
        throw new RemoteCheckoutControllerNotFoundException("The remote checkout session " + sessionKey + " either does not exist or has expired");
    }
    
    
    
    @Lock(LockType.WRITE)
    public void removeRemoteCheckoutController(String sessionKey)
    {
        RemoteCheckoutController remoteCheckoutControllerToRemove = null;
        
        for(RemoteCheckoutController remoteCheckoutController:remoteCheckoutControllers)
        {
            if(remoteCheckoutController.getSessionKey().equals(sessionKey))
            {
                remoteCheckoutControllerToRemove = remoteCheckoutController;
                
                break;
            }
        }
        
        if(remoteCheckoutControllerToRemove != null)
        {
            remoteCheckoutControllerToRemove.getCheckoutSessionBeanLocal().remove();
            remoteCheckoutControllerToRemove.setCheckoutSessionBeanLocal(null);
            remoteCheckoutControllers.remove(remoteCheckoutControllerToRemove);
        }
    }
    
    
    
    @Schedule(hour = "*", minute = "*/1")
    @Lock(LockType.WRITE)
    public void removeExpiredRemoteCheckoutControllers()
    {
        System.out.println("********** RemoteCheckoutManager.removeExpiredRemoteCheckoutControllers()");
        
        List<RemoteCheckoutController> expiredRemoteCheckoutControllers = new ArrayList<>();
        
        for(RemoteCheckoutController remoteCheckoutController:remoteCheckoutControllers)
        {
            System.out.println("********** Checking: " + remoteCheckoutController.getSessionKey() + "; " + remoteCheckoutController.getExpiryDateTime());
            
            if(remoteCheckoutController.getExpiryDateTime().compareTo(new Date()) < 0)
            {
                expiredRemoteCheckoutControllers.add(remoteCheckoutController);
            }
        }
        
        if(!expiredRemoteCheckoutControllers.isEmpty())
        {
            remoteCheckoutControllers.removeAll(expiredRemoteCheckoutControllers);
        }
    }
    
    
    
    private CheckoutSessionBeanLocal lookupCheckoutSessionBeanLocal()
    {
        try
        {
            Context c = new InitialContext();
            CheckoutSessionBeanLocal checkoutControllerLocal = (CheckoutSessionBeanLocal)c.lookup("java:module/CheckoutSessionBean!ejb.session.stateful.CheckoutSessionBeanLocal");
            
            return checkoutControllerLocal;
        }
        catch(NamingException ex)
        {
            ex.printStackTrace();
            return null;
        }
    }
}
