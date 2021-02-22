/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.restful.model;

import entity.Tag;
import java.util.List;

/**
 *
 * @author 63216
 */
public class RetrieveAllGameTagsRsp {
    private List<Tag> gameTags;

    public RetrieveAllGameTagsRsp() {
    }

    public RetrieveAllGameTagsRsp(List<Tag> gameTags) {
        this.gameTags = gameTags;
    }

    /**
     * @return the gameTags
     */
    public List<Tag> getGameTags() {
        return gameTags;
    }

    /**
     * @param gameTags the gameTags to set
     */
    public void setGameTags(List<Tag> gameTags) {
        this.gameTags = gameTags;
    }
    
    
}
