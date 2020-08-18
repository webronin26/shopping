package com.webronin_26.shopping.query

import android.content.Intent
import android.graphics.Bitmap
import androidx.lifecycle.*
import com.webronin_26.shopping.Event
import com.webronin_26.shopping.VIEWMODEL_INTERNET_ERROR
import com.webronin_26.shopping.VIEWMODEL_INTERNET_SUCCESS
import com.webronin_26.shopping.data.remote.Result
import com.webronin_26.shopping.data.source.ShoppingRepository
import kotlinx.coroutines.launch

class QueryViewModel: ViewModel(), LifecycleObserver {

    var repository: ShoppingRepository? = null

    val viewModelStatus = MutableLiveData<Event<Int>>()

    val currentBuyNumber = MutableLiveData<Int>()

    val productName = MutableLiveData<String>()

    val productPrice = MutableLiveData<Int>()

    private val productID = MutableLiveData<Int>()

    val productPicture = MutableLiveData<Bitmap>()

    val productInfo = MutableLiveData<String>()

    val productNumber = MutableLiveData<Int>()

    private var maxBuy = MutableLiveData<Int>()

    fun plusButtonClick() { if (maxBuy.value!! > currentBuyNumber.value!!) { currentBuyNumber.value!! + 1 } }

    fun minusButtonClick() { if (currentBuyNumber.value!! > 0) { currentBuyNumber.value!! - 1 } }

    fun setData(intent: Intent) {
        productName.value = intent.getStringExtra("name")
        productPrice.value = intent.getIntExtra("price", 0)
        productID.value = intent.getIntExtra("id",0)

        currentBuyNumber.value = 0
        maxBuy.value = 0
        productNumber.value = 0
        productInfo.value = ""
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    private fun refreshQueryPage() {
        if (productID.value != null && productID.value != 0) {
            viewModelScope.launch {
                repository?.query(productID.value!!).let { result ->
                    if (result is Result.Success) {
                        maxBuy.value = result.data.data.maxBuy
                        productInfo.value = result.data.data.productInfo
                        productNumber.value = result.data.data.itemNumber

                        setImage(result.data.data.imageUrl, productPicture)
                    }
                }
            }
        }
    }

    fun buy(token: String) {
        if (productID.value != 0 && currentBuyNumber.value != 0 ) {
            viewModelScope.launch {
                repository?.buy(token = token
                    , id = productID.value!!
                    , number = currentBuyNumber.value!!).let { result ->

                    if (result is Result.Success) {
                        viewModelStatus.value = Event(VIEWMODEL_INTERNET_SUCCESS)
                        refreshQueryPage()
                    } else {
                        viewModelStatus.value = Event(VIEWMODEL_INTERNET_ERROR)
                    }
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
}