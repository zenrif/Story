package com.example.story.presentation.auth.register

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.story.R
import com.example.story.data.Result
import com.example.story.databinding.ActivityRegisterBinding
import com.example.story.presentation.auth.login.LoginActivity
import com.example.story.utils.ViewModelFactory
import com.example.story.widget.CustomAlertDialog

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    private lateinit var factory: ViewModelFactory
    private val registerViewModel: RegisterViewModel by viewModels { factory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupViewModel()
        loginButtonHandler()
        registerButtonHandler()
    }

    private fun setupViewModel() {
        factory = ViewModelFactory.getInstance(binding.root.context)
    }

    private fun loginButtonHandler() {
        binding.tvLogin.setOnClickListener {
            startActivity(
                Intent(
                    this, LoginActivity::class.java
                )
            )
        }
    }

    private fun loadingHandler(isLoading: Boolean) {
        if (isLoading) {
            binding.btRegister.setLoading(true)
        } else {
            binding.btRegister.setLoading(false)
        }
    }

    private fun errorHandler() {
        CustomAlertDialog(this, R.string.error_message, R.drawable.error).show()
    }

    private fun registerHandler() {
        CustomAlertDialog(this, R.string.register_success, R.drawable.success).show()
        binding.textName.editText.text?.clear()
        binding.textEmail.editText.text?.clear()
        binding.textPassword.editText.text?.clear()
    }

    private fun registerButtonHandler() {
        binding.btRegister.setOnClickListener {
            if (binding.textEmail.editText.text.toString().isEmpty() || binding.textPassword.editText.text.toString().isEmpty() || binding.textName.editText.text.toString().isEmpty() ) {
                Toast.makeText(this@RegisterActivity,
                    getString(R.string.please_fill_all_fields), Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if(!android.util.Patterns.EMAIL_ADDRESS.matcher(binding.textEmail.editText.text.toString()).matches()){
                Toast.makeText(this@RegisterActivity,
                    getString(R.string.please_enter_a_valid_email_address), Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if(binding.textPassword.editText.text.toString().length < 8){
                Toast.makeText(this@RegisterActivity,
                    getString(R.string.password_must_be_at_least_8_characters), Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val name = binding.textName.editText.text.toString()
            val email = binding.textEmail.editText.text.toString()
            val password = binding.textPassword.editText.text.toString()

            if (!TextUtils.isEmpty(email) && !TextUtils.isEmpty(password) && !TextUtils.isEmpty(name)) {
                handlingRegister(name, email, password)
            } else {
                CustomAlertDialog(this, R.string.error_validation, R.drawable.error_form).show()
            }
        }
    }

    private fun handlingRegister(name: String, email: String, password: String) {
        registerViewModel.postRegister(name, email, password).observe(this@RegisterActivity) { result ->
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
                        loadingHandler(false)
                        registerHandler()
                    }
                }
            }
        }
    }
}