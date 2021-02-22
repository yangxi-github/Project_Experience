/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import com.sun.org.apache.xerces.internal.impl.dv.xs.YearMonthDV;
import entity.Customer;
import entity.SaleTransaction;
import entity.SaleTransactionLineItem;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.HashMap;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import util.exception.CreateNewSaleTransactionException;
import util.exception.CustomerNotFoundException;
import util.exception.InvalidLoginCredentialException;
import util.exception.SaleTransactionNotFoundException;

/**
 *
 * @author yangxi
 */
@Stateless
public class SaleTransactionSessionBean implements SaleTransactionSessionBeanLocal {

    @EJB
    private CustomerSessionBeanLocal customerSessionBeanLocal;

    @EJB
    private CompanySessionBeanLocal companySessionBeanLocal;

    @PersistenceContext(unitName = "GamingNexus-ejbPU")
    private EntityManager em;

    public SaleTransactionSessionBean() {
    }

    @Override
    public Long createNewSaleTransaction(Long customerId, SaleTransaction newSaleTransaction) throws CustomerNotFoundException, CreateNewSaleTransactionException {
        if (newSaleTransaction != null) {

            Customer customer = customerSessionBeanLocal.retrieveCustomerById(customerId);
            newSaleTransaction.setCustomer(customer);
            customer.getSaleTransactions().add(newSaleTransaction);
            em.persist(newSaleTransaction);

            for (SaleTransactionLineItem saleTransactionLineItem : newSaleTransaction.getSaleTransactionLineItems()) {
                em.persist(saleTransactionLineItem);
            }

            em.flush();

            return newSaleTransaction.getSaleTransactionId();

        } else {
            throw new CreateNewSaleTransactionException("Sale transaction information not provided");
        }
    }

    @Override
    public List<SaleTransaction> retrieveAllSaleTransactions() {
        Query query = em.createQuery("SELECT st FROM SaleTransaction st");

        return query.getResultList();
    }

    
    
    

    @Override
    public SaleTransaction retrieveSaleTransactionBySaleTransactionId(Long saleTransactionId) throws SaleTransactionNotFoundException {
        SaleTransaction saleTransaction = em.find(SaleTransaction.class, saleTransactionId);

        if (saleTransaction != null) {
            saleTransaction.getSaleTransactionLineItems().size();

            return saleTransaction;
        } else {
            throw new SaleTransactionNotFoundException("Sale Transaction ID " + saleTransactionId + " does not exist!");
        }
    }

    @Override
    public List<SaleTransaction> retrieveAllSaleTransactionsByCustomerId(Long customerId) {
        Query query = em.createQuery("SELECT st FROM SaleTransaction st where st.customer.userId = :inCustomerId");
        query.setParameter("inCustomerId", customerId);
        return query.getResultList();
    }

    @Override
    public List<SaleTransaction> retrieveAllSaleTransactionByUsernameAndPassword(String username, String password) throws InvalidLoginCredentialException {

        Customer customer = customerSessionBeanLocal.customerLogin(username, password);

        if (customer != null) {
            Query query = em.createQuery("SELECT st FROM SaleTransaction st where st.customer.username = :inCustomerUsername");

            query.setParameter("inCustomerUsername", username);
            
            List<SaleTransaction> saleTransactions = query.getResultList();
            
            for (SaleTransaction saleTransaction : saleTransactions) {
                saleTransaction.getSaleTransactionLineItems().size();
            }
            return saleTransactions;
        } else {
            throw new InvalidLoginCredentialException("Username or Password is wrong!");
        }

    }
    
    @Override
    public HashMap<YearMonth,BigDecimal> calculateRevenueByMonth(){
        HashMap<YearMonth,BigDecimal> hm = new HashMap<>();
        List<SaleTransaction> saleTransactions = retrieveAllSaleTransactions();
        for(SaleTransaction s : saleTransactions){
            int year = s.getTransactionDateTime().getYear();
            int month = s.getTransactionDateTime().getMonthValue();
            YearMonth yearMonth = YearMonth.of(year,month);
            if(hm.containsKey(yearMonth)){
                BigDecimal oldRevenue = hm.get(yearMonth);
                BigDecimal newRevenue = oldRevenue.add(s.getTotalAmount());
                hm.replace(yearMonth, oldRevenue, newRevenue);
            } else {
                hm.put(yearMonth, s.getTotalAmount());
            }
        }
        return hm;
    }
}
