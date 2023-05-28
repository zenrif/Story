package com.example.story.presentation.story

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.app.ActivityOptionsCompat
import androidx.core.util.Pair
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.story.data.response.Story
import com.example.story.databinding.ItemRowStoryBinding
import com.example.story.presentation.detail_story.StoryDetailActivity
import com.example.story.presentation.detail_story.StoryDetailActivity.Companion.EXTRA_STORY_ID

class StoryAdapter : PagingDataAdapter<Story, StoryAdapter.ListViewHolder>(diffCallbackListener) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ListViewHolder {
        return ListViewHolder(
            ItemRowStoryBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val data = getItem(position)
        if (data != null) {
            holder.bind(item = data)
        }
    }

    class ListViewHolder(private val binding: ItemRowStoryBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Story) {
            with(binding) {
                tvItemName.text = item.name
                tvItemDesc.text = item.description
                Glide.with(itemView.context)
                    .load(item.photoUrl)
                    .fitCenter()
                    .into(ivItemImage)

                root.setOnClickListener {
                    val optionsCompat: ActivityOptionsCompat =
                        ActivityOptionsCompat.makeSceneTransitionAnimation(
                            root.context as Activity,
                            Pair(ivItemImage, "image"),
                            Pair(tvItemName, "name"),
                            Pair(tvItemDesc, "description"),
                        )

                    root.context.startActivity(
                        Intent(
                            root.context, StoryDetailActivity::class.java
                        ).putExtra(EXTRA_STORY_ID, item), optionsCompat.toBundle()
                    )
                }
            }
        }
    }


    companion object {
        val diffCallbackListener = object : DiffUtil.ItemCallback<Story>() {
            override fun areItemsTheSame(oldItem: Story, newItem: Story): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: Story, newItem: Story): Boolean {
                return oldItem.id == newItem.id
            }
        }
    }
}