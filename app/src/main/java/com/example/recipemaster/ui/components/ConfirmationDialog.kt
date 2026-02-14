package com.example.recipemaster.ui.components

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector

/**
 * Confirmation dialog component
 *
 * @param icon Optional icon
 * @param title Dialog title
 * @param message Dialog message
 * @param confirmText Confirm button text
 * @param dismissText Dismiss button text
 * @param onConfirm Confirm callback
 * @param onDismiss Dismiss callback
 * @author Heavenlight Mhally
 */
@Composable
fun ConfirmationDialog(
    icon: ImageVector? = null,
    title: String,
    message: String,
    confirmText: String = "Confirm",
    dismissText: String = "Cancel",
    onConfirm: () -> Unit,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        icon = icon?.let { { Icon(it, contentDescription = null) } },
        title = { Text(title) },
        text = { Text(message) },
        confirmButton = {
            TextButton(onClick = onConfirm) {
                Text(confirmText)
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text(dismissText)
            }
        }
    )
}
