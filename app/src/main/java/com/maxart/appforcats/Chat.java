package com.maxart.appforcats;

import android.widget.ImageView;

public class Chat {

    private String name;
    private int profile_picture;


    public Chat(String name, int profile_picture) {
        this.name = name;
        this.profile_picture = profile_picture;
    }

    public Chat(String name) {
        this.name = name;
        this.profile_picture = R.drawable.profile_icon;
    }



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getProfile_picture() {
        return profile_picture;
    }

    public void setProfile_picture(int profile_picture) {
        this.profile_picture = profile_picture;
    }

}
