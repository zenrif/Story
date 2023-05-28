package com.example.story.presentation.story

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.story.data.repository.StoryRepository
import com.example.story.data.response.Story

class StoryViewModel(repo: StoryRepository): ViewModel() {
    val stories: LiveData<PagingData<Story>> =
        repo.getListStories().cachedIn(viewModelScope)
}