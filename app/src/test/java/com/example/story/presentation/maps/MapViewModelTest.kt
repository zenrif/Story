package com.example.story.presentation.maps

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.example.story.data.Result
import com.example.story.data.repository.StoryRepository
import com.example.story.data.response.StoryResponse
import com.example.story.utils.DataDummy
import com.example.story.utils.getOrAwaitValue
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class MapViewModelTest{
    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var storyRepository: StoryRepository
    private lateinit var mapViewModel: MapViewModel
    private var dummyStory = DataDummy.generateDummyStory()

    @Before
    fun setUp() {
        mapViewModel = MapViewModel(storyRepository)
    }

    @Test
    fun `when Get Story Should Not Null and Return Success`() {
        val expectedResponse = MutableLiveData<Result<StoryResponse>>()
        expectedResponse.value = Result.Success(dummyStory)
        `when`(storyRepository.stories()).thenReturn(expectedResponse)

        val actualResponse = mapViewModel.getStories().getOrAwaitValue()

        Mockito.verify(storyRepository).stories()
        Assert.assertNotNull(actualResponse)
        Assert.assertTrue(actualResponse is Result.Success)
    }

    @Test
    fun `when Get Story Should Null and Return Error`() {
        dummyStory = DataDummy.generateErrorDummyStory()

        val expectedResponse = MutableLiveData<Result<StoryResponse>>()
        expectedResponse.value = Result.Error("error")
        `when`(storyRepository.stories()).thenReturn(expectedResponse)

        val actualResponse = mapViewModel.getStories().getOrAwaitValue()

        Mockito.verify(storyRepository).stories()
        Assert.assertNotNull(actualResponse)
        Assert.assertTrue(actualResponse is Result.Error)
    }
}