package com.webronin_26.shopping.information

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.webronin_26.shopping.*
import com.webronin_26.shopping.data.source.TokenManager
import com.webronin_26.shopping.data.source.UserInformationManager
import com.webronin_26.shopping.databinding.InformationFragmentBinding
import com.webronin_26.shopping.login.LoginActivity
import kotlinx.android.synthetic.main.information_fragment.*

class InformationFragment : Fragment() {

    private lateinit var viewModel: InformationViewModel

    private lateinit var viewDataBinding: InformationFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.information_fragment, container, false)

        viewModel = ViewModelProvider(this).get(InformationViewModel::class.java)
        viewModel.repository = (requireContext().applicationContext as ShoppingApplication).repository
        lifecycle.addObserver(viewModel)

        viewDataBinding = InformationFragmentBinding.bind(view).apply { viewmodel = viewModel }
        viewDataBinding.lifecycleOwner = this

        return view
    }

    override fun onResume() {
        super.onResume()
        initView()
    }

    private fun initView() {
        if (TokenManager.getToken(requireContext()).isNullOrEmpty()) {
            viewModel.statusNotLogin()
        } else {
            viewModel.statusAlreadyLogin(
                id = UserInformationManager.getUserID(requireContext()),
                name = UserInformationManager.getUserName(requireContext()))
        }

        information_login_button.setOnClickListener {
            startActivity(Intent(activity, LoginActivity::class.java))
        }

        information_logout_button.setOnClickListener {
            viewModel.logout(TokenManager.getToken(requireContext()))
        }

        viewModel.viewModelStatus.observe(this, EventObserver {
            when(it) {
                VIEWMODEL_INTERNET_SUCCESS -> {
                    viewModel.statusNotLogin()
                    TokenManager.setToken(requireContext(), "")
                    UserInformationManager.saveUserName(requireContext(), "")
                    UserInformationManager.saveUserID(requireContext(), 0)
                }
                VIEWMODEL_INTERNET_ERROR -> Toast.makeText(requireContext(), "網路錯誤，請稍等", Toast.LENGTH_LONG).show()
            }
        })
    }
}
