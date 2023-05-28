package com.example.story.presentation.auth.login

import androidx.lifecycle.ViewModel
import com.example.story.data.repository.StoryRepository

class LoginViewModel(private val storyRepository: StoryRepository): ViewModel() {
    fun postLogin(email: String, password: String) = storyRepository.login(email, password)
}