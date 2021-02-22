/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.managedbean;

import ejb.session.stateless.SaleTransactionSessionBeanLocal;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import org.primefaces.event.ItemSelectEvent;
import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.CategoryAxis;
import org.primefaces.model.chart.ChartSeries;
import org.primefaces.model.chart.LineChartModel;

/**
 *
 * @author ufoya
 */
@Named(value = "revenueLineChart")
@ViewScoped
public class RevenueManagedBean implements Serializable {

    @EJB(name = "SaleTransactionSessionBeanLocal")
    private SaleTransactionSessionBeanLocal saleTransactionSessionBeanLocal;

    private HashMap<YearMonth, BigDecimal> revenueByMonth;
    private List<YearMonth> keyList;

    private LineChartModel lineModel;

    public RevenueManagedBean() {

    }

    @PostConstruct
    public void init() {
        setRevenueByMonth(getSaleTransactionSessionBeanLocal().calculateRevenueByMonth());
        setKeyList(new ArrayList<YearMonth>(getRevenueByMonth().keySet()));
        createLineModels();
    }

    public void itemSelect(ItemSelectEvent event) {
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Item selected",
                "Item Index: " + event.getItemIndex() + ", Series Index:" + event.getSeriesIndex());

        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public LineChartModel getLineModel() {
        return lineModel;
    }

    private LineChartModel initCategoryModel() {
        LineChartModel model = new LineChartModel();

        ChartSeries months = new ChartSeries();
        months.setLabel("Sales Revenue");
        
        for(int i = 1; i <= 12; i++) {
            BigDecimal revenue = revenueByMonth.get(YearMonth.of(2020, i));
            if(revenue != null) {
                switch(i) {
                    case 1:  months.set("Jan", revenue); break;
                    case 2:  months.set("Feb", revenue); break;
                    case 3:  months.set("Mar", revenue); break;
                    case 4:  months.set("April", revenue); break;
                    case 5:  months.set("May", revenue); break;
                    case 6:  months.set("June", revenue); break;
                    case 7:  months.set("July", revenue); break;
                    case 8:  months.set("Aug", revenue); break;
                    case 9:  months.set("Sep", revenue); break;
                    case 10:  months.set("Oct", revenue); break;
                    case 11:  months.set("Nov", revenue); break;
                    case 12:  months.set("Dec", revenue); break;                                          
                }
            }
        }
       

//        months.set("Jan", revenueByMonth.get(YearMonth.of(2020, 4)));
//        months.set("Feb", revenueByMonth.get(YearMonth.of(2020, 4)));
//        months.set("Mar",  revenueByMonth.get(YearMonth.of(2020, 4)));
//        months.set("April", revenueByMonth.get(YearMonth.of(2020, 4)));

        model.addSeries(months);

        return model;
    }

    private void createLineModels() {

        lineModel = initCategoryModel();
        lineModel.setTitle("Revenue Chart");
        lineModel.setLegendPosition("e");
        lineModel.setShowPointLabels(true);
        lineModel.getAxes().put(AxisType.X, new CategoryAxis("Months"));
        Axis yAxis = lineModel.getAxis(AxisType.Y);
        yAxis.setLabel("Revenue");
        yAxis.setMin(0);
        yAxis.setMax(1000);

    }

    public SaleTransactionSessionBeanLocal getSaleTransactionSessionBeanLocal() {
        return saleTransactionSessionBeanLocal;
    }

    public void setSaleTransactionSessionBeanLocal(SaleTransactionSessionBeanLocal saleTransactionSessionBeanLocal) {
        this.saleTransactionSessionBeanLocal = saleTransactionSessionBeanLocal;
    }

    public HashMap<YearMonth, BigDecimal> getRevenueByMonth() {
        return revenueByMonth;
    }

    public void setRevenueByMonth(HashMap<YearMonth, BigDecimal> revenueByMonth) {
        this.revenueByMonth = revenueByMonth;
    }

    public List<YearMonth> getKeyList() {
        return keyList;
    }

    public void setKeyList(List<YearMonth> keyList) {
        this.keyList = keyList;
    }

}
