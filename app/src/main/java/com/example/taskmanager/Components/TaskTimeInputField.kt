package com.example.taskmanager.Components

import android.graphics.drawable.Icon
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardActions

import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.focus.onFocusEvent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import com.example.taskmanager.ui.theme.customColor
import com.example.taskmanager.ui.theme.mediumGray
import com.example.taskmanager.ui.theme.onSurface
import com.example.test.Screens.mainActivityViewModel

@OptIn(ExperimentalMaterial3Api::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun TaskDateTimeInputField(focusManager:FocusManager) {

    var periodSelected by remember {
        mutableStateOf("AM")
    }

    var expanded by remember { mutableStateOf(false) }


val color = TextFieldDefaults.textFieldColors(
    focusedTextColor = onSurface,
    unfocusedIndicatorColor = mediumGray,
    focusedIndicatorColor = customColor,
    cursorColor = onSurface,
    focusedLabelColor = customColor,
    unfocusedLabelColor = mediumGray,
    containerColor = Color.Transparent,
    errorContainerColor = Color.Transparent
)

    Column(verticalArrangement = Arrangement.spacedBy(15.dp) , horizontalAlignment = Alignment.CenterHorizontally){
        Row(modifier=Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(15.dp)){
            OutlinedTextField(
                value = if(mainActivityViewModel.day.value!=null) mainActivityViewModel.day.value.toString() else "",
                onValueChange = {
                    if(it.isNotEmpty()){
                        if (it.isNumeric() && it.toInt() in 1..31) mainActivityViewModel.setValue(it,"day")
                    } else {
                        mainActivityViewModel.setValue(null , "day")
                    }
                    }
                   ,
                label = { Text("Day") },
                modifier = Modifier.weight(1f).onFocusEvent { focusState ->
                    if(focusState.isFocused)  {
                        mainActivityViewModel.setValue(false ,"dayError")
                    }
                },
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Next
                ),
                keyboardActions = KeyboardActions(
                    onNext = {focusManager.moveFocus(FocusDirection.Right)}

                ),
                colors = color,
                isError = mainActivityViewModel.dayError.value
            )

            OutlinedTextField(
                value =  if(mainActivityViewModel.month.value!=null) mainActivityViewModel.month.value.toString() else "",
                onValueChange = {
                    if(it.isNotEmpty()){
                        if (it.isNumeric() && it.toInt() in 1..12) mainActivityViewModel.setValue(it,"month")
                    }else {
                        mainActivityViewModel.setValue(null , "month")
                    }
                    }
                   ,
                label = { Text("Month") },
                modifier = Modifier.weight(1f).onFocusEvent { focusState ->
                    if(focusState.isFocused)  {
                        mainActivityViewModel.setValue(false ,"monthError")
                    }
                },
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Next
                ),
                keyboardActions = KeyboardActions(
                    onNext = {focusManager.moveFocus(FocusDirection.Right)}

                ),
                colors=color,
                isError = mainActivityViewModel.monthError.value
            )

            OutlinedTextField(
                value = if(mainActivityViewModel.year.value!=null) mainActivityViewModel.year.value.toString() else "",
                onValueChange = {

                    if(it.isNotEmpty()){
                        if (it.isNumeric()) mainActivityViewModel.setValue(it,"year") }
                    else {
                        mainActivityViewModel.setValue(null,"year")
                    }
                    }

                   ,
                label = { Text("Year") },
                modifier = Modifier.weight(1f).onFocusEvent { focusState ->
                    if(focusState.isFocused)  {
                        mainActivityViewModel.setValue(false ,"yearError")
                    }
                },
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Next
                ),
                keyboardActions = KeyboardActions(
                    onNext = {focusManager.moveFocus(FocusDirection.Next)}

                ),
                colors=color,
                isError = mainActivityViewModel.yearError.value
            )
        }

        Row (modifier=Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(15.dp)){
            OutlinedTextField(
                value = if(mainActivityViewModel.hour.value!=null) mainActivityViewModel.hour.value.toString() else "",
                onValueChange = {
                    if(it.isNotEmpty()){
                        if (it.isNumeric() && it.toInt() in 0..12) mainActivityViewModel.setValue(it,"hour") }
                    else {
                        mainActivityViewModel.setValue(null , "hour")
                    }
                    }
                 ,
                label = { Text("Hour") },
                modifier = Modifier.weight(1f).onFocusEvent { focusState ->
                      if(focusState.isFocused)  {
                          mainActivityViewModel.setValue(false ,"hourError")
                      }
                },
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Next
                ),
                keyboardActions = KeyboardActions(
                    onNext = {focusManager.moveFocus(FocusDirection.Right)}

                ),
                colors = color,
                isError = mainActivityViewModel.hourError.value,

            )

            OutlinedTextField(
                value =if(mainActivityViewModel.minutes.value!=null) mainActivityViewModel.minutes.value.toString() else "",
                onValueChange = {
                    if(it.isNotEmpty()){
                        if (it.isNumeric() && it.toInt() in 0..59){
                            mainActivityViewModel.setValue(it,"minutes")
                        }
                    }else {
                        mainActivityViewModel.setValue(null , "minutes")
                    }


                },
                label = { Text("Minutes") },
                modifier = Modifier.weight(1f).onFocusEvent { focusState ->
                    if(focusState.isFocused)  {
                        mainActivityViewModel.setValue(false ,"minutesError")
                    }
                },
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Next
                ),
                keyboardActions = KeyboardActions(
                    onNext = {focusManager.moveFocus(FocusDirection.Right)}

                ),
                colors=color,
                isError = mainActivityViewModel.minutesError.value
            )

            OutlinedTextField(
                value = mainActivityViewModel.dayPeriod.value,
                onValueChange = { },
                label = { Text("DayPeriod") },
                modifier = Modifier.weight(1f),
                singleLine = true,
                readOnly = true,

                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Next
                ),
                keyboardActions = KeyboardActions(
                    onNext = {focusManager.moveFocus(FocusDirection.Next)}

                ),colors=color,
                trailingIcon = {
                    IconButton(onClick = { expanded=!expanded }) {

                      Icon(imageVector = if(expanded) Icons.Filled.KeyboardArrowUp else Icons.Filled.KeyboardArrowDown, contentDescription ="" )


                    }
                    DropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false },
                        modifier = Modifier.weight(1f)
                    ) {

                        DropdownMenuItem(text = {Text("AM")}, onClick = {
                            mainActivityViewModel.setValue("AM","dayPeriod")
                            expanded=false})

                        DropdownMenuItem(text = {Text("PM")}, onClick = {
                            mainActivityViewModel.setValue("PM","dayPeriod")
                            expanded=false
                        })


                    }
                }
            )


        }
    }
}

fun String.isNumeric(): Boolean {
    return this.toIntOrNull() != null
}
