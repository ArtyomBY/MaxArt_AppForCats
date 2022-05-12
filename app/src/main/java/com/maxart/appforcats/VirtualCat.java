package com.maxart.appforcats;

public class VirtualCat {

    private String name;
    private int profile_picture;
    private int audio;

    public VirtualCat(String name, int profile_picture, int audio) {
        this.name = name;
        this.profile_picture = profile_picture;
        this.audio = audio;
    }

    public VirtualCat(){
        this.name = "Кот Борис";
        this.profile_picture = R.drawable.cat_boris;
        this.audio = R.raw.kot_myaukane;
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

    public int getAudio() {
        return audio;
    }

    public void setAudio(int audio) {
        this.audio = audio;
    }
}
