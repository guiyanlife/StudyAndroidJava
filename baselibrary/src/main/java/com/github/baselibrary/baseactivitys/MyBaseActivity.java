package com.github.baselibrary.baseactivitys;

import android.Manifest;

public class MyBaseActivity extends CheckPermissionsActivity {
    /**
     * 需要进行检测的权限数组
     */
    protected String[] needPermissions = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.REQUEST_INSTALL_PACKAGES,
            Manifest.permission.CAMERA
    };

    @Override
    protected int getLayoutId() {
        return 0;
    }

    @Override
    protected String[] YouNeedPermissions() {
        return needPermissions;
    }

}
