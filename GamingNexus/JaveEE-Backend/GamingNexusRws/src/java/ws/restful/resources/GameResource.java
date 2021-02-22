/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.restful.resources;

import ejb.session.stateless.GameSessionBeanLocal;
import entity.Forum;
import entity.Game;
import entity.GameAccount;
import entity.Promotion;
import entity.Rating;
import entity.Tag;
import java.util.ArrayList;
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
import javax.ws.rs.core.Response.Status;
import ws.restful.model.ErrorRsp;
import ws.restful.model.RetrieveAllGamesRsp;
import ws.restful.model.RetrieveOneGameRsp;
import ws.restful.model.TagIdListReq;

/**
 * REST Web Service
 *
 * @author chenli
 */
@Path("Game")
public class GameResource {

    @Context
    private UriInfo context;

    private GameSessionBeanLocal gameSessionBean = lookupGameSessionBeanLocal();

    /**
     * Creates a new instance of GameResource
     */
    public GameResource() {
    }

    /**
     * Retrieves representation of an instance of
     * ws.restful.resources.GameResource
     *
     * @return an instance of java.lang.String
     */
    @Path("retrieveAllGames")
    @GET
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.APPLICATION_JSON)
    public Response retrieveAllGames() {
        try {
            
            System.out.println("********** retrieveAllGames() is successfully invoked");

            List<Game> games = gameSessionBean.retrieveAllGames();

            for (Game game : games) {
                if (game.getCategory().getParentCategory() != null) {
                    game.getCategory().getParentCategory().getSubCategories().clear();
                }
                game.getCategory().getProducts().clear();
                for (Tag tagEntity : game.getTags()) {
                    tagEntity.getProducts().clear();
                }
                for (Promotion promotion : game.getPromotions()) {
                    promotion.getProducts().clear();
                }
                game.getCompany().getProducts().clear();
                for (Rating rating : game.getRatings()) {
                    rating.setProduct(null);
                }
                for (Forum forum : game.getForums()) {
                    forum.setProduct(null);
                }
                for (GameAccount gameAccount : game.getGameAccounts()) {
                    gameAccount.setGame(null);
                }
            }

            return Response.status(Status.OK).entity(new RetrieveAllGamesRsp(games)).build();
        } catch (Exception ex) {
            ErrorRsp errorRsp = new ErrorRsp(ex.getMessage());

            return Response.status(Status.INTERNAL_SERVER_ERROR).entity(errorRsp).build();
        }
    }
    
    
    @Path("retrieveGameById")
    @GET
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.APPLICATION_JSON)
    public Response retrieveGameById(@QueryParam("gameId") Integer gameId) {
        try {

            Game game = gameSessionBean.retrieveGamebyId(Long.valueOf(gameId.longValue()));

                if (game.getCategory().getParentCategory() != null) {
                    game.getCategory().getParentCategory().getSubCategories().clear();
                }
                game.getCategory().getProducts().clear();
                for (Tag tagEntity : game.getTags()) {
                    tagEntity.getProducts().clear();
                }
                for (Promotion promotion : game.getPromotions()) {
                    promotion.getProducts().clear();
                }
                game.getCompany().getProducts().clear();
                for (Rating rating : game.getRatings()) {
                    rating.setProduct(null);
                }
                for (Forum forum : game.getForums()) {
                    forum.setProduct(null);
                }
                for (GameAccount gameAccount : game.getGameAccounts()) {
                    gameAccount.setGame(null);
                }
 
            return Response.status(Status.OK).entity(new RetrieveOneGameRsp(game)).build();
        } catch (Exception ex) {
            ErrorRsp errorRsp = new ErrorRsp(ex.getMessage());

            return Response.status(Status.INTERNAL_SERVER_ERROR).entity(errorRsp).build();
        }
    }

    /**
     * PUT method for updating or creating an instance of GameResource
     *
     * @param content representation for the resource
     */
    //    
    @Path("filterGamesByTags")
    @GET
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.APPLICATION_JSON)
    public Response filterGamesByTags(@QueryParam("tagId") Integer tagId) {
        try {
            
            List<Long> tagIds = new ArrayList<>();
            tagIds.add(tagId.longValue());
            
            List<Game> games = gameSessionBean.filterGamesByTags(tagIds, "AND");

            for (Game game : games) {
                if (game.getCategory().getParentCategory() != null) {
                    game.getCategory().getParentCategory().getSubCategories().clear();
                }
                game.getCategory().getProducts().clear();
                for (Tag tagEntity : game.getTags()) {
                    tagEntity.getProducts().clear();
                }
                for (Promotion promotion : game.getPromotions()) {
                    promotion.getProducts().clear();
                }
                game.getCompany().getProducts().clear();
                for (Rating rating : game.getRatings()) {
                    rating.setProduct(null);
                }
                for (Forum forum : game.getForums()) {
                    forum.setProduct(null);
                }
                for (GameAccount gameAccount : game.getGameAccounts()) {
                    gameAccount.setGame(null);
                }
            }

            return Response.status(Status.OK).entity(new RetrieveAllGamesRsp(games)).build();
        } catch (Exception ex) {
            ErrorRsp errorRsp = new ErrorRsp(ex.getMessage());

            return Response.status(Status.INTERNAL_SERVER_ERROR).entity(errorRsp).build();
        }
    }
    
    
    @PUT
    @Consumes(MediaType.APPLICATION_XML)
    public void putXml(String content
    ) {
    }

    private GameSessionBeanLocal lookupGameSessionBeanLocal() {
        try {
            javax.naming.Context c = new InitialContext();
            return (GameSessionBeanLocal) c.lookup("java:global/GamingNexus/GamingNexus-ejb/GameSessionBean!ejb.session.stateless.GameSessionBeanLocal");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }
}
