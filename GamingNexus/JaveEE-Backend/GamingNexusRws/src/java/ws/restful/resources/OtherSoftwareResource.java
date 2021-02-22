/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.restful.resources;

import ejb.session.stateless.OtherSoftwareSessionBeanLocal;
import entity.Forum;
import entity.OtherSoftware;
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
import ws.restful.model.RetrieveAllSoftwareRsp;

/**
 * REST Web Service
 *
 * @author chenli
 */
@Path("OtherSoftware")
public class OtherSoftwareResource {

    

    @Context
    private UriInfo context;

    private OtherSoftwareSessionBeanLocal otherSoftwareSessionBean = lookupOtherSoftwareSessionBeanLocal();
    
    /**
     * Creates a new instance of OtherSoftwareResource
     */
    public OtherSoftwareResource() {
    }

    /**
     * Retrieves representation of an instance of ws.restful.resources.OtherSoftwareResource
     * @return an instance of java.lang.String
     */
    @Path("retrieveAllOtherSoftware")
    @GET
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.APPLICATION_JSON)
     public Response RetrieveAllOtherSoftware() {
        try {
        List<OtherSoftware> allOtherSoftware = otherSoftwareSessionBean.retrieveAllOtherSoftwares();
        for (OtherSoftware otherSoftware : allOtherSoftware) {
                if (otherSoftware.getCategory().getParentCategory() != null) {
                    otherSoftware.getCategory().getParentCategory().getSubCategories().clear();
                }
                otherSoftware.getCategory().getProducts().clear();
                for (Tag tagEntity : otherSoftware.getTags()) {
                    tagEntity.getProducts().clear();
                }
                for (Promotion promotion : otherSoftware.getPromotions()) {
                    promotion.getProducts().clear();
                }
                otherSoftware.getCompany().getProducts().clear();
                for (Rating rating : otherSoftware.getRatings()) {
                    rating.setProduct(null);
                }
                for (Forum forum : otherSoftware.getForums()) {
                    forum.setProduct(null);
                }
            }
        RetrieveAllSoftwareRsp retrieveAllotherSoftwareRsp = new RetrieveAllSoftwareRsp(allOtherSoftware);
        
        return Response.status(Status.OK).entity(retrieveAllotherSoftwareRsp).build();
        } catch (Exception ex) {
            ErrorRsp errorRsp = new ErrorRsp(ex.getMessage());
            return Response.status(Status.INTERNAL_SERVER_ERROR).entity(errorRsp).build();
        }
    }

    /**
     * PUT method for updating or creating an instance of OtherSoftwareResource
     * @param content representation for the resource
     */
    @PUT
    @Consumes(MediaType.APPLICATION_XML)
    public void putXml(String content) {
    }

    private OtherSoftwareSessionBeanLocal lookupOtherSoftwareSessionBeanLocal() {
        try {
            javax.naming.Context c = new InitialContext();
            return (OtherSoftwareSessionBeanLocal) c.lookup("java:global/GamingNexus/GamingNexus-ejb/OtherSoftwareSessionBean!ejb.session.stateless.OtherSoftwareSessionBeanLocal");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }
}
