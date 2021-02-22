/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.restful.model;

import entity.Game;

/**
 *
 * @author Yang Xi
 */
public class RetrieveOneGameRsp {
    
    private Game game;

    public RetrieveOneGameRsp() {
    }

    public RetrieveOneGameRsp(Game game) {
        this.game = game;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }
    
}
