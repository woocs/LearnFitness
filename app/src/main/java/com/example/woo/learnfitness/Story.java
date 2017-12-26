package com.example.woo.learnfitness;

/**
 * Created by woo on 12/26/2017.
 */

public class Story {
    private String id;
    private String imagebefore;
    private String imageafter;
    private String story;

    public Story(String id, String imagebefore, String imageafter, String story){
        this.id = id;
        this.imagebefore = imagebefore;
        this.imageafter = imageafter;
        this.story = story;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImagebefore() {
        return imagebefore;
    }

    public void setImagebefore(String imagebefore) {
        this.imagebefore = imagebefore;
    }

    public String getImageafter() {
        return imageafter;
    }

    public void setImageafter(String imageafter) {
        this.imageafter = imageafter;
    }

    public String getStory() {
        return story;
    }

    public void setStory(String story) {
        this.story = story;
    }

    @Override
    public String toString() {
        return "Story{" +
                "id='" + id + '\'' +
                ", imagebefore='" + imagebefore + '\'' +
                ", imageafter='" + imageafter + '\'' +
                ", story='" + story + '\'' +
                '}';
    }
}
