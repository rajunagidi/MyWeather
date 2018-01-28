package com.prudvi.weather.util;

import android.content.Context;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

/**
 * Created by ESHAN on 1/26/2018.
 */

public class PicassoLoadIcon {

    public static void loadImage(Context context, ImageView imageView, String url){
        Picasso.with(context).load(url).into(imageView);
    }
}
