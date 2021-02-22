/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.restful.model;

import java.util.List;

/**
 *
 * @author Yang Xi
 */
public class TagIdListReq {
    
    private int tagId;
    private int [] tagsIdArray;
    private List<Long> tagIds;

    public TagIdListReq() {
    }

    public TagIdListReq(List<Long> tagIds) {
        this.tagIds = tagIds;
    }

    public List<Long> getTagIds() {
        return tagIds;
    }

    public void setTagIds(List<Long> tagIds) {
        this.tagIds = tagIds;
    }
    
    public void changeIntegerToLong() {
        for(int i = 0; i < getTagsIdArray().length; i++) {
            tagIds.add((long)getTagsIdArray()[i]);
        }
        tagIds.add((long)tagId);
        
    }

    public int[] getTagsIdArray() {
        return tagsIdArray;
    }

    public void setTagsIdArray(int[] tagsIdArray) {
        this.tagsIdArray = tagsIdArray;
    }
    
    
    
}
