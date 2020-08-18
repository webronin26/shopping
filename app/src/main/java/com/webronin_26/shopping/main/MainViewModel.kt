package com.webronin_26.shopping.main

import android.graphics.Bitmap
import androidx.lifecycle.*
import com.webronin_26.shopping.data.remote.Response
import com.webronin_26.shopping.data.remote.Result
import com.webronin_26.shopping.data.source.ShoppingRepository
import kotlinx.coroutines.launch

class MainViewModel : ViewModel(), LifecycleObserver {

    var repository: ShoppingRepository? = null

    val hotSalesAd01 = MutableLiveData<Bitmap>()
    val hotSalesAd02 = MutableLiveData<Bitmap>()

    val seasonProductImageView01 = MutableLiveData<Bitmap>()
    val seasonProductTextView01 = MutableLiveData<String>()

    val seasonProductImageView02 = MutableLiveData<Bitmap>()
    val seasonProductTextView02 = MutableLiveData<String>()

    val seasonProductImageView03 = MutableLiveData<Bitmap>()
    val seasonProductTextView03 = MutableLiveData<String>()

    val seasonProductImageView04 = MutableLiveData<Bitmap>()
    val seasonProductTextView04 = MutableLiveData<String>()

    val limitedProductImageView01 = MutableLiveData<Bitmap>()
    val limitedProductTextView01 = MutableLiveData<String>()

    val limitedProductImageView02 = MutableLiveData<Bitmap>()
    val limitedProductTextView02 = MutableLiveData<String>()

    val limitedProductImageView03 = MutableLiveData<Bitmap>()
    val limitedProductTextView03 = MutableLiveData<String>()

    val limitedProductImageView04 = MutableLiveData<Bitmap>()
    val limitedProductTextView04 = MutableLiveData<String>()

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun refreshMainPage() {
        viewModelScope.launch {
            repository?.refreshMainPage().let { result ->
                if (result is Result.Success) {
                    setData(result.data.data)
                }
            }
        }
    }

    private fun setImage(urlString: String?, liveData: MutableLiveData<Bitmap>) {
        if (urlString != null) {
            viewModelScope.launch {
                repository?.getImage(urlString).let { bitmap ->
                    liveData.value = bitmap
                }
            }
        }
    }

    private fun setData(data: Response.MainResponseData) {
        setImage(data.hotSales.firstPic, hotSalesAd01)
        setImage(data.hotSales.secondPic, hotSalesAd02)

        setImage(data.seasonAd.firstPic, seasonProductImageView01)
        seasonProductTextView01.value = data.seasonAd.firstText
        setImage(data.seasonAd.secondPic, seasonProductImageView02)
        seasonProductTextView02.value = data.seasonAd.secondText
        setImage(data.seasonAd.thirdPic, seasonProductImageView03)
        seasonProductTextView03.value = data.seasonAd.thirdText
        setImage(data.seasonAd.fourPic, seasonProductImageView04)
        seasonProductTextView04.value = data.seasonAd.fourText

        setImage(data.LimitedProduct.firstPic, limitedProductImageView01)
        limitedProductTextView01.value = data.LimitedProduct.firstText
        setImage(data.LimitedProduct.secondPic, limitedProductImageView02)
        limitedProductTextView02.value = data.LimitedProduct.secondText
        setImage(data.LimitedProduct.thirdPic, limitedProductImageView03)
        limitedProductTextView03.value = data.LimitedProduct.thirdText
        setImage(data.LimitedProduct.fourPic, limitedProductImageView04)
        limitedProductTextView04.value = data.LimitedProduct.fourText
    }
}
