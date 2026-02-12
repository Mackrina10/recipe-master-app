package com.example.recipemaster.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp

/**
 * Time and servings input component
 *
 * @param prepTime Prep time in minutes
 * @param cookTime Cook time in minutes
 * @param servings Number of servings
 * @param onPrepTimeChange Prep time change callback
 * @param onCookTimeChange Cook time change callback
 * @param onServingsChange Servings change callback
 * @author Heavenlight Mhally
 */
@Composable
fun TimeServingsInput(
    prepTime: Int,
    cookTime: Int,
    servings: Int,
    onPrepTimeChange: (Int) -> Unit,
    onCookTimeChange: (Int) -> Unit,
    onServingsChange: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            OutlinedTextField(
                value = if (prepTime == 0) "" else prepTime.toString(),
                onValueChange = { value ->
                    onPrepTimeChange(value.toIntOrNull() ?: 0)
                },
                label = { Text("Prep Time (min)") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.weight(1f)
            )

            OutlinedTextField(
                value = if (cookTime == 0) "" else cookTime.toString(),
                onValueChange = { value ->
                    onCookTimeChange(value.toIntOrNull() ?: 0)
                },
                label = { Text("Cook Time (min)") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.weight(1f)
            )
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Servings",
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.SemiBold
            )

            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    onClick = { if (servings > 1) onServingsChange(servings - 1) },
                    modifier = Modifier.size(40.dp)
                ) {
                    Icon(Icons.Filled.Remove, "Decrease servings")
                }

                Text(
                    text = servings.toString(),
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )

                IconButton(
                    onClick = { if (servings < 50) onServingsChange(servings + 1) },
                    modifier = Modifier.size(40.dp)
                ) {
                    Icon(Icons.Filled.Add, "Increase servings")
                }
            }
        }
    }
}
