/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.restful.model;

import entity.OtherSoftware;
import java.util.List;

/**
 *
 * @author chenli
 */
public class RetrieveAllSoftwareRsp {
     private List<OtherSoftware> otherSoftware;

    public RetrieveAllSoftwareRsp() {
    }

    public RetrieveAllSoftwareRsp(List<OtherSoftware> otherSoftware) {
        this.otherSoftware = otherSoftware;
    }
 
    public List<OtherSoftware> getOtherSoftware() {
        return otherSoftware;
    }

    public void setOtherSoftware(List<OtherSoftware> otherSoftware) {
        this.otherSoftware = otherSoftware;
    }
     
}
