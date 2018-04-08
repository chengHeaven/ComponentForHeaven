package com.github.chengheaven.book.componuirouter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;

import com.github.chengheaven.book.view.BookActivity;
import com.github.chengheaven.componentlib.router.ui.IComponentRouter;

/**
 * @author Heaven_Cheng Created on 2018/1/1.
 */
public class BookUiRouter implements IComponentRouter {

    private static final String SCHEME = "component";

    private static final String SUBHOST = "book";

    private static String[] HOSTS = new String[]{SUBHOST};

    private static BookUiRouter sInstance;

    private BookUiRouter() {

    }

    public static BookUiRouter getInstance() {
        return sInstance;
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
            Intent intent = new Intent(context, BookActivity.class);
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
