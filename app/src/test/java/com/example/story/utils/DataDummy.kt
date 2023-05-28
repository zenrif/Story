package com.example.story.utils

import com.example.story.data.response.GeneralResponse
import com.example.story.data.response.LoginResponse
import com.example.story.data.response.LoginResultResponse
import com.example.story.data.response.RegisterResponse
import com.example.story.data.response.Story
import com.example.story.data.response.StoryResponse

object DataDummy {
    fun generateDummyLoginSuccess(): LoginResponse {
        return LoginResponse(
            error = false,
            message = "success",
            loginResult = LoginResultResponse(
                userId = "userId",
                name = "name",
                token = "token"
            )
        )
    }

    fun generateDummyLoginError(): LoginResponse {
        return LoginResponse(
            error = true,
            message = "invalid password"
        )
    }

    fun generateDummyRegisterSuccess(): RegisterResponse {
        return RegisterResponse(
            error = false,
            message = "success"
        )
    }

    fun generateDummyRegisterError(): RegisterResponse {
        return RegisterResponse(
            error = true,
            message = "bad request"
        )
    }

    fun generateDummyAddStorySuccess(): GeneralResponse {
        return GeneralResponse(
            error = false,
            message = "success"
        )
    }

    fun generateDummyAddStoryError(): GeneralResponse {
        return GeneralResponse(
            error = true,
            message = "error"
        )
    }

    fun generateDummyStory(): StoryResponse {
        return StoryResponse(
            error = false,
            message = "success",
            listStory = arrayListOf(
                Story(
                    id = "id",
                    name = "name",
                    description = "description",
                    photoUrl = "photoUrl",
                    createdAt = "createdAt",
                    lat = 0.01,
                    lon = 0.01
                )
            )
        )
    }

    fun generateErrorDummyStory(): StoryResponse {
        return StoryResponse(
            error = true,
            message = "error",
            listStory = arrayListOf(
                Story(
                    id = "id",
                    name = "name",
                    description = "description",
                    photoUrl = "photoUrl",
                    createdAt = "createdAt",
                    lat = 0.01,
                    lon = 0.01
                )
            )
        )
    }
}