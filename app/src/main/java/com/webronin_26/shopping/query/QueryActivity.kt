package com.webronin_26.shopping.query

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.app.NavUtils
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.webronin_26.shopping.*
import com.webronin_26.shopping.data.source.TokenManager
import com.webronin_26.shopping.databinding.QueryActivityBinding
import com.webronin_26.shopping.login.LoginActivity
import kotlinx.android.synthetic.main.query_activity.*

class QueryActivity : AppCompatActivity() {

    private lateinit var viewModel: QueryViewModel

    private lateinit var viewDataBinding: QueryActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProvider(this).get(QueryViewModel::class.java)
        viewModel.repository = (applicationContext as ShoppingApplication).repository
        viewModel.setData(intent)
        lifecycle.addObserver(viewModel)

        viewDataBinding = DataBindingUtil.setContentView(this, R.layout.query_activity)
        viewDataBinding.lifecycleOwner = this
        viewDataBinding.viewmodel = viewModel

        initView()
    }

    private fun initView() {

        viewModel.viewModelStatus.observe(this, EventObserver {
            when(it) {
                VIEWMODEL_INTERNET_SUCCESS -> Toast.makeText(this, "購買成功" , Toast.LENGTH_SHORT).show()
                VIEWMODEL_INTERNET_ERROR -> Toast.makeText(this, "網路錯誤" , Toast.LENGTH_SHORT).show()
            }
        })

        query_send_buy_request_button.setOnClickListener {
            val token = TokenManager.getToken(this)
            if (token == "") {
                startActivity(Intent(this, LoginActivity::class.java))
                return@setOnClickListener
            }
            viewModel.buy(token!!)
        }

        setSupportActionBar(query_toolbar)
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onBackPressed() {
        NavUtils.navigateUpFromSameTask(this);
        super.onBackPressed()
    }
}