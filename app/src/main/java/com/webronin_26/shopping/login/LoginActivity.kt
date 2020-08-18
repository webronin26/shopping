package com.webronin_26.shopping.login

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.webronin_26.shopping.R
import com.webronin_26.shopping.ShoppingApplication
import com.webronin_26.shopping.data.remote.Result
import com.webronin_26.shopping.data.source.ShoppingRepository
import com.webronin_26.shopping.data.source.TokenManager
import com.webronin_26.shopping.data.source.UserInformationManager
import kotlinx.android.synthetic.main.login_activity.*
import kotlinx.coroutines.launch

class LoginActivity : AppCompatActivity() {

    private var repository: ShoppingRepository? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_activity)

        repository = (applicationContext as ShoppingApplication).repository

        initView()
    }

    private fun initView() {
        login_login_button.setOnClickListener {
            if (login_email_edit_text.text.isNullOrEmpty() || login_password_edit_text.text.isNullOrEmpty()) {
                Toast.makeText(this, "請將資料輸入完整",Toast.LENGTH_LONG).show()
            } else {
                lifecycleScope.launch {
                    repository?.login(login_email_edit_text.text.toString(), login_password_edit_text.text.toString()).let { result ->
                        if (result is Result.Success) {
                            setLogin(
                                token = result.data.data.token,
                                id = result.data.data.id,
                                name = result.data.data.name)
                        } else {
                            setLoginError()
                        }
                    }
                }
            }
        }
    }

    private fun setLogin(token: String, id: Int, name: String) {
        TokenManager.setToken(this, token)
        UserInformationManager.saveUserID(this, id)
        UserInformationManager.saveUserName(this, name)

        finish()
    }

    private fun setLoginError() { Toast.makeText(this, "帳號密碼有誤" , Toast.LENGTH_LONG).show() }
}
