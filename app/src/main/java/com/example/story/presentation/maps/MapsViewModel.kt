package com.example.story.presentation.maps

import androidx.lifecycle.ViewModel
import com.example.story.data.repository.StoryRepository

class MapViewModel(private val storyRepository: StoryRepository) : ViewModel() {
    fun getStories() = storyRepository.stories()
}