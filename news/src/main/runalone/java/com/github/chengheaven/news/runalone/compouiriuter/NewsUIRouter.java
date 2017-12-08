package com.github.chengheaven.news.runalone.compouiriuter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;

import com.github.chengheaven.componentlib.router.ui.IComponentRouter;
import com.github.chengheaven.news.runalone.view.news.NewsTestActivity;

/**
 * @author Heaven・Cheng Created on 2017/9/26.
 */

public class NewsUIRouter implements IComponentRouter {

    private static final String SCHEME = "component";

    private static final String SHAREHOST = "news";

    private static String[] HOSTS = new String[]{SHAREHOST};

    private static NewsUIRouter instance = new NewsUIRouter();

    private NewsUIRouter() {

    }

    public static NewsUIRouter getInstance() {
        return instance;
    }

    @Override
    public boolean openUri(Context context, String url, Bundle bundle) {
        return TextUtils.isEmpty(url) || context == null || openUri(context, Uri.parse(url), bundle);
    }

    @Override
    public boolean openUri(Context context, Uri uri, Bundle bundle) {
        if (uri == null || context == null) {
            return true;
        }
        String host = uri.getHost();
        if (SHAREHOST.equals(host)) {
            Intent intent = new Intent(context, NewsTestActivity.class);
            intent.putExtras(bundle == null ? new Bundle() : bundle);
            context.startActivity(intent);
            return true;
        }
        return false;
    }

    @Override
    public boolean verifyUri(Uri uri) {
        String scheme = uri.getScheme();
        String host = uri.getHost();
        if (SCHEME.equals(scheme)) {
            for (String str : HOSTS) {
                if (str.equals(host)) {
                    return true;
                }
            }
        }
        return false;
    }
}
