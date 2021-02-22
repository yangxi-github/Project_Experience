/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.restful.resources;

import ejb.session.stateless.TagSessionBeanLocal;
import entity.Tag;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import ws.restful.model.ErrorRsp;
import ws.restful.model.RetrieveAllTagsRsp;

/**
 * REST Web Service
 *
 * @author chenli
 */
@Path("Tag")
public class TagResource {

    @Context
    private UriInfo context;

    private TagSessionBeanLocal tagSessionBeanLocal = lookupTagSessionBeanLocal();

    /**
     * Creates a new instance of TagResource
     */
    public TagResource() {
    }

    /**
     * Retrieves representation of an instance of
     * ws.restful.resources.TagResource
     *
     * @return an instance of java.lang.String
     */
    @Path("retrieveAllTags")
    @GET
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.APPLICATION_JSON)
    public Response retrieveAllTags()
    {
        try
        {
            
            List<Tag> tagEntities = tagSessionBeanLocal.retrieveAllTags();
            
            for(Tag tagEntity:tagEntities)
            {                
                tagEntity.getProducts().clear();
            }
            
            return Response.status(Status.OK).entity(new RetrieveAllTagsRsp(tagEntities)).build();
        }

        catch(Exception ex)
        {
            ErrorRsp errorRsp = new ErrorRsp(ex.getMessage());
            
            return Response.status(Status.INTERNAL_SERVER_ERROR).entity(errorRsp).build();
        }
    }
    
    @Path("retrieveAllGameTags")
    @GET
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.APPLICATION_JSON)
    public Response retrieveAllGameTags()
    {
        try
        {
            
            List<Tag> tagEntities = tagSessionBeanLocal.retrieveAllGameTags();
            
            for(Tag tagEntity:tagEntities)
            {                
                tagEntity.getProducts().clear();
            }
            
            return Response.status(Status.OK).entity(new RetrieveAllTagsRsp(tagEntities)).build();
        }

        catch(Exception ex)
        {
            ErrorRsp errorRsp = new ErrorRsp(ex.getMessage());
            
            return Response.status(Status.INTERNAL_SERVER_ERROR).entity(errorRsp).build();
        }
    }

    /**
     * PUT method for updating or creating an instance of TagResource
     *
     * @param content representation for the resource
     */
    @PUT
    @Consumes(MediaType.APPLICATION_XML)
    public void putXml(String content) {
    }

    private TagSessionBeanLocal lookupTagSessionBeanLocal() {
        try {
            javax.naming.Context c = new InitialContext();
            return (TagSessionBeanLocal) c.lookup("java:global/GamingNexus/GamingNexus-ejb/TagSessionBean!ejb.session.stateless.TagSessionBeanLocal");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }
}
