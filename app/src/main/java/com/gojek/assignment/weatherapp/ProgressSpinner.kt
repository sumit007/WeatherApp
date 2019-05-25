package com.gojek.assignment.weatherapp

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.ImageView
import android.view.animation.Animation
import android.view.animation.RotateAnimation





/**
 * Created by Sumit on 2019-05-25.
 *
 */


class ProgressSpinner : ImageView {

    companion object {
        const val ROTATE_ANIMATION_DURATION = 1600L
        const val IMAGE_RESOURCE_ID = R.mipmap.ic_loading
    }

    constructor(context: Context) : super(context, null)

    constructor(context: Context, attributes: AttributeSet) : super(context, attributes)

    constructor(context: Context, attributes: AttributeSet, defStyleAttr: Int) : super (context, attributes, defStyleAttr)

    override fun onFinishInflate() {
        super.onFinishInflate()

        setImageResource(IMAGE_RESOURCE_ID)
        startAnimation()
    }

    private fun startAnimation() {
        clearAnimation()

        val rotate = RotateAnimation(
            0f, 360f,
            Animation.RELATIVE_TO_SELF, 0.5f,
            Animation.RELATIVE_TO_SELF, 0.5f
        )
        rotate.duration = ROTATE_ANIMATION_DURATION
        rotate.repeatCount = Animation.INFINITE
        startAnimation(rotate)
    }

    override fun setVisibility(visibility: Int) {
        super.setVisibility(visibility)

        if (visibility == View.VISIBLE) {
            startAnimation()

        } else {
            clearAnimation()

        }
    }


}