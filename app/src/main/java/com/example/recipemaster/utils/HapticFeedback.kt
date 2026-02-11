package com.example.recipemaster.utils

import android.view.HapticFeedbackConstants
import android.view.View
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalView

/**
 * Utility class for haptic feedback
 * Provides tactile feedback for user interactions
 *
 * @author Heavenlight Mhally
 */
object HapticFeedback {

    /**
     * Perform click haptic feedback
     * @param view View to perform feedback on
     */
    fun performClick(view: View) {
        view.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY)
    }

    /**
     * Perform long press haptic feedback
     * @param view View to perform feedback on
     */
    fun performLongPress(view: View) {
        view.performHapticFeedback(HapticFeedbackConstants.LONG_PRESS)
    }

    /**
     * Perform context click haptic feedback
     * @param view View to perform feedback on
     */
    fun performContextClick(view: View) {
        view.performHapticFeedback(HapticFeedbackConstants.CONTEXT_CLICK)
    }
}

/**
 * Composable function to get haptic feedback handler
 * @return View for haptic feedback
 */
@Composable
fun rememberHapticFeedback(): View {
    return LocalView.current
}
