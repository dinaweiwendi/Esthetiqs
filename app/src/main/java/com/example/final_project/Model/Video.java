package com.example.final_project.Model;

import android.os.Parcel;
import android.os.Parcelable;

public class Video implements Parcelable {
    private String videoUrl;
    private String title;
    public Video(String videourl, String ttl) {
        this.videoUrl = videourl;
        this.title = ttl;
    }

    protected Video(Parcel in) {
        videoUrl = in.readString();
        title = in.readString();
    }

    public static final Creator<Video> CREATOR = new Creator<Video>() {
        @Override
        public Video createFromParcel(Parcel in) {
            return new Video(in);
        }

        @Override
        public Video[] newArray(int size) {
            return new Video[size];
        }
    };

    public String getVideoUrl(){
        return videoUrl;
    }
    public String getTitle(){
        return title;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(videoUrl);
        dest.writeString(title);
    }
}