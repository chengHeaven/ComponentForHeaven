package com.github.chengheaven.technology.util;

import com.github.chengheaven.technology.app.TechnologyApp;

/**
 * @author Heaven_Cheng Created on 2017/12/28.
 */
public class ResourceUtil {

    public static String getString(int string) {
        return TechnologyApp.getInstance().mContext.getString(string);
    }
}
