package com.example.taskmanager.Screens

import TaskToDo
import android.content.ContentValues
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.taskmanager.Classes.Mission
import com.example.taskmanager.Components.PastTask
import com.example.taskmanager.R
import com.example.taskmanager.ui.theme.customColor
import com.example.test.Screens.mainActivityViewModel
import com.google.firebase.Timestamp
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import me.saket.swipe.SwipeAction
import java.text.SimpleDateFormat
import java.util.Date

@RequiresApi(Build.VERSION_CODES.O)
@Composable

fun Task(navController: NavController, selectedTask:String) {

    val db = Firebase.firestore
    var userId = Firebase.auth.currentUser?.uid.toString()
    val context = LocalContext.current


    val scope = rememberCoroutineScope()

    var tasks :MutableList<Mission> by remember {
        mutableStateOf(mutableListOf())
    }


    LaunchedEffect(key1 =true , key2= mainActivityViewModel.tasks.value , key3 = selectedTask) {
        scope.launch(Dispatchers.Default){
            val start =SimpleDateFormat("dd-MM-yyyy hh:mm:ss a").parse("${ mainActivityViewModel.dayOfMonth.value}-${ mainActivityViewModel.monthOfYear.value}-${ mainActivityViewModel.yearOf.value} 12:00:00 AM")
            val end =SimpleDateFormat("dd-MM-yyyy hh:mm:ss a").parse("${ mainActivityViewModel.dayOfMonth.value}-${ mainActivityViewModel.monthOfYear.value}-${ mainActivityViewModel.yearOf.value} 11:59:59 PM")
            val list : MutableList<Mission> = mutableListOf()
            if(mainActivityViewModel.tasks.value.size!=0){

                if(selectedTask=="To Do"){
                    mainActivityViewModel.tasks.value.forEach { task ->
                        if(task.status=="pending"){
                            if(task.dateTime!! >= start && task.dateTime!! <=end){
                                list.add(task)
                            }

                        }
                    }
                }else {
                    mainActivityViewModel.tasks.value.forEach { task ->
                        if(task.status!="pending") {
                            if (task.dateTime!! >= start && task.dateTime!! <= end) {
                                list.add(task)
                            }
                        }
                    }
                }

            }
            withContext(Dispatchers.Main){
                tasks=list
                delay(1000)

            }
        }



    }


        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 20.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {


                if(tasks.size!=0){

                    LazyColumn(modifier= Modifier
                        .fillMaxSize()
                        , verticalArrangement = Arrangement.spacedBy(15.dp), contentPadding = PaddingValues(bottom=10.dp)
                    ){
                        tasks.forEach { task->
                            val delete = SwipeAction(
                                onSwipe = {


                                    db.collection("Tasks").document(task.taskId).update("status","canceled").addOnSuccessListener {
                                        Toast.makeText(context , "Task deleted successfully",Toast.LENGTH_SHORT ).show()
                                    }

                                },
                                icon = {
                                    Icon(
                                        painter = painterResource(id = R.drawable.delete),
                                        contentDescription = null,
                                        tint = Color.Black,
                                        modifier = Modifier.padding(start = 15.dp)
                                    )

                                },
                                background = Color.Red,

                                )
                            val complete = SwipeAction(
                                onSwipe = {


                                    db.collection("Tasks").document(task.taskId).update("status","completed").addOnSuccessListener {
                                        Toast.makeText(context , "Task deleted successfully",Toast.LENGTH_SHORT ).show()
                                    }

                                },
                                icon = {
                                    Icon(
                                        painter = painterResource(id = R.drawable.complete),
                                        contentDescription = null,
                                        tint = Color.Black,
                                        modifier = Modifier.padding(end = 15.dp)
                                    )

                                },
                                background = Color.Green,

                                )

                            item {
                                if(selectedTask=="To Do"){
                                    TaskToDo(navController =navController, mission =task , delete = delete,complete=complete)

                                }else {
                                    PastTask(navController =navController, mission =task)
                                }
                            }



                        }



                    }


                }else {



                    Text(text ="No tasks yet" , fontSize = 18.sp , fontWeight = FontWeight.Bold)
                }


        }




        }




