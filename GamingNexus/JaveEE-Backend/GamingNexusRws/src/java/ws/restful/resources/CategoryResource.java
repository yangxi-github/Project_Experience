/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.restful.resources;

import ejb.session.stateless.CategorySessionBeanLocal;
import ejb.session.stateless.ProductSessionBeanLocal;
import entity.Category;
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
import ws.restful.model.RetrieveAllCategoriesRsp;

/**
 * REST Web Service
 *
 * @author chenli
 */
@Path("Category")
public class CategoryResource {

    @Context
    private UriInfo context;

    private CategorySessionBeanLocal categorySessionBean = lookupCategorySessionBeanLocal();

    /**
     * Creates a new instance of CategoryResource
     */
    public CategoryResource() {
        
    }

    /**
     * Retrieves representation of an instance of
     * ws.restful.resources.CategoryResource
     *
     * @return an instance of java.lang.String
     */
    @Path("retrieveAllCategories")
    @GET
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.APPLICATION_JSON)
    public Response retrieveAllCategories()
    {
        try
        {
           
            List<Category> categoryEntities = categorySessionBean.retrieveAllCategories();
            
            for(Category categoryEntity:categoryEntities)
            {
                if(categoryEntity.getParentCategory() != null)
                {
                    categoryEntity.getParentCategory().getSubCategories().clear();
                }
                
                categoryEntity.getSubCategories().clear();
                categoryEntity.getProducts().clear();
            }
            
            return Response.status(Status.OK).entity(new RetrieveAllCategoriesRsp(categoryEntities)).build();
        }

        catch(Exception ex)
        {
            ErrorRsp errorRsp = new ErrorRsp(ex.getMessage());
            
            return Response.status(Status.INTERNAL_SERVER_ERROR).entity(errorRsp).build();
        }
    }
    
    @Path("retrieveAllSoftwareToolCategories")
    @GET
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.APPLICATION_JSON)
    public Response retrieveAllSoftwareToolCategories()
    {
        try
        {
           
            List<Category> categoryEntities = categorySessionBean.retrieveAllSoftwareToolCategories();
            
            for(Category categoryEntity:categoryEntities)
            {
                if(categoryEntity.getParentCategory() != null)
                {
                    categoryEntity.getParentCategory().getSubCategories().clear();
                }
                
                categoryEntity.getSubCategories().clear();
                categoryEntity.getProducts().clear();
            }
            
            return Response.status(Status.OK).entity(new RetrieveAllCategoriesRsp(categoryEntities)).build();
        }

        catch(Exception ex)
        {
            ErrorRsp errorRsp = new ErrorRsp(ex.getMessage());
            
            return Response.status(Status.INTERNAL_SERVER_ERROR).entity(errorRsp).build();
        }
    }
    
    @Path("retrieveAllHardwareCategories")
    @GET
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.APPLICATION_JSON)
    public Response retrieveAllHardwareCategories()
    {
        try
        {
           
            List<Category> categoryEntities = categorySessionBean.retrieveAllHardwareCategories();
            
            for(Category categoryEntity:categoryEntities)
            {
                if(categoryEntity.getParentCategory() != null)
                {
                    categoryEntity.getParentCategory().getSubCategories().clear();
                }
                
                categoryEntity.getSubCategories().clear();
                categoryEntity.getProducts().clear();
            }
            
            return Response.status(Status.OK).entity(new RetrieveAllCategoriesRsp(categoryEntities)).build();
        }

        catch(Exception ex)
        {
            ErrorRsp errorRsp = new ErrorRsp(ex.getMessage());
            
            return Response.status(Status.INTERNAL_SERVER_ERROR).entity(errorRsp).build();
        }
    }
    

    /**
     * PUT method for updating or creating an instance of CategoryResource
     *
     * @param content representation for the resource
     */
    @PUT
    @Consumes(MediaType.APPLICATION_XML)
    public void putXml(String content) {
    }

    private CategorySessionBeanLocal lookupCategorySessionBeanLocal() {
        try {
            javax.naming.Context c = new InitialContext();
            return (CategorySessionBeanLocal) c.lookup("java:global/GamingNexus/GamingNexus-ejb/CategorySessionBean!ejb.session.stateless.CategorySessionBeanLocal");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }

    private ProductSessionBeanLocal lookupProductSessionBeanLocal() {
        try {
            javax.naming.Context c = new InitialContext();
            return (ProductSessionBeanLocal) c.lookup("java:global/GamingNexus/GamingNexus-ejb/ProductSessionBean!ejb.session.stateless.ProductSessionBeanLocal");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }
}
