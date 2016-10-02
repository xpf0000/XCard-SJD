package com.x.custom;

import com.com.x.AppModel.BannerModel;
import com.com.x.AppModel.HttpResult;
import com.com.x.AppModel.UserModel;

import java.util.List;

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
}
