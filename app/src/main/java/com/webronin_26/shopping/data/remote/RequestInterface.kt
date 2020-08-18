package com.webronin_26.shopping.data.remote

import okhttp3.ResponseBody
import retrofit2.http.*

interface RequestInterface {

    @Headers(
        "Cache-Control: max-age=0",
        "Upgrade-Insecure-Requests: 1",
        "user-agent: Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)",
        "Accept: text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9",
        "Accept-Encoding: gzip, deflate, br",
        "Accept-Language: zh-TW,zh;q=0.9,en-US;q=0.8,en;q=0.7,ja;q=0.6,zh-CN;q=0.5,lb;q=0.4")
    @GET
    suspend fun downloadPicture(@Url path: String): ResponseBody

    @GET("/main")
    suspend fun mainRequest(): Response.MainResponse

    @FormUrlEncoded
    @POST("/login")
    suspend fun loginRequest(@Field("email") email: String,
                             @Field("password") password: String): Response.LoginResponse

    @DELETE("/member/logout")
    suspend fun logoutRequest(@Header("Authorization") token: String): Response.LogoutResponse

    @GET("/search")
    suspend fun searchRequest(@Query("q") q: String): Response.SearchResponse

    @GET("/query/product/{product_id}")
    suspend fun queryRequest(@Path("product_id") id: Int): Response.QueryResponse

    @FormUrlEncoded
    @POST("/member/buy")
    suspend fun buyRequest(@Header("Authorization") token: String
                           ,@Field("product_id")id: Int
                           ,@Field("number")number: Int): Response.BuyProductResponse
}