package com.example.woo.learnfitness;

/**
 * Created by woo on 12/22/2017.
 */

public class Video{

    private String video;
    private String title;
    private String categories;
    private String favorite;
    private String likes;
    private String id;

    public Video(String video, String title, String categories, String favorite, String likes, String id) {
        this.video=video;
        this.title=title;
        this.categories=categories;
        this.favorite=favorite;
        this.likes=likes;
        this.id = id;

    }
    public Video(){

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

    public String getCategories() {
        return categories;
    }

    public void setCategories(String categories) {
        this.categories = categories;
    }

    public String getFavorite() {
        return favorite;
    }

    public void setFavorite(String favorite) {
        this.favorite = favorite;
    }

    public String getLikes() {
        return likes;
    }

    public void setLikes(String likes) {
        this.likes = likes;
    }

    @Override
    public String toString() {
        return "Video{" +
                "id='" + id + '\'' +
                ", video='" + video + '\'' +
                ", title='" + title + '\'' +
                ", categories='" + categories + '\'' +
                ", favorite='" + favorite + '\'' +
                ", likes='" + likes + '\'' +
                '}';
    }
}
