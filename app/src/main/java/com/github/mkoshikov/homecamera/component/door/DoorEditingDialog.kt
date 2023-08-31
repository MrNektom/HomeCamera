package com.github.mkoshikov.homecamera.component.door

import androidx.compose.foundation.layout.padding
import androidx.compose.material.AlertDialog
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.github.mkoshikov.homecamera.R
import com.github.mkoshikov.homecamera.repository.`object`.Door

@Composable
fun DoorEditingDialog(
    door: Door,
    onDismissRequest: () -> Unit,
    onUpdateName: (Door, String) -> Unit
) {
    var name by remember {
        mutableStateOf(door.name)
    }

    AlertDialog(
        onDismissRequest = onDismissRequest,
        title = {
            Text(
                text = stringResource(id = R.string.door_editing_title),
                fontSize = 20.sp,
                modifier = Modifier
                    .padding(bottom = 8.dp)
                )
        },
        text = {
            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                label = {
                    Text(text = stringResource(id = R.string.name))
                }
            )
        },
        confirmButton = {
            TextButton(
                onClick = {
                    onUpdateName(door, name)
                    onDismissRequest()
                },
            ) {
                Text(text = stringResource(id = android.R.string.ok))
            }
        },
        dismissButton = {
            TextButton(onClick = onDismissRequest) {
                Text(text = stringResource(id = android.R.string.cancel))
            }
        }
    )
}