package com.example.final_project.Model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class UserProfile implements Parcelable {
    public String name;
    public String description;
    public String avatar;
    public ArrayList<Skill> skillset;
    public ArrayList<Experience> expset;
    public ArrayList<Video> videoset;
    //private Skill skill;
    //private Experience experience;
    private static UserProfile mInstance = null;

    private UserProfile(){
        this.name = "";
        description = "Edit your description.";
        skillset = new ArrayList<>();
        expset = new ArrayList<>();
        videoset = new ArrayList<>();
    }

    protected UserProfile(Parcel in) {
        name = in.readString();
        description = in.readString();
        avatar = in.readString();
        skillset = new ArrayList<>();
        expset = new ArrayList<>();
        videoset = new ArrayList<>();

        in.readTypedList(skillset, Skill.CREATOR);
        in.readTypedList(expset, Experience.CREATOR);
        in.readTypedList(videoset, Video.CREATOR);

    }
    public static synchronized UserProfile getInstance() {
        if(null == mInstance){
            mInstance = new UserProfile();
        }
        return mInstance;
    }


    public static final Creator<UserProfile> CREATOR = new Creator<UserProfile>() {
        @Override
        public UserProfile createFromParcel(Parcel in) {
            return new UserProfile(in);
        }

        @Override
        public UserProfile[] newArray(int size) {
            return new UserProfile[size];
        }
    };

    public String getName(){
        return name;
    }
    public String getDescription(){
        return description;
    }
    public String getAvatar(){
        return avatar;
    }
    public ArrayList<Skill> getSkillset() {
        return skillset;
    }
    public ArrayList<Experience> getExpset() {return expset;}
    public ArrayList<Video> getVideoset() {return videoset;}
    public void setName(String name) {this.name = name;}
    public void setDescription(String des){
        description = des;
    }
    public void setSkill(String skl) {
        Skill sk = new Skill(skl);
        skillset.add(sk);
    }
    public void setExp(String experience) {
        Experience exp = new Experience(experience);
        expset.add(exp);

    }
    public void setVid(String videourl, String title) {
        Video video = new Video(videourl, title);
        videoset.add(video);


    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(description);
        dest.writeString(avatar);
        dest.writeTypedList(skillset);
        dest.writeTypedList(expset);
        dest.writeTypedList(videoset);

    }
}
