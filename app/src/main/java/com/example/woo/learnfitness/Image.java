package com.example.woo.learnfitness;

/**
 * Created by woo on 12/31/2017.
 */

public class Image {
    private String id;
    private String imgbefore, imgafter;

    public Image(String id, String imgbefore, String imgafter) {
        this.id = id;
        this.imgbefore = imgbefore;
        this.imgafter = imgafter;
    }

    public Image(){
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImgbefore() {
        return imgbefore;
    }

    public void setImgbefore(String imgbefore) {
        this.imgbefore = imgbefore;
    }

    public String getImgafter() {
        return imgafter;
    }

    public void setImgafter(String imgafter) {
        this.imgafter = imgafter;
    }

    @Override
    public String toString() {
        return "Image{" +
                "id='" + id + '\'' +
                ", imgbefore='" + imgbefore + '\'' +
                ", imgafter='" + imgafter + '\'' +
                '}';
    }
}
