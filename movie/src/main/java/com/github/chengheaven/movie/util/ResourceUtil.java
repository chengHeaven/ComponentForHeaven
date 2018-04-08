package com.github.chengheaven.movie.util;

import com.github.chengheaven.movie.app.MovieApp;

/**
 * @author Heaven_Cheng Created on 2017/12/28.
 */
public class ResourceUtil {

    public static String getString(int string) {
        return MovieApp.getInstance().mContext.getString(string);
    }

    public static float getDimens(int resId) {
        return MovieApp.getInstance().mContext.getResources().getDimension(resId);
    }
}
