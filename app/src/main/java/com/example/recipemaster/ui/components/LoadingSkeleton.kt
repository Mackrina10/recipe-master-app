package com.example.recipemaster.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

/**
 * Loading skeleton for recipe card
 *
 * @author Heavenlight Mhally
 */
@Composable
fun RecipeCardSkeleton(
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            ShimmerBar(height = 180.dp)

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                ShimmerBar(height = 20.dp, modifier = Modifier.fillMaxWidth(0.7f))
                ShimmerBar(height = 16.dp, modifier = Modifier.fillMaxWidth(0.4f))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    ShimmerBar(height = 16.dp, modifier = Modifier.width(60.dp))
                    ShimmerBar(height = 16.dp, modifier = Modifier.width(60.dp))
                    ShimmerBar(height = 16.dp, modifier = Modifier.width(60.dp))
                }
            }
        }
    }
}
