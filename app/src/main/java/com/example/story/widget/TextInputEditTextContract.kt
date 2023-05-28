package com.example.story.widget

class TextInputEditTextContract {
    interface View {
        var max: Int
        var min: Int
        var isValid: Boolean
        var isRequired: Boolean
        var type: Int
        var editText: EditText

        fun init()
    }
}