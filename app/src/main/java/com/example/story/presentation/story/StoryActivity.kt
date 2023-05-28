package com.example.story.presentation.story

import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.provider.Settings
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.story.data.source.local.UserPreference
import com.example.story.databinding.ActivityStoryBinding
import com.example.story.presentation.add_story.AddStoryActivity
import com.example.story.presentation.auth.login.LoginActivity
import com.example.story.presentation.maps.MapsActivity
import com.example.story.utils.ViewModelFactory

class StoryActivity : AppCompatActivity() {
    private var _binding: ActivityStoryBinding? = null
    private val binding get() = _binding!!
    private lateinit var mUserPreference: UserPreference
    private lateinit var storyAdapter: StoryAdapter
    private lateinit var factory: ViewModelFactory
    private val homeViewModel: StoryViewModel by viewModels { factory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityStoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mUserPreference = UserPreference(this)
        binding.tvName.text = mUserPreference.getUser().name

        setupViewModel()
        setupView(this)
        stories()
        addStoryHandler()
        languageHandler()
        logoutHandler()
        mapsHandler()
    }

    private fun setupViewModel() {
        factory = ViewModelFactory.getInstance(binding.root.context)
    }

    private fun setupView(context: Context) {
        val rvStory = binding.rvStory

        if (context.resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            rvStory.layoutManager = GridLayoutManager(context, 2)
        } else {
            rvStory.layoutManager = LinearLayoutManager(context)
        }

        storyAdapter = StoryAdapter()
        rvStory.adapter = storyAdapter
    }

    private fun stories() {
        binding.rvStory.adapter = storyAdapter.withLoadStateFooter(
            footer = LoadingAdapter {
                storyAdapter.retry()
            }
        )

        homeViewModel.stories.observe(this) {
            storyAdapter.submitData(lifecycle, it)
        }

    }

    private fun addStoryHandler() {
        binding.fabAddStory.setOnClickListener {
            val intent = Intent(binding.root.context, AddStoryActivity::class.java)
            startActivity(intent)
        }
    }

    private fun logoutHandler() {
        binding.ibLogout.setOnClickListener {
            mUserPreference.removeUser()
            val intent = Intent(binding.root.context, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun languageHandler() {
        binding.ibLanguage.setOnClickListener {
            startActivity(Intent(Settings.ACTION_LOCALE_SETTINGS))
        }
    }

    private fun mapsHandler() {
        binding.ibMaps.setOnClickListener {
            val intent = Intent(binding.root.context, MapsActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}