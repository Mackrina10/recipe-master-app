package com.example.recipemaster.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

/**
 * Dynamic ingredient input list
 *
 * @param ingredients Current ingredients
 * @param onIngredientsChange Change callback
 * @author Heavenlight Mhally
 */
@Composable
fun IngredientInput(
    ingredients: String,
    onIngredientsChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val ingredientList = remember(ingredients) {
        mutableStateListOf<String>().apply {
            if (ingredients.isNotEmpty()) {
                addAll(ingredients.split("\n").filter { it.isNotBlank() })
            }
            if (isEmpty()) add("")
        }
    }

    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        ingredientList.forEachIndexed { index, ingredient ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                OutlinedTextField(
                    value = ingredient,
                    onValueChange = { newValue ->
                        ingredientList[index] = newValue
                        onIngredientsChange(ingredientList.filter { it.isNotBlank() }.joinToString("\n"))
                    },
                    label = { Text("Ingredient ${index + 1}") },
                    modifier = Modifier.weight(1f)
                )

                if (ingredientList.size > 1) {
                    IconButton(
                        onClick = {
                            ingredientList.removeAt(index)
                            onIngredientsChange(ingredientList.filter { it.isNotBlank() }.joinToString("\n"))
                        },
                        modifier = Modifier.size(40.dp)
                    ) {
                        Icon(Icons.Filled.Close, "Remove ingredient")
                    }
                }
            }
        }

        TextButton(
            onClick = {
                ingredientList.add("")
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Icon(Icons.Filled.Add, contentDescription = null)
            Text("Add Ingredient")
        }
    }
}
