package com.webronin_26.shopping.search

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.webronin_26.shopping.EventObserver
import com.webronin_26.shopping.R
import com.webronin_26.shopping.ShoppingApplication
import com.webronin_26.shopping.databinding.SearchActivityBinding
import com.webronin_26.shopping.query.QueryActivity
import kotlinx.android.synthetic.main.search_activity.*

class SearchActivity : AppCompatActivity() {

    private lateinit var viewModel: SearchViewModel

    private lateinit var viewDataBinding: SearchActivityBinding

    private var searchAdapter: SearchAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProvider(this).get(SearchViewModel::class.java)
        viewModel.repository = (applicationContext as ShoppingApplication).repository
        lifecycle.addObserver(viewModel)

        viewDataBinding = DataBindingUtil.setContentView(this, R.layout.search_activity)
        viewDataBinding.lifecycleOwner = this
        viewDataBinding.viewmodel = viewModel

        initView()
        initAdapter()
    }

    private fun initView() {

        search_cancel_button.setOnClickListener { finish() }

        search_start_search_button.setOnClickListener {
            setEditTextUnFocus()
            viewModel.searchRecyclerViewVisible.value = View.GONE
            viewModel.searchTextViewVisible.value = View.GONE
            startRequest()
        }

        viewModel.products.observe(this, EventObserver {
            (viewDataBinding.searchRecyclerView.adapter as SearchAdapter).submitList(it.toMutableList())
        })

        viewModel.queryActivityBundle.observe(this, EventObserver {
            val intent = Intent(this, QueryActivity::class.java)
            intent.putExtras(it)
            startActivity(intent)
        })

        search_edit_text.setOnClickListener { setEditTextFocus() }

        setEditTextFocus()
    }

    private fun initAdapter() {

        val viewModel = viewDataBinding.viewmodel

        if (viewModel != null) {
            searchAdapter = SearchAdapter(viewModel)
            viewDataBinding.searchRecyclerView.adapter = searchAdapter
            (viewDataBinding.searchRecyclerView.adapter as SearchAdapter).submitList(null)
        }
    }

    private fun startRequest() {

        if (search_edit_text.text.isNullOrEmpty()) {
            Toast.makeText(this, "請輸入關鍵字",Toast.LENGTH_SHORT).show()
            return
        }

        viewModel.sendSearchRequest(search_edit_text.text.toString())
    }

    private fun setEditTextFocus() {
        search_edit_text.isFocusableInTouchMode = true
        val methodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        methodManager.showSoftInput(search_edit_text, InputMethodManager.SHOW_IMPLICIT)
    }

    private fun setEditTextUnFocus() {
        val methodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        methodManager.hideSoftInputFromWindow(search_edit_text.windowToken, 0)
    }
}