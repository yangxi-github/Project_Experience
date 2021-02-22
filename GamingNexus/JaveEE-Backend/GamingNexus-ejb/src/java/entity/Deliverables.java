/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.sql.Date;
import java.time.LocalDateTime;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author root
 */
@Entity
public class Deliverables implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long deliverablesId;
    @Future
    @NotNull
    private LocalDateTime expectedDateOfArrival;
    @Size(min = 0, max = 100)
    @NotNull
    private String deliveryFirm;
    @NotNull
   // @Digits(integer = 15, fraction = 0)
    private String DeliveryManPhoneNumber;
    @NotNull
    private boolean hasArrived = false;
    @ManyToOne
    private Hardware hardware;
    @ManyToOne
    private Customer customer;

    public Deliverables() {
    }

    public Deliverables(LocalDateTime expectedDateOfArrival, String DeliveryManPhoneNumber, Hardware hardware, Customer customer) {
        this.expectedDateOfArrival = expectedDateOfArrival;
        this.DeliveryManPhoneNumber = DeliveryManPhoneNumber;
        this.hardware = hardware;
        this.customer = customer;
    }

    

    public Long getDeliverablesId() {
        return deliverablesId;
    }

    public void setDeliverablesId(Long deliverablesId) {
        this.deliverablesId = deliverablesId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (deliverablesId != null ? deliverablesId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the deliverablesId fields are not set
        if (!(object instanceof Deliverables)) {
            return false;
        }
        Deliverables other = (Deliverables) object;
        if ((this.deliverablesId == null && other.deliverablesId != null) || (this.deliverablesId != null && !this.deliverablesId.equals(other.deliverablesId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ejb.entity.Deliverables[ id=" + deliverablesId + " ]";
    }

    /**
     * @return the expectedDateOfArrival
     */
    public LocalDateTime getExpectedDateOfArrival() {
        return expectedDateOfArrival;
    }

    /**
     * @param expectedDateOfArrival the expectedDateOfArrival to set
     */
    public void setExpectedDateOfArrival(LocalDateTime expectedDateOfArrival) {
        this.expectedDateOfArrival = expectedDateOfArrival;
    }

    /**
     * @return the DeliveryManPhoneNumber
     */
    public String getDeliveryManPhoneNumber() {
        return DeliveryManPhoneNumber;
    }

    /**
     * @param DeliveryManPhoneNumber the DeliveryManPhoneNumber to set
     */
    public void setDeliveryManPhoneNumber(String DeliveryManPhoneNumber) {
        this.DeliveryManPhoneNumber = DeliveryManPhoneNumber;
    }

    /**
     * @return the hasArrived
     */
    public boolean isHasArrived() {
        return hasArrived;
    }

    /**
     * @param hasArrived the hasArrived to set
     */
    public void setHasArrived(boolean hasArrived) {
        this.hasArrived = hasArrived;
    }

    /**
     * @return the hardwares
     */
    public Hardware getHardware() {
        return hardware;
    }

    /**
     * @param hardware
     */
    public void setHardware(Hardware hardware) {
        this.hardware = hardware;
    }

    /**
     * @return the customer
     */
    public Customer getCustomer() {
        return customer;
    }

    /**
     * @param customer
     */
    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public String getDeliveryFirm() {
        return deliveryFirm;
    }

    public void setDeliveryFirm(String deliveryFirm) {
        this.deliveryFirm = deliveryFirm;
    }

}
