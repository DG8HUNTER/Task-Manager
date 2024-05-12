package com.example.taskmanager.Screens

import TaskToDo
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.taskmanager.Components.PastTask
import com.example.taskmanager.R
import com.example.test.Screens.mainActivityViewModel
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import me.saket.swipe.SwipeAction
import me.saket.swipe.SwipeableActionsBox

@Composable
fun SearchTask(navController: NavController , selectedTask:String){
val db = Firebase.firestore
    val context = LocalContext.current


    Column(modifier= Modifier
        .fillMaxSize()
        .padding(start = 20.dp , end=20.dp ), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally){

        if(mainActivityViewModel.searchTasks.value.size!=0){

            LazyColumn(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.spacedBy(15.dp), contentPadding = PaddingValues(bottom=10.dp)){
                mainActivityViewModel.searchTasks.value.forEach { task ->

                    val delete = SwipeAction(
                        onSwipe = {


                            db.collection("Tasks").document(task.taskId).update("status","canceled").addOnSuccessListener {
                                Toast.makeText(context , "Task deleted successfully", Toast.LENGTH_SHORT ).show()
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
                                Toast.makeText(context , "Task deleted successfully", Toast.LENGTH_SHORT ).show()
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


                    if(selectedTask=="To Do"){
                        item {
                            TaskToDo(navController =navController, mission =task , delete = delete, complete =complete )
                        }
                        }
                    else {
                        item{
                            PastTask(navController = navController, mission = task)
                        }
                    }


                }

            }
        }else {

            Text(text ="No tasks available" , fontSize = 18.sp , fontWeight = FontWeight.Bold)
        }

    }





}