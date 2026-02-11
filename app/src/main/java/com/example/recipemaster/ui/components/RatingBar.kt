package com.example.recipemaster.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.StarOutline
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * Interactive rating bar component
 * Allows users to tap stars to set rating (0-5)
 *
 * @param rating Current rating value (0.0 to 5.0)
 * @param onRatingChanged Callback when rating changes
 * @param starSize Size of each star icon
 * @param starColor Color of filled stars
 * @author Heavenlight Mhally
 */
@Composable
fun RatingBar(
    rating: Float,
    onRatingChanged: (Float) -> Unit,
    modifier: Modifier = Modifier,
    starSize: Dp = 32.dp,
    starColor: Color = Color(0xFFFFB703)
) {
    Row(modifier = modifier) {
        repeat(5) { index ->
            val starValue = (index + 1).toFloat()
            Icon(
                imageVector = if (rating >= starValue) Icons.Filled.Star else Icons.Outlined.StarOutline,
                contentDescription = "Star $starValue",
                modifier = Modifier
                    .size(starSize)
                    .clickable { onRatingChanged(starValue) },
                tint = if (rating >= starValue) starColor else Color.Gray
            )
        }
    }
}
