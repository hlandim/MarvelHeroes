package com.hlandim.marvelheroes.ui

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.window.DialogProperties
import com.hlandim.marvelheroes.core.ui.R

@Composable
fun ErrorDialog(
    message: String,
    onDismissRequest: () -> Unit,
    onConfirmClicked: () -> Unit,
    modifier: Modifier = Modifier,
    properties: DialogProperties = DialogProperties(),
) {
    AlertDialog(
        title = {
            Text(text = stringResource(R.string.error_dialog_title))
        },
        onDismissRequest = onDismissRequest,
        modifier = modifier,
        properties = properties,
        confirmButton = {
            Button(onClick = onConfirmClicked) {
                Text(text = stringResource(R.string.error_dialog_confirm_btn))
            }
        },
        text = {
            Text(text = message)
        },
    )
}
