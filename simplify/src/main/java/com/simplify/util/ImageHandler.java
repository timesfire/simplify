package com.simplify.util;

import android.widget.ImageView;

/**
 * Created by shujian on 5/7/16.
 */
public interface ImageHandler {

    /**
     * Load an image from a url into the given image view using the default placeholder if
     * available.
     * @param url The web URL of an image.
     * @param imageView The target ImageView to load the image into.
     */
    void loadImage(String url, ImageView imageView);

}
