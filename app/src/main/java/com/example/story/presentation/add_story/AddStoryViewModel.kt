package com.example.story.presentation.add_story

import androidx.lifecycle.ViewModel
import com.example.story.data.repository.StoryRepository
import okhttp3.MultipartBody
import okhttp3.RequestBody

class AddStoryViewModel(private val storyRepository: StoryRepository): ViewModel() {
    fun postAddStory(imageFile: MultipartBody.Part, desc: RequestBody, lat: Double, lon: Double) = storyRepository.addStory(imageFile, desc, lat, lon)
}