package com.simplify.util;

import android.content.Context;
import android.support.annotation.DrawableRes;
import android.widget.ImageView;

/**
 * Created by shujian on 5/7/16.
 */
public interface ImageLoader {

    /**
     * Load an image from a url into the given image view using the default placeholder if
     * available.
     * @param url The web URL of an image.
     * @param imageView The target ImageView to load the image into.
     */
    void loadImage(String url, ImageView imageView);

    /**
     * Load an image from a url into an ImageView using the default placeholder
     * drawable if available.
     * @param url The web URL of an image.
     * @param imageView The target ImageView to load the image into.
     * @param crop True to apply a center crop to the image.
     */
    void loadImage(String url, ImageView imageView, boolean crop);

    void loadImage(Context context, @DrawableRes int drawableResId, ImageView imageView);

}
