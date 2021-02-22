/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.restful.model;

import entity.Hardware;
import java.util.List;

/**
 *
 * @author chenli
 */
public class RetrieveAllHardwareRsp {
     private List<Hardware> hardware;

    public RetrieveAllHardwareRsp() {
    }

    public RetrieveAllHardwareRsp(List<Hardware> hardware) {
        this.hardware = hardware;
    }

    public List<Hardware> getHardware() {
        return hardware;
    }

    public void setHardware(List<Hardware> hardware) {
        this.hardware = hardware;
    }
     
     
}
