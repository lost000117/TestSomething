package com.myself.dosomething.baselibs;
/*
 *create by mac_hou on 2019/8/1 14:52
 *
 *
 *
 */


import android.app.ActivityManager;
import android.content.Context;
import android.os.Build;
import android.webkit.WebView;

import com.socks.library.KLog;
import androidx.multidex.MultiDexApplication;

public class SomethingApplication extends MultiDexApplication {


    @Override
    public void onCreate() {
        super.onCreate();
        KLog.e("FotoPlayApplication start");


        baseutil.context = getApplicationContext();
        baseutil.dpsize =getResources().getDimension(R.dimen.size1);
        baseutil.context = this;

    }


    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        //webview android 9 + 线程问题
        KLog.e("attachBaseContext");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            String processName = getProcessName(this);
            String packageName = this.getPackageName();
            if (!packageName.equals(processName)) {
                WebView.setDataDirectorySuffix(processName);
            }
        }
    }
    private String getProcessName(Context context) {
        if (context == null) return null;
        ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningAppProcessInfo processInfo : manager.getRunningAppProcesses()) {
            if (processInfo.pid == android.os.Process.myPid()) {
                return processInfo.processName;
            }
        }
        return null;
    }
}
