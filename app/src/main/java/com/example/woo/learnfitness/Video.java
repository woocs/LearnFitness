package com.example.woo.learnfitness;

/**
 * Created by woo on 12/22/2017.
 */

public class Video{
    private String id;
    private String video;
    private String title;

    public Video(String id, String video, String title) {
        this.id = id;
        this.video=video;
        this.title=title;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getVideo() {
        return video;
    }

    public void setVideo(String video) {
        this.video = video;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return "Video{" +
                "id='" + id + '\'' +
                ", video='" + video + '\'' +
                ", title='" + title + '\'' +
                '}';
    }
}
