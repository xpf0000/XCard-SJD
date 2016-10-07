package com.x.custom;

import com.com.x.AppModel.ActivityModel;
import com.com.x.AppModel.BannerModel;
import com.com.x.AppModel.CardTypeModel;
import com.com.x.AppModel.GangweiModel;
import com.com.x.AppModel.HttpResult;
import com.com.x.AppModel.MessageModel;
import com.com.x.AppModel.PowerModel;
import com.com.x.AppModel.UserModel;
import com.com.x.AppModel.ValueSumModel;
import com.com.x.AppModel.YuangongModel;

import java.util.List;
import java.util.Map;

import okhttp3.RequestBody;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PartMap;
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

    @POST("?service=Hyk.getShopCard")//获取商家会员卡列表
    Observable<HttpResult<List<CardTypeModel>>> hykGetShopCard(@Query("shopid") String shopid
            , @Query("uid") String uid
    );

    @POST("?service=Shopd.addShopCard")//新增商家会员卡
    Observable<HttpResult<Object>> shopdAddShopCard(@Query("shopid") String shopid
            , @Query("color") String color
            , @Query("info") String info
            , @Query("typeid") String typeid
    );

    @POST("?service=Shopd.updateShopCard")//编辑商家会员卡
    Observable<HttpResult<Object>> shopdUpdateShopCard(@Query("id") String id
            , @Query("color") String color
            , @Query("info") String info
    );

 @POST("?service=Shopd.delShopCard")//作废会员卡
 Observable<HttpResult<Object>> shopdDelShopCard(@Query("id") String id
 );

 @POST("?service=Shopa.getMessagesList")//消息管理列表
 Observable<HttpResult<List<MessageModel>>> shopaGetMessagesList(@Query("shopid") String shopid
         , @Query("page") String page
         , @Query("perNumber") String perNumber
 );

 @POST("?service=Shopa.addMessages")//新增消息
 Observable<HttpResult<Object>> shopaAddMessages(@Query("uid") String uid
         , @Query("username") String username
         , @Query("title") String title
         , @Query("content") String content
 );

 @POST("?service=Shopa.getShopHD")//活动列表
 Observable<HttpResult<List<ActivityModel>>> shopaGetShopHD(@Query("id") String sid
         , @Query("page") String page
         , @Query("perNumber") String perNumber
 );

 @Multipart
 @POST("?service=Shopa.addShopHD")//创建活动
 Observable<HttpResult<Object>> shopaAddShopHD(@PartMap Map<String , RequestBody> params);

 @POST("?service=Shopa.delShopHD")//作废活动
 Observable<HttpResult<Object>> shopaDelShopHD(@Query("id") String id);

 @POST("?service=Shopd.getShopUser")//商家会员列表
 Observable<HttpResult<List<UserModel>>> shopdGetShopUser(@Query("id") String sid
         , @Query("page") String page
         , @Query("perNumber") String perNumber
 );

 @POST("?service=Hyk.getShopCardY")//获取用户已领取会员卡列表
 Observable<HttpResult<List<CardTypeModel>>> hykGetShopCardY(@Query("shopid") String sid
         , @Query("uid") String uid
 );

 @POST("?service=Shopt.getValueSum")//获取商家充值统计
 Observable<HttpResult<ValueSumModel>> shoptGetValueSum(@Query("shopid") String sid);

}
