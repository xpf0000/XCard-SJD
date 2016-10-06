package com.x.custom;

import android.support.annotation.Nullable;

import com.com.x.AppModel.BannerModel;
import com.com.x.AppModel.GangweiModel;
import com.com.x.AppModel.HttpResult;
import com.com.x.AppModel.PowerModel;
import com.com.x.AppModel.UserModel;
import com.com.x.AppModel.YuangongModel;

import java.util.List;
import java.util.Objects;

import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;


/**
 * Created by X on 2016/10/1.
 */

public interface ServicesAPI {

 String APPUrl = "http://182.92.70.85/hfshopapi/Public/Found/";

    @GET("?service=Setting.getGuanggao&typeid=83")
    Observable<HttpResult<List<BannerModel>>> getBanner();

    @POST("?service=User.login")
    Observable<HttpResult<List<UserModel>>> doLogin(@Query("mobile") String mobile, @Query("password") String password);

    @POST("?service=User.getUserInfoM")//根据手机号获取会员信息
    Observable<HttpResult<List<UserModel>>> userGetUserInfoM(@Query("mobile") String mobile);

    @POST("?service=User.smsSend")//发送验证码  type: 1 注册 2 找回密码
    Observable<HttpResult<Object>> userSmsSend(@Query("mobile") String mobile, @Query("type") String type);

   @POST("?service=User.updateMobile")//修改用户手机号
   Observable<HttpResult<Object>> userUpdateMobile(@Query("mobile") String mobile
           , @Query("newmobile") String newmobile
           , @Query("code") String code
   );

    @POST("?service=User.updatePass2")//修改用户密码
    Observable<HttpResult<Object>> userUpdatePass2(@Query("mobile") String mobile
            , @Query("oldpass") String oldpass
            , @Query("newpass") String newpass
    );

    @POST("?service=User.smsVerify")//短信验证
    Observable<HttpResult<Object>> userSmsVerify(@Query("mobile") String mobile
            , @Query("code") String code
    );

    @POST("?service=User.updatePass")//重置密码
    Observable<HttpResult<Object>> userUpdatePass(@Query("mobile") String mobile
            , @Query("code") String code
            , @Query("password") String password
    );

    @POST("?service=Shopd.updateShopInfo")//修改店铺资料
    Observable<HttpResult<Object>> shopdUpdateShopInfo(@Query("id") String sid
            , @Query("address") String address
            , @Query("tel") String tel
    );

    @POST("?service=Shopd.getShopInfo")//获取商家详情
    Observable<HttpResult<List<UserModel>>> shopdGetShopInfo(@Query("id") String sid);

    @POST("?service=Power.getShopWorker")//员工列表
    Observable<HttpResult<List<YuangongModel>>> powerGetShopWorker(@Query("id") String sid
            , @Query("page") String page
            , @Query("perNumber") String perNumber
    );

    @POST("?service=Power.getShopJob")//岗位列表
    Observable<HttpResult<List<GangweiModel>>> powerGetShopJob(@Query("id") String sid
            , @Query("page") String page
            , @Query("perNumber") String perNumber
    );

    @POST("?service=Power.addShopJob")//添加岗位
    Observable<HttpResult<Object>> powerAddShopJob(@Query("shopid") String shopid
            , @Query("name") String name
    );

    @POST("?service=Power.updateShopJob")//修改岗位
    Observable<HttpResult<Object>> powerUpdateShopJob(@Query("id") String id
            , @Query("name") String name
    );

    @GET("?service=Setting.getShopPower")//获取全部权限
    Observable<HttpResult<List<PowerModel>>> settingGetShopPower();

    @POST("?service=Power.updateJobPower")//修改岗位
    Observable<HttpResult<Object>> powerUpdateJobPower(@Query("shopid") String shopid
            , @Query("jobid") String jobid
            , @Query("power") String power
    );

    @POST("?service=Shopd.getUserInfoM")//手机号获取用户信息
    Observable<HttpResult<List<UserModel>>> shopdGetUserInfoM(@Query("mobile") String mobile
            , @Query("shopid") String shopid
    );

    @POST("?service=Power.addShopWorker")//添加员工
    Observable<HttpResult<Object>> powerAddShopWorker(@Query("uid") String uid
            , @Query("shopid") String shopid
            , @Query("jobid") String jobid
            , @Query("wnumber") String wnumber
    );

}
