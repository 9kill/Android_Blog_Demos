package com.szy.blogcode.http;

import com.szy.blogcode.utils.BaseConstant;

import org.xutils.common.Callback;
import org.xutils.common.util.LogUtil;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.net.UnknownHostException;

/**
 * 类描述：异步回调
 * 创建人：sunzhenyu
 * 创建时间：2016/1/20 16:35
 */
public class ResponseCallback<T> implements Callback.CommonCallback,Callback.ProgressCallback {
    public boolean mSuccess;

    public BaseResponse mBaseResponse;

    protected Class<T> mType;

    public ResponseCallback() {
        Type genType = getClass().getGenericSuperclass();
        if (genType instanceof ParameterizedType) {
            try {
                mType = ((Class<T>) (((ParameterizedType) (genType)).getActualTypeArguments()[0]));
            } catch (ClassCastException e) {

            }
        }
    }

    @Override
    public void onWaiting() {

    }

    @Override
    public void onStarted() {

    }

    @Override
    public void onLoading(long total, long current, boolean isDownloading) {

    }

    @Override
    public void onSuccess(Object result) {
        LogUtil.d(BaseConstant.LOG_PREFIX + "result->" + result);

    }

    @Override
    public void onError(Throwable ex, boolean isOnCallback) {
        if (ex instanceof UnknownHostException) {//断网，或者主机不存在
            LogUtil.d(ex.getMessage());
        } else {

        }
    }

    @Override
    public void onCancelled(CancelledException cex) {

    }

    @Override
    public void onFinished() {

    }
}
