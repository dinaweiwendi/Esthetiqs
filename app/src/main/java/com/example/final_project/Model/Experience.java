package com.example.final_project.Model;

import android.os.Parcel;
import android.os.Parcelable;

public class Experience implements Parcelable {
    private String mExp;
    public Experience(String skill) {
        this.mExp = skill;
    }

    protected Experience(Parcel in) {
        mExp = in.readString();
    }

    public static final Creator<Experience> CREATOR = new Creator<Experience>() {
        @Override
        public Experience createFromParcel(Parcel in) {
            return new Experience(in);
        }

        @Override
        public Experience[] newArray(int size) {
            return new Experience[size];
        }
    };

    public String getexp(){
        return mExp;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mExp);
    }
}


