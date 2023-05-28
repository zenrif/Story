package com.example.story.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.example.story.data.repository.StoryRepository
import com.example.story.data.source.local.UserPreference
import com.example.story.data.source.remote.ApiConfig

val Context.dataStore: DataStore<Preferences> by preferencesDataStore("stories")

object Injection {
    fun provideRepository(context: Context): StoryRepository {
        val preferences = UserPreference(context)
        val apiService = ApiConfig.getApiService()
        return StoryRepository.getInstance(preferences, apiService)
    }
}