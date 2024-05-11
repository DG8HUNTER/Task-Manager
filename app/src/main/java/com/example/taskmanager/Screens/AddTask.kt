package com.example.taskmanager.Screens

import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.taskmanager.Classes.Mission
import com.example.taskmanager.Classes.Priority
import com.example.taskmanager.Components.TaskDateTimeInputField
import com.example.taskmanager.Components.TaskPriorityInputField
import com.example.taskmanager.Components.TextFieldComponent
import com.example.taskmanager.ui.theme.customColor
import com.example.test.Screens.mainActivityViewModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat

@RequiresApi(Build.VERSION_CODES.O)
@Composable


fun AddTask(navController: NavController) {
    val focusManager = LocalFocusManager.current
    val context = LocalContext.current
    val userId = Firebase.auth.currentUser?.uid.toString()
    val scope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(15.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextFieldComponent(name = "title", focusManager = focusManager)

        TextFieldComponent(name = "description", focusManager = focusManager)

        TaskDateTimeInputField(focusManager=focusManager)

        TaskPriorityInputField(focusManager=focusManager)
          Spacer(modifier =Modifier.height(15.dp))
        Row(modifier=Modifier.fillMaxWidth() , verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween){

            Button(onClick = {
                mainActivityViewModel.setValue(null,"title")
                mainActivityViewModel.setValue(null,"description")
                mainActivityViewModel.setValue(null,"day")
                mainActivityViewModel.setValue(null,"month")
                mainActivityViewModel.setValue(null,"year")
                mainActivityViewModel.setValue(null,"hour")
                mainActivityViewModel.setValue(null,"minutes")
                mainActivityViewModel.setValue("AM","dayPeriod")
                mainActivityViewModel.setValue(Priority.Low.toString(),"priority")

            } , shape = RectangleShape , modifier = Modifier
                .width(120.dp)
                .clip(RoundedCornerShape(10.dp))
                .background(color = customColor, shape = RoundedCornerShape(10.dp)), colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent)){

                Text(text ="Reset" , fontSize = 18.sp , fontWeight = FontWeight.Bold , color=Color.White)

            }

            Button(onClick = {

                             if(mainActivityViewModel.title.value==null){
                                 mainActivityViewModel.setValue(true, "titleError")
                             }

                if(mainActivityViewModel.description.value==null){
                    mainActivityViewModel.setValue(true, "descriptionError")
                }

                if(mainActivityViewModel.day.value==null){
                    mainActivityViewModel.setValue(true, "dayError")
                }

                if(mainActivityViewModel.month.value==null){
                    mainActivityViewModel.setValue(true, "monthError")
                }

                if(mainActivityViewModel.year.value==null){
                    mainActivityViewModel.setValue(true, "yearError")
                }

                if(mainActivityViewModel.hour.value==null){
                    mainActivityViewModel.setValue(true, "hourError")
                }
                if(mainActivityViewModel.minutes.value==null){
                    mainActivityViewModel.setValue(true, "minutesError")
                }


                if(!mainActivityViewModel.titleError.value &&
                    !mainActivityViewModel.descriptionError.value &&
                    !mainActivityViewModel.dayError.value&&
                    !mainActivityViewModel.monthError.value&&
                    !mainActivityViewModel.yearError.value &&
                    !mainActivityViewModel.hourError.value &&
                    !mainActivityViewModel.minutesError.value
                    ){

                    val day :String = if(mainActivityViewModel.day.value!!.toString().toInt() <10){
                        "0${mainActivityViewModel.day.value}"
                    }else {
                        mainActivityViewModel.day.value.toString()

                    }

                    val month :String = if(mainActivityViewModel.month.value!!.toString().toInt() <10){
                        "0${mainActivityViewModel.month.value}"
                    }else {
                        mainActivityViewModel.month.value.toString()

                    }

                    val hour :String = if(mainActivityViewModel.hour.value!!.toString().toInt() <10){
                        "0${mainActivityViewModel.hour.value}"
                    }else {
                        mainActivityViewModel.hour.value.toString()

                    }

                    val minutes :String = if(mainActivityViewModel.minutes.value!!.toString().toInt() <10){
                        "0${mainActivityViewModel.minutes.value}"
                    }else {
                        mainActivityViewModel.minutes.value.toString()

                    }






                    val db = Firebase.firestore
                    val Task = Mission(
                        title = mainActivityViewModel.title.value!!,
                        description = mainActivityViewModel.description.value!!,
                        dateTime = SimpleDateFormat("dd-MM-yyyy hh:mm a").parse("${day}-${month}-${mainActivityViewModel.year.value!!}  ${hour}:$minutes ${mainActivityViewModel.dayPeriod.value}"), // Convert LocalDateTime to Firestore Timestamp
                        priority = mainActivityViewModel.priority.value,
                        userId =userId

                    )

                    scope.launch(Dispatchers.Default) {
                        db.collection("Tasks").add(Task).addOnSuccessListener { document ->
                            if (document != null) {
                                val taskId = document.id
                                db.collection("Tasks").document(taskId)
                                    .update(
                                            "taskId", taskId


                                    )
                            }
                        }

                            withContext(Dispatchers.Main){
                                mainActivityViewModel.setValue(null,"title")
                                mainActivityViewModel.setValue(null,"description")
                                mainActivityViewModel.setValue(null,"day")
                                mainActivityViewModel.setValue(null,"month")
                                mainActivityViewModel.setValue(null,"year")
                                mainActivityViewModel.setValue(null,"hour")
                                mainActivityViewModel.setValue(null,"minutes")
                                mainActivityViewModel.setValue("AM","dayPeriod")
                                mainActivityViewModel.setValue(Priority.Low.toString(),"priority")
                                Toast.makeText(context , "Task added successfully!",Toast.LENGTH_SHORT).show()
                                focusManager.clearFocus()
                            }
                        }







                } else {
                    Toast.makeText(context , "Please fill all the fields",Toast.LENGTH_SHORT).show()
                }







            },

                shape = RectangleShape , modifier = Modifier
                    .width(120.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(color = customColor, shape = RoundedCornerShape(10.dp)), colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent
                )){

                Text(text ="Add" , fontSize = 18.sp , fontWeight = FontWeight.Bold , color=Color.White)

            }

        }



    }

}