package com.x.custom;

import android.support.annotation.Nullable;

import com.com.x.AppModel.BannerModel;
import com.com.x.AppModel.HttpResult;
import com.com.x.AppModel.UserModel;

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

    @POST("?service=User.updatePass2")//修改用户手机号
    Observable<HttpResult<Object>> userUpdatePass2(@Query("mobile") String mobile
            , @Query("oldpass") String oldpass
            , @Query("newpass") String newpass
    );

}
