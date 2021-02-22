/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.restful.resources;

import ejb.session.stateless.HardwareSessionBeanLocal;
import entity.Deliverables;
import entity.Forum;
import entity.Hardware;
import entity.Promotion;
import entity.Rating;
import entity.Tag;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import ws.restful.model.ErrorRsp;
import ws.restful.model.RetrieveAllHardwareRsp;

/**
 * REST Web Service
 *
 * @author chenli
 */
@Path("Hardware")
public class HardwareResource {

   
    @Context
    private UriInfo context;

    HardwareSessionBeanLocal hardwareSessionBean = lookupHardwareSessionBeanLocal();
    
    public HardwareResource() {
    }

    /**
     * Retrieves representation of an instance of ws.restful.resources.HardwareResource
     * @return an instance of java.lang.String
     */
    @Path("retrieveAllHardware")
    @GET
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.APPLICATION_JSON)
     public Response RetrieveAllHardtware() {
        try {
        List<Hardware> allHardware = hardwareSessionBean.retrieveAllHardwares();
        for (Hardware hardware : allHardware) {
                if (hardware.getCategory().getParentCategory() != null) {
                    hardware.getCategory().getParentCategory().getSubCategories().clear();
                }
                hardware.getCategory().getProducts().clear();
                for (Tag tagEntity : hardware.getTags()) {
                    tagEntity.getProducts().clear();
                }
                for (Promotion promotion : hardware.getPromotions()) {
                    promotion.getProducts().clear();
                }
                hardware.getCompany().getProducts().clear();
                for (Rating rating : hardware.getRatings()) {
                    rating.setProduct(null);
                }
                for (Forum forum : hardware.getForums()) {
                    forum.setProduct(null);
                }
                for(Deliverables deliverables : hardware.getDeliverables()) {
                    deliverables.setHardware(null);
                }
            }
        RetrieveAllHardwareRsp retrieveAllHardwareRsp = new RetrieveAllHardwareRsp(allHardware);
        
        return Response.status(Status.OK).entity(retrieveAllHardwareRsp).build();
        } catch (Exception ex) {
            ErrorRsp errorRsp = new ErrorRsp(ex.getMessage());
            return Response.status(Status.INTERNAL_SERVER_ERROR).entity(errorRsp).build();
        }
    }

    /**
     * PUT method for updating or creating an instance of HardwareResource
     * @param content representation for the resource
     */
    @PUT
    @Consumes(MediaType.APPLICATION_XML)
    public void putXml(String content) {
    }

    private HardwareSessionBeanLocal lookupHardwareSessionBeanLocal() {
        try {
            javax.naming.Context c = new InitialContext();
            return (HardwareSessionBeanLocal) c.lookup("java:global/GamingNexus/GamingNexus-ejb/HardwareSessionBean!ejb.session.stateless.HardwareSessionBeanLocal");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }
}
