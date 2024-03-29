package com.github.commonlibs.libupdateapputils.util;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

import com.github.commonlibs.libfileprovider.FileProvider7;

import java.io.File;



/**
 * Created by Teprinciple on 2017/11/3.
 */

public class UpdateAppReceiver extends BroadcastReceiver {


    @Override
    public void onReceive(Context context, Intent intent) {

        int notifyId = 1;
        int progress = intent.getIntExtra("progress", 0);
        String title = intent.getStringExtra("title");

        NotificationManager nm = null;
        if (UpdateAppUtils.showNotification) {
            NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
            builder.setContentTitle("正在下载 " + title);
            builder.setSmallIcon(android.R.mipmap.sym_def_app_icon);
            builder.setProgress(100, progress, false);

            Notification notification = builder.build();
            nm = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            nm.notify(notifyId, notification);
        }

        if(UpdateAppUtils.showProgress)
            UpdateAppUtils.mProgressDialog.setProgress(progress);

        if (progress == 100) {
            if (nm != null) nm.cancel(notifyId);

            if(UpdateAppUtils.showProgress)
                UpdateAppUtils.mProgressDialog.dismiss();

            if (DownloadAppUtils.downloadUpdateApkFilePath != null) {
                File apkFile = new File(DownloadAppUtils.downloadUpdateApkFilePath);
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                FileProvider7.setIntentDataAndType(context, i, "application/vnd.android.package-archive", apkFile, true);
                context.startActivity(i);
            }
        }
    }
}
