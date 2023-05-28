package com.example.story.widget

import android.content.Context
import android.text.InputType
import android.util.AttributeSet
import android.view.ViewGroup
import com.example.story.R
import com.google.android.material.textfield.TextInputLayout

class TextInputEditText : TextInputLayout, TextInputEditTextContract.View,
    EditTextContract.Listener {

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        context.obtainStyledAttributes(attrs, R.styleable.SoadTextInputEditText).apply {
            try {
                max = getInteger(R.styleable.SoadTextInputEditText_max, 0)
                min = getInteger(R.styleable.SoadTextInputEditText_min, 0)
                isRequired = getBoolean(R.styleable.SoadTextInputEditText_isRequired, false)
                type = getInteger(R.styleable.SoadTextInputEditText_type, 1)
            } catch (e: Exception) {
                e.printStackTrace()
                recycle()
            }
        }

        init()
    }

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(
        context,
        attrs,
        defStyle
    ) {
        context.obtainStyledAttributes(attrs, R.styleable.SoadTextInputEditText).apply {
            try {
                max = getInteger(R.styleable.SoadTextInputEditText_max, 0)
                min = getInteger(R.styleable.SoadTextInputEditText_min, 0)
                isRequired = getBoolean(R.styleable.SoadTextInputEditText_isRequired, false)
                type = getInteger(R.styleable.SoadTextInputEditText_type, 1)
            } catch (e: Exception) {
                e.printStackTrace()
                recycle()
            }
        }
        init()
    }

    override var max: Int = 0
    override var min: Int = 0
    override var isValid: Boolean = false
    get() {
        field = if (this::editText.isInitialized) {
            editText.isValid
        } else {
            false
        }
        return field
    }

    override var isRequired: Boolean = false
    override var type: Int = 1

    override lateinit var editText: EditText

    override fun init() {
        setWillNotDraw(false)
        editText = EditText(context).apply {
            label = this@TextInputEditText.hint.toString()
            isRequired = this@TextInputEditText.isRequired
            max = this@TextInputEditText.max
            min = this@TextInputEditText.min
            listener = this@TextInputEditText
            inputType = InputType.TYPE_CLASS_TEXT or this@TextInputEditText.type
            type = this@TextInputEditText.type
        }

        val layoutParam =
            LayoutParams(LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        editText.layoutParams = layoutParam
        addView(editText)
    }

    override fun onTextInvalid(message: String) {
        error = message
        isValid = false
    }

    override fun onTextValid() {
        error = null
        isValid = true
    }
}