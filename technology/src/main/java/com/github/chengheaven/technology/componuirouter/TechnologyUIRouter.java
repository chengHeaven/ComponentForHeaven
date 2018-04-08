package com.github.chengheaven.technology.componuirouter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;

import com.github.chengheaven.technology.view.technology.TechnologyActivity;
import com.github.chengheaven.componentlib.router.ui.IComponentRouter;

/**
 * @author Heaven・Cheng Created on 2017/9/26.
 */

public class TechnologyUIRouter implements IComponentRouter {

    private static final String SCHEME = "component";

    private static final String SUBHOST = "technology";

    private static String[] HOSTS = new String[]{SUBHOST};

    private static TechnologyUIRouter instance = new TechnologyUIRouter();

    private TechnologyUIRouter() {

    }

    public static TechnologyUIRouter getInstance() {
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
        if (SUBHOST.equals(host)) {
            Intent intent = new Intent(context, TechnologyActivity.class);
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
