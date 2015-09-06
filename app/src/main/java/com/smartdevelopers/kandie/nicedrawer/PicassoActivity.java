package com.smartdevelopers.kandie.nicedrawer;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

/**
 * Created by 4331 on 20/07/2015.
 */
public class PicassoActivity extends AppCompatActivity {
    String user_pic;
    ImageView imageView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picasso);

        imageView=(ImageView) findViewById(R.id.imgPic);

        Intent intent=getIntent();
        user_pic=intent.getStringExtra("picture");

        Picasso.with(this).load(user_pic).into(imageView);


    }
}
