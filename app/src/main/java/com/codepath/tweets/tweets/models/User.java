package com.codepath.tweets.tweets.models;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by VRAJA03 on 2/19/2016.
 */
public class User {
    private String name;
    private long uid;
    private String screenName;
    private String profileImageUrl;

    public String getProfileImageUrl() {
        return profileImageUrl;
    }

    public String getScreenName() {
        return screenName;
    }

    public long getUid() {
        return uid;
    }

    public String getName() {
        return name;
    }

    public static User fromJSON(JSONObject json){
        User user = new User();

        try {
            user.name = json.getString("name");
            user.uid = json.getLong("id");
            user.screenName = json.getString("screen_name");
            user.profileImageUrl = json.getString("profile_image_url");
        } catch(JSONException e){
            e.printStackTrace();
        }

        return user;



    }

}
