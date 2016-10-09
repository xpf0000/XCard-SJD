package com.x.custom;

import android.widget.Toast;

import com.bigkoo.svprogresshud.SVProgressHUD;
import com.bigkoo.svprogresshud.listener.OnDismissListener;
import com.com.x.AppModel.BannerModel;
import com.com.x.AppModel.HttpResult;
import com.com.x.AppModel.MoneyDetailModel;
import com.example.x.xcard.ApplicationClass;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import java.lang.ref.WeakReference;
import java.lang.reflect.ParameterizedType;
import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by X on 2016/10/1.
 */

public class XNetUtil {

    public interface OnHttpResult<T>
    {
        void onError(Throwable e);
        void onSuccess(T t);
    }

    final static public <T> void APPPrintln(T t)
    {
        System.out.println(t);
    }

    public static <T> void Handle(Observable<HttpResult<T>> obj,Subscriber<T> res) {

        obj
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .map(new HttpResultFunc<T>())
                .subscribe(res);

    }

    public static <T> void HandleReturnAll(Observable<HttpResult<T>> obj,final OnHttpResult<HttpResult<T>> res) {
        obj
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<HttpResult<T>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        res.onError(e);
                    }

                    @Override
                    public void onNext(HttpResult<T> tHttpResult) {
                        res.onSuccess(tHttpResult);
                    }
                });

    }


    public static <T> void Handle(Observable<HttpResult<T>> obj,final OnHttpResult<T> res) {
        obj
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .map(new HttpResultFunc<T>())
                .subscribe(new Subscriber<T>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        res.onError(e);
                    }

                    @Override
                    public void onNext(T t) {
                        res.onSuccess(t);
                    }
                });

    }


    public static <T> void Handle(Observable<HttpResult<T>> obj,String success,String fail,final OnHttpResult<Boolean> res) {

        obj
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .map(new HttpResultFuncBool<T>(success,fail))
                .subscribe(new Subscriber<Boolean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                        Toast.makeText(ApplicationClass.context, e.toString(), Toast.LENGTH_LONG).show();
                        res.onError(e);
                    }

                    @Override
                    public void onNext(Boolean aBoolean) {
                        res.onSuccess(aBoolean);
                    }
                });

    }

    /**
     * 用来统一处理Http的resultCode,并将HttpResult的Data部分剥离出来返回给subscriber
     *
     * @param <T> Subscriber真正需要的数据类型，也就是Data部分的数据类型
     */
    private static class HttpResultFunc<T> implements Func1<HttpResult<T>, T> {

        @Override
        public T call(HttpResult<T> httpResult) {
            if (httpResult.getRet() != 200) {

                XActivityindicator.create(ApplicationClass.context).showErrorWithStatus("数据加载失败!");
            }
            else
            {
                if(httpResult.getData().getCode() != 0)
                {
                    String msg = httpResult.getData().getMsg();
                    msg = msg.length() == 0 ? "数据加载失败!" : msg;
                    XActivityindicator.create(ApplicationClass.context).showErrorWithStatus(msg);
                }
            }

            return httpResult.getData().getInfo();
        }
    }

    private static class HttpResultFuncBool<T> implements Func1<HttpResult<T>, Boolean> {

        private String success = "";
        private String fail = "";

        public HttpResultFuncBool(String s,String f) {

            this.success = s;
            this.fail = f;
        }

        @Override
        public Boolean call(HttpResult<T> httpResult) {

            if (httpResult.getRet() != 200) {

                XActivityindicator.create(ApplicationClass.context).showErrorWithStatus(fail);

                return false;
            }
            else
            {
                if(httpResult.getData().getCode() != 0)
                {
                    String msg = httpResult.getData().getMsg();
                    msg = msg.length() == 0 ? fail : msg;
                    XActivityindicator.create(ApplicationClass.context).showErrorWithStatus(msg);
                    return false;
                }
            }

            if(success != null)
            {
                XActivityindicator.create(ApplicationClass.context).showSuccessWithStatus(success);
            }

            return true;
        }
    }


}
