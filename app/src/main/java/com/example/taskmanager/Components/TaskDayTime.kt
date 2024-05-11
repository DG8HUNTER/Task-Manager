package com.example.taskmanager.Components

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.focus.onFocusEvent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.taskmanager.Classes.Mission
import com.example.taskmanager.ui.theme.customColor
import com.example.taskmanager.ui.theme.mediumGray
import com.example.taskmanager.ui.theme.onSurface
import com.example.test.Screens.mainActivityViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat

@OptIn(ExperimentalMaterial3Api::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable

fun TaskDayTime(focusManager:FocusManager,selectedTask:String){

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
    val scope = rememberCoroutineScope()


    Row(modifier= Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(15.dp)){
        OutlinedTextField(
            value = if(mainActivityViewModel.searchDay.value!=null) mainActivityViewModel.searchDay.value.toString() else "",
            onValueChange = {
                if(it.isNotEmpty()){
                    if (it.isNumeric() && it.toInt() in 1..31) mainActivityViewModel.setValue(it,"searchDay")
                } else {
                    mainActivityViewModel.setValue(null , "searchDay")
                }
            }
            ,
            label = { Text("DD") },
            modifier = Modifier.weight(1f),
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Next
            ),
            keyboardActions = KeyboardActions(
                onNext = {focusManager.moveFocus(FocusDirection.Right)}

            ),
            colors = color,

        )

        OutlinedTextField(
            value =  if(mainActivityViewModel.searchMonth.value!=null) mainActivityViewModel.searchMonth.value.toString() else "",
            onValueChange = {
                if(it.isNotEmpty()){
                    if (it.isNumeric() && it.toInt() in 1..12) mainActivityViewModel.setValue(it,"searchMonth")
                }else {
                    mainActivityViewModel.setValue(null , "searchMonth")
                }
            }
            ,
            label = { Text("MM") },
            modifier = Modifier.weight(1f),
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Next
            ),
            keyboardActions = KeyboardActions(
                onNext = {focusManager.moveFocus(FocusDirection.Right)}

            ),
            colors=color,

        )

        OutlinedTextField(
            value = if(mainActivityViewModel.searchYear.value!=null) mainActivityViewModel.searchYear.value.toString() else "",
            onValueChange = {

                if(it.isNotEmpty()){
                    if (it.isNumeric()) mainActivityViewModel.setValue(it,"searchYear") }
                else {
                    mainActivityViewModel.setValue(null,"searchYear")
                }
            }

            ,
            label = { Text("YYYY") },
            modifier = Modifier.weight(1f),
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onNext = {focusManager.clearFocus()}

            ),
            colors=color,
        )




            Button(onClick = {

                focusManager.clearFocus()

                val startDate = SimpleDateFormat("dd-MM-yyyy hh:mm:ss a").parse("${ mainActivityViewModel.searchDay.value}-${mainActivityViewModel.searchMonth.value}-${mainActivityViewModel.searchYear.value} 12:00:00 AM")
                val endDate =  SimpleDateFormat("dd-MM-yyyy hh:mm:ss a").parse("${ mainActivityViewModel.searchDay.value}-${mainActivityViewModel.searchMonth.value}-${mainActivityViewModel.searchYear.value} 11:59:59 PM")


                if (startDate != null) {
                    Log.d("start1",startDate.toString())
                }
                if (endDate != null) {
                    Log.d("start2",endDate.toString())
                }


scope.launch(Dispatchers.Default){
    val list :MutableList<Mission> = mutableListOf()
    if(mainActivityViewModel.tasks.value.size!=0){
        mainActivityViewModel.tasks.value.forEach { task->
            Log.d("start3",task.dateTime.toString())
            if(task.dateTime!! >=startDate &&  task.dateTime!! <=endDate ){

                if(selectedTask=="To Do"){
                    if(task.status=="pending"){
                        list.add(task)
                    }

                }else {
                    if(task.status!="pending"){
                        list.add(task)
                    }
                }


            }
        }
    }

    withContext(Dispatchers.Main){
        mainActivityViewModel.setValue(list,"searchTasks")
    }

}


            } , shape = RectangleShape ,
                modifier= Modifier
                    .weight(1f)
                    .clip(shape = RoundedCornerShape(7.dp))
                    .background(
                        color = if (mainActivityViewModel.searchDay.value != null && mainActivityViewModel.searchMonth.value != null && mainActivityViewModel.searchYear.value != null) customColor else Color.Gray,
                        shape = RoundedCornerShape(7.dp)
                    )
                , colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
                enabled = mainActivityViewModel.searchDay.value != null && mainActivityViewModel.searchMonth.value != null && mainActivityViewModel.searchYear.value != null
            ){

                Row(modifier=Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically , horizontalArrangement = Arrangement.Center){

                    Icon(imageVector = Icons.Filled.Search, contentDescription ="" , modifier=Modifier.size(25.dp) , tint = Color.White )

                }

            }


    }
}