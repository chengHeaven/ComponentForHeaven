package com.github.chengheaven.componentlib.router;

import android.content.Context;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.github.chengheaven.componentlib.app.IApp;

import java.util.HashMap;

/**
 * @author Heaven・Cheng Created on 2017/9/21.
 */

public class Router {

    private HashMap<String, Object> services = new HashMap<>();
    //注册的组件的集合
    private static HashMap<String, IApp> components = new HashMap<>();

    private static volatile Router instance;

    private Router() {
    }

    public static Router getInstance() {
        if (instance == null) {
            synchronized (Router.class) {
                if (instance == null) {
                    instance = new Router();
                }
            }
        }
        return instance;
    }

    public synchronized void addService(String serviceName, Object serviceImpl) {
        if (serviceName == null || serviceImpl == null) {
            return;
        }
        services.put(serviceName, serviceImpl);
    }

    public synchronized Object getService(String serviceName) {
        if (serviceName == null) {
            return null;
        }
        return services.get(serviceName);
    }

    public synchronized void removeService(String serviceName) {
        if (serviceName == null) {
            return;
        }
        services.remove(serviceName);
    }

    /**
     * 注册组件
     *
     * @param classname 组件名
     */
    public static void registerComponent(Context context, @Nullable String classname) {
        if (TextUtils.isEmpty(classname)) {
            return;
        }
        if (components.keySet().contains(classname)) {
            return;
        }
        try {
            Class clazz = Class.forName(classname);
            IApp app = (IApp) clazz.newInstance();
            app.create(context);
            components.put(classname, app);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 反注册组件
     *
     * @param classname 组件名
     */
    public static void unregisterComponent(@Nullable String classname) {
        if (TextUtils.isEmpty(classname)) {
            return;
        }
        if (components.keySet().contains(classname)) {
            components.get(classname).stop();
            components.remove(classname);
            return;
        }
        try {
            Class clazz = Class.forName(classname);
            IApp app = (IApp) clazz.newInstance();
            app.stop();
            components.remove(classname);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
