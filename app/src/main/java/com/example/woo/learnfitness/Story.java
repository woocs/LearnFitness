package com.example.woo.learnfitness;

/**
 * Created by woo on 12/26/2017.
 */

public class Story {
    private String id;
    private String story;

    public Story(String id, String story){
        this.id = id;
        this.story = story;
    }

    public Story(){

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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
                ", story='" + story + '\'' +
                '}';
    }
}
