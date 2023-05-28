package com.example.story.presentation.auth.register

import androidx.lifecycle.ViewModel
import com.example.story.data.repository.StoryRepository

class RegisterViewModel(private val storyRepository: StoryRepository): ViewModel() {
    fun postRegister(name: String, email: String, password: String) = storyRepository.register(name, email, password)
}