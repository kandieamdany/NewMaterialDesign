package com.smartdevelopers.kandie.nicedrawer.model;

import android.content.Context;

/**
 * Created by 4331 on 09/07/2015.
 */
public class Country {
    public String name;
    public String description;
    public String imageName;


    public int getImageResourceId(Context context)
    {
        try {
            return context.getResources().getIdentifier(this.imageName, "drawable", context.getPackageName());

        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

}
