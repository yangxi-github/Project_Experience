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
 * @author chenli
 */
public class RetrieveAllTagsRsp {
    private List<Tag> tags;

    public RetrieveAllTagsRsp() {
    }

    public RetrieveAllTagsRsp(List<Tag> tags) {
        this.tags = tags;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }
    
    
}
