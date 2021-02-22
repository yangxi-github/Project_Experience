/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.managedbean;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import org.primefaces.event.ScheduleEntryMoveEvent;
import org.primefaces.event.ScheduleEntryResizeEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.DefaultScheduleEvent;
import org.primefaces.model.DefaultScheduleModel;
import org.primefaces.model.ScheduleEvent;
import org.primefaces.model.ScheduleModel;

/**
 *
 * @author Yang Xi
 */
@Named(value = "scheduleManagedBean")
@ViewScoped
public class ScheduleManagedBean implements Serializable{
    private ScheduleModel scheduleModel;
    private ScheduleEvent scheduleEvent;

    /**
     * Creates a new instance of ScheduleManagedBean
     */
    public ScheduleManagedBean() {
        scheduleModel = new DefaultScheduleModel();
        scheduleEvent = new DefaultScheduleEvent();
    }
    
    @PostConstruct
    public void postConstruct()
    {
        scheduleModel.addEvent(new DefaultScheduleEvent("Company Meeting", previousDay8Pm(), previousDay11Pm()));
        scheduleModel.addEvent(new DefaultScheduleEvent("System Maintainance", today1Pm(), today6Pm()));
        scheduleModel.addEvent(new DefaultScheduleEvent("Meeting with clients", nextDay9Am(), nextDay11Am()));
        scheduleModel.addEvent(new DefaultScheduleEvent("Party", theDayAfter3Pm(), fourDaysLater3pm()));
    }
    
    
    
    public ScheduleModel getScheduleModel() {
        return scheduleModel;
    }

    public void setScheduleModel(ScheduleModel scheduleModel) {
        this.scheduleModel = scheduleModel;
    }
    
    public ScheduleEvent getScheduleEvent() {
        return scheduleEvent;
    }

    public void setScheduleEvent(ScheduleEvent scheduleEvent) {
        this.scheduleEvent = scheduleEvent;
    }
    
    
    
    public void addEvent(ActionEvent actionEvent) 
    {
        if(scheduleEvent.getId() == null)
            scheduleModel.addEvent(scheduleEvent);
        else
            scheduleModel.updateEvent(scheduleEvent);
         
        scheduleEvent = new DefaultScheduleEvent();
    }
    
    
    
    public void onEventSelect(SelectEvent selectEvent) 
    {
        scheduleEvent = (ScheduleEvent) selectEvent.getObject();
    }
    
    
    
    public void onDateSelect(SelectEvent selectEvent) 
    {
        scheduleEvent = new DefaultScheduleEvent("", (Date) selectEvent.getObject(), (Date) selectEvent.getObject());
    }
    
    
    
    public void onEventMove(ScheduleEntryMoveEvent scheduleEvent) 
    {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Event moved", "Day delta:" + scheduleEvent.getDayDelta() + ", Minute delta:" + scheduleEvent.getMinuteDelta());
         
        addMessage(message);
    }
    
    
    
    public void onEventResize(ScheduleEntryResizeEvent scheduleEvent) 
    {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Event resized", "Day delta:" + scheduleEvent.getDayDelta() + ", Minute delta:" + scheduleEvent.getMinuteDelta());
         
        addMessage(message);
    }
     
    
    
    private void addMessage(FacesMessage message) 
    {
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    
    
    private Calendar today() 
    {
        Calendar calendar = Calendar.getInstance();
        calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DATE), 0, 0, 0);
 
        return calendar;
    }
    
    private Date previousDay8Pm() 
    {
        Calendar t = (Calendar) today().clone();
        t.set(Calendar.AM_PM, Calendar.PM);
        t.set(Calendar.DATE, t.get(Calendar.DATE) - 1);
        t.set(Calendar.HOUR, 8);

        return t.getTime();
    }

    private Date previousDay11Pm() 
    {
        Calendar t = (Calendar) today().clone();
        t.set(Calendar.AM_PM, Calendar.PM);
        t.set(Calendar.DATE, t.get(Calendar.DATE) - 1);
        t.set(Calendar.HOUR, 11);

        return t.getTime();
    }

    private Date today1Pm() 
    {
        Calendar t = (Calendar) today().clone();
        t.set(Calendar.AM_PM, Calendar.PM);
        t.set(Calendar.HOUR, 1);

        return t.getTime();
    }

    private Date theDayAfter3Pm()
    {
        Calendar t = (Calendar) today().clone();
        t.set(Calendar.DATE, t.get(Calendar.DATE) + 2);
        t.set(Calendar.AM_PM, Calendar.PM);
        t.set(Calendar.HOUR, 3);

        return t.getTime();
    }

    private Date today6Pm() 
    {
        Calendar t = (Calendar) today().clone();
        t.set(Calendar.AM_PM, Calendar.PM);
        t.set(Calendar.HOUR, 6);

        return t.getTime();
    }

    private Date nextDay9Am()
    {
        Calendar t = (Calendar) today().clone();
        t.set(Calendar.AM_PM, Calendar.AM);
        t.set(Calendar.DATE, t.get(Calendar.DATE) + 1);
        t.set(Calendar.HOUR, 9);

        return t.getTime();
    }

    private Date nextDay11Am()
    {
        Calendar t = (Calendar) today().clone();
        t.set(Calendar.AM_PM, Calendar.AM);
        t.set(Calendar.DATE, t.get(Calendar.DATE) + 1);
        t.set(Calendar.HOUR, 11);

        return t.getTime();
    }

    private Date fourDaysLater3pm() 
    {
        Calendar t = (Calendar) today().clone();
        t.set(Calendar.AM_PM, Calendar.PM);
        t.set(Calendar.DATE, t.get(Calendar.DATE) + 4);
        t.set(Calendar.HOUR, 3);

        return t.getTime();
    }
    
}
