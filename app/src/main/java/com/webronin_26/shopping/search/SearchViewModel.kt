package com.webronin_26.shopping.search

import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.AppCompatImageView
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.webronin_26.shopping.Event
import com.webronin_26.shopping.data.remote.Response
import com.webronin_26.shopping.data.remote.Result
import com.webronin_26.shopping.data.source.ShoppingRepository
import kotlinx.coroutines.launch

class SearchViewModel: ViewModel(), LifecycleObserver {

    var repository : ShoppingRepository? = null

    val searchRecyclerViewVisible = MutableLiveData<Int>()

    val searchTextViewVisible = MutableLiveData<Int>()

    val products = MutableLiveData<Event<List<Response.SearchProduct>>>()

    var imageHashMap : HashMap<String , String> = HashMap()

    val queryActivityBundle = MutableLiveData<Event<Bundle>>()

    fun sendSearchRequest(searchString: String) {
        viewModelScope.launch {
            repository?.search(searchString).let { result ->
                if (result is Result.Success && result.data.count != 0) {
                    val productList =  result.data.data.toMutableList()
                    if (productList.size == 0) {
                        unFoundProducts()
                    } else {
                        foundProducts()
                        products.value = Event(productList)
                    }
                } else {
                    unFoundProducts()
                }
            }
        }
    }

    fun loadImage(urlString: String , imageView: AppCompatImageView , id : String) {
        viewModelScope.launch {
            repository?.getImage(urlString).let {
                if (it != null) {
                    if (imageHashMap[id] == urlString) {
                        imageView.setImageBitmap(it)
                    }
                }
            }
        }
    }

    private fun foundProducts() {
        searchRecyclerViewVisible.value = View.VISIBLE
        searchTextViewVisible.value = View.GONE
    }

    private fun unFoundProducts() {
        searchRecyclerViewVisible.value = View.GONE
        searchTextViewVisible.value = View.VISIBLE
    }
}