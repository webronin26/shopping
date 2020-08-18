package com.webronin_26.shopping.data.remote

import com.google.gson.annotations.SerializedName

class Response {

    /**
     *  MainPage
     */
    data class MainResponse (val count: Int, val code: Int, val data: MainResponseData)

    data class MainResponseData (@SerializedName("hot_sales") val hotSales: HotSales,
                                 @SerializedName("season_ad") val seasonAd: SeasonAd,
                                 @SerializedName("limited_product") val LimitedProduct: LimitedProduct)

    data class HotSales (@SerializedName("first_pic") val firstPic: String,
                         @SerializedName("second_pic") val secondPic: String)

    data class SeasonAd (@SerializedName("first_pic") val firstPic: String,
                         @SerializedName("second_pic") val secondPic: String,
                         @SerializedName("third_pic") val thirdPic: String,
                         @SerializedName("four_pic") val fourPic: String,
                         @SerializedName("first_text") val firstText: String,
                         @SerializedName("second_text")val secondText: String,
                         @SerializedName("third_text")val thirdText: String,
                         @SerializedName("four_text")val fourText: String)

    data class LimitedProduct (@SerializedName("first_pic") val firstPic: String,
                               @SerializedName("second_pic") val secondPic: String,
                               @SerializedName("third_pic") val thirdPic: String,
                               @SerializedName("four_pic") val fourPic: String,
                               @SerializedName("first_text") val firstText: String,
                               @SerializedName("second_text")val secondText: String,
                               @SerializedName("third_text")val thirdText: String,
                               @SerializedName("four_text")val fourText: String)

    /**
     *  Login
     */
    data class LoginResponse (val count: Int, val code: Int, val data: LoginResponseData)

    data class LoginResponseData (val token: String, val id: Int, val name: String)

    /**
     *  Logout
     */
    data class LogoutResponse (val count: Int, val code: Int)

    /**
     *  Search
     */
    data class SearchResponse (val count: Int, val code: Int, val data: Array<SearchProduct>)

    data class SearchProduct (val id: Int,
                              val name: String,
                              val price: Int,
                              @SerializedName("image_url") val imageUrl: String)

    /**
     *  Query
     */
    data class QueryResponse (val count: Int, val code: Int, val data: QueryProduct)

    data class QueryProduct (@SerializedName("product_info") val productInfo: String,
                             @SerializedName("image_url") val imageUrl: String,
                             @SerializedName("max_buy") val maxBuy: Int,
                             @SerializedName("item_number") val itemNumber: Int)

    /**
     *  buy product
     */
    data class BuyProductResponse (val count: Int, val code: Int)
}
