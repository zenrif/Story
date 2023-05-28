package com.example.story.presentation.add_story

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.example.story.data.Result
import com.example.story.data.repository.StoryRepository
import com.example.story.data.response.GeneralResponse
import com.example.story.utils.DataDummy
import com.example.story.utils.getOrAwaitValue
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner
import java.io.File

@RunWith(MockitoJUnitRunner::class)
class AddStoryViewModelTest {
    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var storyRepository: StoryRepository
    private lateinit var addStoryViewModel: AddStoryViewModel
    private var dummyResponse = DataDummy.generateDummyAddStorySuccess()
    private var dummyDesc = "description".toRequestBody("text/plain".toMediaType())
    private var dummyLat = 0.01
    private var dummyLon = 0.01

    private val file: File = mock(File::class.java)
    private val requestImageFile = file.asRequestBody("image/jpg".toMediaTypeOrNull())
    private val imageMultipart: MultipartBody.Part = MultipartBody.Part.createFormData(
        "photo",
        file.name,
        requestImageFile
    )

    @Before
    fun setUp() {
        addStoryViewModel = AddStoryViewModel(storyRepository)
    }

    @Test
    fun `when Post Create Story Should Not Null and Return Success`() {
        val expectedAddStory = MutableLiveData<Result<GeneralResponse>>()
        expectedAddStory.value = Result.Success(dummyResponse)
        `when`(
            storyRepository.addStory(
                imageFile = imageMultipart,
                desc = dummyDesc,
                lat = dummyLat,
                lon = dummyLon
            )
        ).thenReturn(expectedAddStory)

        val actualResponse = addStoryViewModel.postAddStory(
            imageFile = imageMultipart,
            desc = dummyDesc,
            lat = dummyLat,
            lon = dummyLon
        ).getOrAwaitValue()

        Mockito.verify(storyRepository).addStory(
            imageFile = imageMultipart,
            desc = dummyDesc,
            lat = dummyLat,
            lon = dummyLon
        )
        Assert.assertNotNull(actualResponse)
        Assert.assertTrue(actualResponse is Result.Success)
    }

    @Test
    fun `when Post Create Story Should Null and Return Error`() {
        dummyResponse = DataDummy.generateDummyAddStoryError()

        val expectedAddStory = MutableLiveData<Result<GeneralResponse>>()
        expectedAddStory.value = Result.Error("error")
        `when`(
            storyRepository.addStory(
                imageFile = imageMultipart,
                desc = dummyDesc,
                lat = dummyLat,
                lon = dummyLon
            )
        ).thenReturn(expectedAddStory)

        val actualResponse = addStoryViewModel.postAddStory(
            imageFile = imageMultipart,
            desc = dummyDesc,
            lat = dummyLat,
            lon = dummyLon
        ).getOrAwaitValue()

        Mockito.verify(storyRepository).addStory(
            imageFile = imageMultipart,
            desc = dummyDesc,
            lat = dummyLat,
            lon = dummyLon
        )
        Assert.assertNotNull(actualResponse)
        Assert.assertTrue(actualResponse is Result.Error)
    }
}