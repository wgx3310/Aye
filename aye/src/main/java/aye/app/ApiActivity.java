package aye.app;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.text.TextUtilsCompat;
import android.text.TextUtils;

import aye.Constants;
import aye.ui.BaseActivity;

/**
 * Created by reid on 2016/11/19.
 */

public class ApiActivity extends BaseActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        handleIntent(getIntent());
    }

    private void handleIntent(Intent intent) {
        if (intent == null) {
            finish();
            return;
        }

        Uri data = intent.getData();
        if (data == null){
            finish();
            return;
        }

        String scheme = data.getScheme();
        String host = data.getHost();

        //如果schema不是aye或者host不是reid，则直接返回
        if (!Constants.API_SCHEMA.equalsIgnoreCase(scheme) || Constants.API_HOST.equalsIgnoreCase(host)){
            finish();
            return;
        }

        String path = data.getLastPathSegment();
        if (TextUtils.isEmpty(path)){
            finish();
            return;
        }


    }
}
