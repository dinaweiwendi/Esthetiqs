package com.example.final_project.Model;

import android.os.Parcel;
import android.os.Parcelable;

public class Skill implements Parcelable {
    private String mSkill;
    public Skill(String skill) {
        this.mSkill = skill;
    }

    protected Skill(Parcel in) {
        mSkill = in.readString();
    }

    public static final Creator<Skill> CREATOR = new Creator<Skill>() {
        @Override
        public Skill createFromParcel(Parcel in) {
            return new Skill(in);
        }

        @Override
        public Skill[] newArray(int size) {
            return new Skill[size];
        }
    };

    public String getskill(){
        return mSkill;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mSkill);
    }
}

