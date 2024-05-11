package com.example.taskmanager.Components

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp


import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.text.input.KeyboardType
import com.example.taskmanager.Classes.Priority
import com.example.taskmanager.ui.theme.customColor
import com.example.taskmanager.ui.theme.mediumGray
import com.example.taskmanager.ui.theme.onSurface
import com.example.test.Screens.mainActivityViewModel


@OptIn(ExperimentalMaterial3Api::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun TaskPriorityInputField(focusManager: FocusManager) {

    var expanded by remember { mutableStateOf(false) }


        Row {
            OutlinedTextField(
                value = mainActivityViewModel.priority.value,
                onValueChange = {},
                label = { Text("Priority") },
                modifier = Modifier.weight(1f),
                singleLine = true,
                readOnly = true,
                trailingIcon = {
                    IconButton(onClick = {expanded=!expanded }) {
                        Icon(imageVector =if(expanded) Icons.Filled.KeyboardArrowUp else Icons.Filled.KeyboardArrowDown, contentDescription ="" )

                    }


                    DropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false },
                        modifier = Modifier.weight(1f)

                    ) {
                        Priority.entries.forEach { value->
                            DropdownMenuItem(text = { Text(text =value.toString())}, onClick = {
                                mainActivityViewModel.setValue(value.toString(),"priority")
                                expanded=false
                                focusManager.clearFocus()
                            })
                        }

                    }

                },colors = TextFieldDefaults.textFieldColors(
                        focusedTextColor = onSurface,
                unfocusedIndicatorColor = mediumGray,
                focusedIndicatorColor = customColor,
                cursorColor = onSurface,
                focusedLabelColor = customColor,
                unfocusedLabelColor = mediumGray,
                containerColor = Color.Transparent,
                errorContainerColor = Color.Transparent
            ),

            )
        }

}
