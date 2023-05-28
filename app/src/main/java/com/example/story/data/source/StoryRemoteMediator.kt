package com.example.story.data.source

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.example.story.data.response.Story
import com.example.story.data.source.local.UserPreference
import com.example.story.data.source.remote.ApiServices

@OptIn(ExperimentalPagingApi::class)
class StoryRemoteMediator(
    private val pref: UserPreference,
    private val apiService: ApiServices
) : RemoteMediator<Int, Story>() {

    override suspend fun initialize(): InitializeAction {
        return InitializeAction.LAUNCH_INITIAL_REFRESH
    }

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, Story>
    ): MediatorResult {
        val page = INITIAL_PAGE_INDEX
        val token = pref.getUser().token.toString()

        try {
            val responseData = token.let { apiService.stories(
                "Bearer $it",
                page,
                state.config.pageSize,
                0
            ) }

            return if (responseData.isSuccessful) {
                val endOfPaginationReached = responseData.body()!!.listStory.isEmpty()
                MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
            } else {
                MediatorResult.Error(Exception("Failed load story"))
            }
        } catch (exception: Exception) {
            return MediatorResult.Error(exception)
        }
    }

    private companion object {
        const val INITIAL_PAGE_INDEX = 1
    }
}