package com.example.woo.learnfitness;

/**
 * Created by woo on 12/26/2017.
 */

public class User {
    private String id;
    private String name;
    private String age;
    private String gender;
    private String experience;
    private String imageprofile;

    public User(String id, String name, String age, String gender, String experience, String imageprofile) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.experience = experience;
        this.imageprofile = imageprofile;
    }

    public User() {

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getExperience() {
        return experience;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }

    public String getImageprofile() {
        return imageprofile;
    }

    public void setImageprofile(String imageprofile) {
        this.imageprofile = imageprofile;
    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", gender='" + gender + '\'' +
                ", experience='" + experience + '\'' +
                ", imageprofile='" + imageprofile + '\'' +
                '}';
    }
}
