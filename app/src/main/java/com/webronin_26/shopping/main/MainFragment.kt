package com.webronin_26.shopping.main

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.webronin_26.shopping.R
import com.webronin_26.shopping.ShoppingApplication
import com.webronin_26.shopping.databinding.MainFragmentBinding
import com.webronin_26.shopping.search.SearchActivity
import kotlinx.android.synthetic.main.main_fragment.*


class MainFragment : Fragment() {

    private lateinit var viewModel: MainViewModel

    private lateinit var viewDataBinding: MainFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.main_fragment, container, false)

        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        viewModel.repository = (requireContext().applicationContext as ShoppingApplication).repository
        lifecycle.addObserver(viewModel)

        viewDataBinding = MainFragmentBinding.bind(view).apply { viewmodel = viewModel }
        viewDataBinding.lifecycleOwner = this

        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initView()
    }

    private fun initView() {

        main_swipe_refresh_layout.setOnRefreshListener {
            viewModel.refreshMainPage()
            main_swipe_refresh_layout.isRefreshing = false
        }

        main_notification_button.setOnClickListener {
            val intent = Intent()
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                intent.action = Settings.ACTION_APP_NOTIFICATION_SETTINGS
                intent.putExtra(Settings.EXTRA_APP_PACKAGE, requireContext().packageName)
            } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                intent.action = "android.settings.APP_NOTIFICATION_SETTINGS"
                intent.putExtra("app_package", requireContext().packageName)
                intent.putExtra("app_uid", requireContext().applicationInfo.uid)
            } else {
                intent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
                intent.addCategory(Intent.CATEGORY_DEFAULT)
                intent.data = Uri.parse("package:" + requireContext().packageName)
            }
            startActivity(intent)
        }

        main_tool_bar_search_linear_layout.setOnClickListener {
            startActivity(Intent(activity, SearchActivity::class.java))
        }
    }
}
