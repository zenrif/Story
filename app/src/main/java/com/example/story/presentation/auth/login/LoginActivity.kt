package com.example.story.presentation.auth.login

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils.isEmpty
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.story.R
import com.example.story.data.LoginModel
import com.example.story.data.Result
import com.example.story.data.response.LoginResponse
import com.example.story.data.source.local.UserPreference
import com.example.story.databinding.ActivityLoginBinding
import com.example.story.presentation.auth.register.RegisterActivity
import com.example.story.presentation.story.StoryActivity
import com.example.story.utils.ViewModelFactory
import com.example.story.widget.CustomAlertDialog

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var factory: ViewModelFactory
    private val loginViewModel: LoginViewModel by viewModels { factory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupViewModel()
        registerButtonHandler()
        loginButtonHandler()
    }

    private fun setupViewModel() {
        factory = ViewModelFactory.getInstance(binding.root.context)
    }

    private fun registerButtonHandler() {
        binding.tvRegister.setOnClickListener {
            startActivity(
                Intent(
                    this, RegisterActivity::class.java
                )
            )
        }
    }

    private fun loginButtonHandler() {
        binding.btLogin.setOnClickListener {
            if (binding.textEmail.editText.text.toString().isEmpty() || binding.textPassword.editText.text.toString().isEmpty()) {
                Toast.makeText(this@LoginActivity,
                    getString(R.string.please_fill_all_fields), Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (!android.util.Patterns.EMAIL_ADDRESS.matcher(binding.textEmail.editText.text.toString()).matches()) {
                Toast.makeText(this@LoginActivity,
                    getString(R.string.please_enter_a_valid_email_address), Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if(binding.textPassword.editText.text.toString().length < 8){
                Toast.makeText(this@LoginActivity,
                    getString(R.string.password_must_be_at_least_8_characters), Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }


            val email = binding.textEmail.editText.text.toString()
            val password = binding.textPassword.editText.text.toString()

            if (!isEmpty(email) && !isEmpty(password)) {
                handlingLogin(email, password)
            } else {
                CustomAlertDialog(this, R.string.error_validation, R.drawable.error_form).show()
            }
        }
    }

    private fun handlingLogin(email: String, password: String) {
        loginViewModel.postLogin(email, password).observe(this@LoginActivity) { result ->
            if (result != null) {
                when(result) {
                    is Result.Loading -> {
                        loadingHandler(true)
                    }
                    is Result.Error -> {
                        loadingHandler(false)
                        errorHandler()
                    }
                    is Result.Success -> {
                        successLoginHandler(result.data)
                    }
                }
            }
        }
    }

    private fun loadingHandler(isLoading: Boolean) {
        if (isLoading) {
            binding.btLogin.setLoading(true)
        } else {
            binding.btLogin.setLoading(false)
        }
    }

    private fun successLoginHandler(loginResponse: LoginResponse) {
        saveLoginData(loginResponse)
        navigateToHome()
    }

    private fun errorHandler() {
        CustomAlertDialog(this, R.string.error_message, R.drawable.error).show()
    }

    private fun saveLoginData(loginResponse: LoginResponse) {
        val userPreference = UserPreference(this)
        val loginResult = loginResponse.loginResult
        val loginModel = LoginModel(
            name = loginResult?.name, userId = loginResult?.userId, token = loginResult?.token
        )

        userPreference.setLogin(loginModel)
    }

    private fun navigateToHome() {
        val intent = Intent(this@LoginActivity, StoryActivity::class.java)
        startActivity(intent)
        finish()
    }
}