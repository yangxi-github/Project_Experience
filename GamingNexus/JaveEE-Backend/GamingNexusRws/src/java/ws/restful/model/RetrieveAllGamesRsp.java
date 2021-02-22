/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.restful.model;

import entity.Game;
import java.util.List;

/**
 *
 * @author chenli
 */
public class RetrieveAllGamesRsp {

    private List<Game> games;

    public RetrieveAllGamesRsp() {
    }

    public RetrieveAllGamesRsp(List<Game> games) {
        this.games = games;
    }

    public List<Game> getGames() {
        return games;
    }

    public void setGames(List<Game> games) {
        this.games = games;
    }


}
