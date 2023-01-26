package com.phalgundixit.githubtrending.widget

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import com.phalgundixit.githubtrending.util.FontType
import com.phalgundixit.githubtrending.util.getTypeface
import com.google.android.material.button.MaterialButton
import com.phalgundixit.githubtrending.R

class CustomButton @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : MaterialButton(context, attrs, defStyleAttr) {

    init {
        if (attrs != null) {
            @SuppressLint("CustomViewStyleable")
            val typedArray = context.obtainStyledAttributes(attrs, R.styleable.CustomFont)
            val textStyle = typedArray.getInt(R.styleable.CustomFont_textFont, 0)
            withCustomFont(context, FontType.from(textStyle))
            typedArray.recycle()
        }
    }

    private fun withCustomFont(context: Context, textFont: FontType) {
        typeface = when (textFont) {
            FontType.ROBOTO_LIGHT -> getTypeface(
                context.getString(R.string.roboto_light),
                R.font.roboto_light,
                context
            )
            FontType.ROBOTO_REGULAR -> getTypeface(
                context.getString(R.string.roboto_regular),
                R.font.roboto_regular,
                context
            )
            FontType.ROBOTO_MEDIUM -> getTypeface(
                context.getString(R.string.roboto_medium),
                R.font.roboto_medium,
                context
            )
            FontType.ROBOTO_BOLD -> getTypeface(
                context.getString(R.string.roboto_bold),
                R.font.roboto_bold,
                context
            )
        }
    }
}