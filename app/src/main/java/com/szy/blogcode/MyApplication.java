package com.szy.blogcode;

import android.app.Application;
import android.os.Environment;

import org.xutils.x;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * 类描述：
 * 创建人：sunzhenyu
 * 创建时间：2016/1/26 10:29
 */
public class MyApplication extends Application {
    private static final String LOG_PATH=Environment.getExternalStorageDirectory().getPath()+"/android_blog_demos/log";
    @Override
    public void onCreate() {
        super.onCreate();
        Thread.setDefaultUncaughtExceptionHandler(new ExHandler(Thread.getDefaultUncaughtExceptionHandler()));
        x.Ext.init(this);
    }

    // 捕获程序崩溃的异常,记录log(可以考虑将异常信息发回服务器)
    private class ExHandler implements Thread.UncaughtExceptionHandler {
        private Thread.UncaughtExceptionHandler internal;

        private ExHandler(Thread.UncaughtExceptionHandler eh) {
            internal = eh;
        }

        @Override
        public void uncaughtException(Thread thread, Throwable ex) {
            File file = new File(LOG_PATH);
            if (!file.exists()) {
                file.mkdirs();
            }
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault());
            String fname = sdf.format(new Date());
            try {
                PrintStream ps = new PrintStream(file.getAbsolutePath() + "/" + fname);
                ps.println(ex.getMessage());
                ex.printStackTrace(ps);
                ps.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            internal.uncaughtException(thread, ex);
        }
    }
}
