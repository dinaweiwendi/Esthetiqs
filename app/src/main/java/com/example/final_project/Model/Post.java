package com.example.final_project.Model;

import java.io.Serializable;

public class Post implements Serializable {

    private String postContent;
    private int image;
    private String userName;
    private String videoUrl;

    public Post(String postContent, int image, String userName, String videoUrl) {

        this.postContent = postContent;
        this.image = image;
        this.userName = userName;
        this.videoUrl = videoUrl;
    }

    public Post() {}



    public String getPostContent() {
        return postContent;
    }


    public int getImage() {
        return image;
    }

    public String getUserName() {return userName; }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }
}
