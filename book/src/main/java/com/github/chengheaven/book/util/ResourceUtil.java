package com.github.chengheaven.book.util;


import com.github.chengheaven.book.app.BookApp;

/**
 * @author Heaven_Cheng Created on 2017/12/28.
 */
public class ResourceUtil {

    public static String getString(int string) {
        return BookApp.getInstance().mContext.getString(string);
    }

    public static float getDimens(int resId) {
        return BookApp.getInstance().mContext.getResources().getDimension(resId);
    }
}
