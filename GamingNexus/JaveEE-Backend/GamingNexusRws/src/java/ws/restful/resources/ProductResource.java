/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.restful.resources;

import ejb.session.stateless.ProductSessionBeanLocal;
import entity.Deliverables;
import entity.Forum;
import entity.Game;
import entity.GameAccount;
import entity.Hardware;
import entity.Product;
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
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import util.exception.ProductNotFoundException;
import ws.restful.model.ErrorRsp;
import ws.restful.model.RetrieveAllProductsRsp;
import ws.restful.model.RetrieveOneProductRsp;

/**
 * REST Web Service
 *
 * @author jin yichen
 */
@Path("Product")
public class ProductResource {

    ProductSessionBeanLocal productSessionBean = lookupProductSessionBeanLocal();

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of ProductResource
     */
    public ProductResource() {
    }

    /**
     * Retrieves representation of an instance of
     * ws.restful.resources.ProductResource
     *
     * @return an instance of java.lang.String
     */
    @Path("retrieveProductById")
    @GET
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.APPLICATION_JSON)
    public Response retrieveProductById(@QueryParam("productId") Long productId) {
        try {
            Product product = productSessionBean.retrieveProductById(productId);

            if (product.getCategory().getParentCategory() != null) {
                product.getCategory().getParentCategory().getSubCategories().clear();
            }

            product.getCategory().getProducts().clear();
            for (Tag tagEntity : product.getTags()) {
                tagEntity.getProducts().clear();
            }
            for (Promotion promotion : product.getPromotions()) {
                promotion.getProducts().clear();
            }
            product.getCompany().getProducts().clear();
            for (Rating rating : product.getRatings()) {
                rating.setProduct(null);
            }
            for (Forum forum : product.getForums()) {
                forum.setProduct(null);
            }

            return Response.status(Response.Status.OK).entity(new RetrieveOneProductRsp(product)).build();
        } catch (ProductNotFoundException ex) {
            ErrorRsp errorRsp = new ErrorRsp(ex.getMessage());

            return Response.status(Response.Status.BAD_REQUEST).entity(errorRsp).build();
        } catch (Exception ex) {
            ErrorRsp errorRsp = new ErrorRsp(ex.getMessage());

            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(errorRsp).build();
        }
    }

    @Path("searchProductsByName")
    @GET
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.APPLICATION_JSON)
    public Response searchProductsByName(@QueryParam("name") String name) {
        try {
            List<Product> products = productSessionBean.searchProductsByName(name);

            for (Product product : products) {

                if (product.getCategory().getParentCategory() != null) {
                    product.getCategory().getParentCategory().getSubCategories().clear();
                }
                product.getCategory().getProducts().clear();
                for (Tag tagEntity : product.getTags()) {
                    tagEntity.getProducts().clear();
                }
                for (Promotion promotion : product.getPromotions()) {
                    promotion.getProducts().clear();
                }
                product.getCompany().getProducts().clear();
                for (Rating rating : product.getRatings()) {
                    rating.setProduct(null);
                }
                for (Forum forum : product.getForums()) {
                    forum.setProduct(null);
                }
                if (product instanceof Game) {
                    Game game = (Game) product;

                    for (GameAccount gameAccount : game.getGameAccounts()) {
                        gameAccount.setGame(null);
                    }
                } else if (product instanceof Hardware) {
                    Hardware hardware = (Hardware) product;
                    for (Deliverables deliverables : hardware.getDeliverables()) {
                        deliverables.setHardware(null);
                    }
                }
            }
            return Response.status(Response.Status.OK).entity(new RetrieveAllProductsRsp(products)).build();
        } catch (Exception ex) {
            ErrorRsp errorRsp = new ErrorRsp(ex.getMessage());

            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(errorRsp).build();
        }
    }
    
    @Path("retrieveProductsByCategoryId")
    @GET
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.APPLICATION_JSON)
    public Response retrieveProductsByCategoryId(@QueryParam("categoryId") Long categoryId) {
        try {
            List<Product> products = productSessionBean.retrieveProductByCategoryId(categoryId);

            for (Product product : products) {

                if (product.getCategory().getParentCategory() != null) {
                    product.getCategory().getParentCategory().getSubCategories().clear();
                }
                product.getCategory().getProducts().clear();
                for (Tag tagEntity : product.getTags()) {
                    tagEntity.getProducts().clear();
                }
                for (Promotion promotion : product.getPromotions()) {
                    promotion.getProducts().clear();
                }
                product.getCompany().getProducts().clear();
                for (Rating rating : product.getRatings()) {
                    rating.setProduct(null);
                }
                for (Forum forum : product.getForums()) {
                    forum.setProduct(null);
                }
                if (product instanceof Game) {
                    Game game = (Game) product;

                    for (GameAccount gameAccount : game.getGameAccounts()) {
                        gameAccount.setGame(null);
                    }
                } else if (product instanceof Hardware) {
                    Hardware hardware = (Hardware) product;
                    for (Deliverables deliverables : hardware.getDeliverables()) {
                        deliverables.setHardware(null);
                    }
                }
            }
            return Response.status(Response.Status.OK).entity(new RetrieveAllProductsRsp(products)).build();
        } catch (Exception ex) {
            ErrorRsp errorRsp = new ErrorRsp(ex.getMessage());

            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(errorRsp).build();
        }
    }
    

    /**
     * PUT method for updating or creating an instance of ProductResource
     *
     * @param content representation for the resource
     */
    @PUT
    @Consumes(MediaType.APPLICATION_XML)
    public void putXml(String content) {
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
