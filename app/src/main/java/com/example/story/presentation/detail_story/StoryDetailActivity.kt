package com.example.story.presentation.detail_story

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.story.data.response.Story
import com.example.story.databinding.ActivityStoryDetailBinding

class StoryDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityStoryDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStoryDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        @Suppress("DEPRECATION")
        val detailStory = intent.getParcelableExtra<Story>(EXTRA_STORY_ID) as Story

        setupUi(detailStory)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        finish()
        return true
    }

    private fun setupUi(detailStory: Story) {
        Glide.with(this@StoryDetailActivity)
            .load(detailStory.photoUrl)
            .fitCenter()
            .into(binding.ivDetailImage)

        detailStory.apply {
            binding.tvDetailName.text = name
            binding.tvDetailDescription.text = description
        }
    }

    companion object {
        const val EXTRA_STORY_ID = "STORY_ID"
    }
}