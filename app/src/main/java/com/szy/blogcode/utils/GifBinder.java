package com.szy.blogcode.utils;

import android.annotation.TargetApi;
import android.graphics.Movie;
import android.os.Build;
import android.view.View;
import android.widget.ImageView;

import org.xutils.image.GifDrawable;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * 类描述：
 * 创建人：sunzhenyu
 * 创建时间：2016/1/25 14:32
 */
public class GifBinder {

    public static void bindGif(ImageView imageView,InputStream in) throws IOException {
        bindGif(imageView, in, null);
    }

    /**
     * 转换文件为Movie, 可用于创建GifDrawable.
     *
     * @return
     * @throws IOException
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public static void bindGif(ImageView imageView,InputStream in,ImageView.ScaleType scaleType) throws IOException {
        if (in == null || imageView==null) return;
        if (scaleType==null)
        {
            scaleType=ImageView.ScaleType.CENTER_CROP;
        }
        try {
            int buffSize = 1024 * 16;
            in = new BufferedInputStream(in, buffSize);
            in.mark(buffSize);
            Movie movie = Movie.decodeStream(in);
            if (movie == null) {
                throw new IOException("decode image error");
            }
            if (movie != null) {
                GifDrawable drawable = new GifDrawable(movie, (movie.width() * movie.height() * 3) * (5));
                imageView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
                imageView.setScaleType(scaleType);
                imageView.setImageDrawable(drawable);
            }
        } catch (IOException ex) {
            throw ex;
        }
    }
}
