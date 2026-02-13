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
 * Dynamic step builder for instructions
 *
 * @param instructions Current instructions
 * @param onInstructionsChange Change callback
 * @author Heavenlight Mhally
 */
@Composable
fun StepBuilder(
    instructions: String,
    onInstructionsChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val stepList = remember(instructions) {
        mutableStateListOf<String>().apply {
            if (instructions.isNotEmpty()) {
                addAll(instructions.split("\n").filter { it.isNotBlank() })
            }
            if (isEmpty()) add("")
        }
    }

    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        stepList.forEachIndexed { index, step ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.Top
            ) {
                OutlinedTextField(
                    value = step,
                    onValueChange = { newValue ->
                        stepList[index] = newValue
                        onInstructionsChange(stepList.filter { it.isNotBlank() }.joinToString("\n"))
                    },
                    label = { Text("Step ${index + 1}") },
                    modifier = Modifier.weight(1f),
                    minLines = 2,
                    maxLines = 4
                )

                if (stepList.size > 1) {
                    IconButton(
                        onClick = {
                            stepList.removeAt(index)
                            onInstructionsChange(stepList.filter { it.isNotBlank() }.joinToString("\n"))
                        },
                        modifier = Modifier.size(40.dp)
                    ) {
                        Icon(Icons.Filled.Close, "Remove step")
                    }
                }
            }
        }

        TextButton(
            onClick = {
                stepList.add("")
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Icon(Icons.Filled.Add, contentDescription = null)
            Text("Add Step")
        }
    }
}
