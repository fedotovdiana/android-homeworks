package com.itis.fedotova.homeworks

import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.ViewPropertyAnimator
import androidx.core.animation.doOnEnd
import kotlinx.android.synthetic.main.activity_main.animatedView
import kotlinx.android.synthetic.main.activity_main.showHideButton

class MainActivity : AppCompatActivity() {

    companion object {
        private const val ANIMATION_INITIAL_VALUE = 0f
        private const val ANIMATION_DURATION = 600L
        private const val ANIMATION_ROTATION_DERGEES = 360f
    }

    private var isButtonUp: Boolean = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupViews()
    }

    private fun getButtonSlideUpAnimator() =
        ValueAnimator.ofFloat(ANIMATION_INITIAL_VALUE).apply {
            duration = ANIMATION_DURATION
            addUpdateListener {
                showHideButton.translationY = it.animatedValue as Float
            }
        }

    private fun getButtonSlideDownAnimator() =
        ObjectAnimator.ofFloat(showHideButton, View.TRANSLATION_Y, ANIMATION_ROTATION_DERGEES).apply {
            duration = ANIMATION_DURATION
            doOnEnd {
                getViewRightRotationAnimator().start()
            }
        }

    private fun getViewRightRotationAnimator(): ViewPropertyAnimator {
        return animatedView.animate().apply {
            duration = ANIMATION_DURATION
            rotationBy(ANIMATION_ROTATION_DERGEES)
        }
    }

    private fun getViewLeftRotationAnimator() =
        ObjectAnimator.ofFloat(animatedView, View.ROTATION, ANIMATION_ROTATION_DERGEES, ANIMATION_INITIAL_VALUE).apply {
            duration = ANIMATION_DURATION
            doOnEnd {
                getButtonSlideUpAnimator().start()
            }
        }

    private fun setupViews() {
        showHideButton.setOnClickListener { changeTextViewVisibility() }
    }

    private fun changeTextViewVisibility() {
        if (isButtonUp) {
            getButtonSlideDownAnimator().start()
        } else {
            getViewLeftRotationAnimator().start()
        }
        isButtonUp = !isButtonUp
    }
}
