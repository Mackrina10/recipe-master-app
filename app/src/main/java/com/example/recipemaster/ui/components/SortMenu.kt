package com.example.recipemaster.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Sort
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.example.recipemaster.data.entity.SortOption

/**
 * Sort menu dropdown component
 *
 * @param currentSort Currently selected sort option
 * @param onSortSelected Sort selection callback
 * @author Heavenlight Mhally
 */
@Composable
fun SortMenu(
    currentSort: SortOption,
    onSortSelected: (SortOption) -> Unit,
    modifier: Modifier = Modifier
) {
    var expanded by remember { mutableStateOf(false) }

    IconButton(
        onClick = { expanded = true },
        modifier = modifier
    ) {
        Icon(
            imageVector = Icons.Filled.Sort,
            contentDescription = "Sort"
        )
    }

    DropdownMenu(
        expanded = expanded,
        onDismissRequest = { expanded = false }
    ) {
        SortOption.entries.forEach { sortOption ->
            DropdownMenuItem(
                text = { Text(sortOption.displayName) },
                onClick = {
                    onSortSelected(sortOption)
                    expanded = false
                },
                trailingIcon = {
                    if (currentSort == sortOption) {
                        Icon(
                            imageVector = Icons.Filled.Check,
                            contentDescription = "Selected"
                        )
                    }
                }
            )
        }
    }
}
