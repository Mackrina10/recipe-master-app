package com.example.recipemaster.ui.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.StarHalf
import androidx.compose.material.icons.outlined.StarOutline
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlin.math.ceil
import kotlin.math.floor

/**
 * Read-only rating stars component
 * Displays stars based on rating value (0-5)
 * Supports half stars for decimal ratings
 *
 * @param rating Rating value (0.0 to 5.0)
 * @param starSize Size of each star icon
 * @param starColor Color of filled stars
 * @author Heavenlight Mhally
 */
@Composable
fun RatingStars(
    rating: Float,
    modifier: Modifier = Modifier,
    starSize: Dp = 20.dp,
    starColor: Color = Color(0xFFFFB703)
) {
    Row(modifier = modifier) {
        repeat(5) { index ->
            val starValue = index + 1
            Icon(
                imageVector = when {
                    rating >= starValue -> Icons.Filled.Star
                    rating >= starValue - 0.5f -> Icons.Filled.StarHalf
                    else -> Icons.Outlined.StarOutline
                },
                contentDescription = "Star $starValue",
                modifier = Modifier.size(starSize),
                tint = if (rating >= starValue - 0.5f) starColor else MaterialTheme.colorScheme.outlineVariant
            )
        }
    }
}
