package com.example.taskmanager.Screens

import android.content.ContentValues
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.input.key.Key.Companion.Home
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.taskmanager.Classes.Mission
import com.example.taskmanager.Components.TaskDayTime
import com.example.taskmanager.R
import com.example.taskmanager.ui.theme.customColor
import com.example.test.Screens.mainActivityViewModel
import com.google.firebase.Timestamp
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat

@RequiresApi(Build.VERSION_CODES.O)
@Composable

fun Main(navController: NavController){
val db =Firebase.firestore
    Log.d("option", mainActivityViewModel.selectedOption.value.toString())
    val tasksRef = db.collection("Tasks").orderBy("dateTime")
    val scope = rememberCoroutineScope()
    val currentUser = Firebase.auth.currentUser?.uid.toString()


    tasksRef.addSnapshotListener { snapshot, e ->
        if (e != null) {
            Log.w(ContentValues.TAG, "Listen failed.", e)
            return@addSnapshotListener
        }

        if (snapshot != null) {
            scope.launch(Dispatchers.Default) {
                val list: MutableList<Mission> = mutableListOf()
                if (snapshot.documents.isNotEmpty()) {
                    for (doc in snapshot.documents) {
                        if (doc != null && doc.data?.get("userId")==currentUser) {
                            val createdAtTimestamp = doc.data?.get("dateTime") as Timestamp
                            val createdAtDate = createdAtTimestamp.toDate()
                            val sdf = SimpleDateFormat("dd-MM-yyyy hh:mm:ss a")
                            val formattedDate = sdf.format(createdAtDate)
                            val task = Mission(
                                title = doc.data?.get("title").toString(),
                                description = doc.data?.get("description").toString(),
                                dateTime = createdAtDate,
                                priority = doc.data?.get("priority").toString(),
                                userId = doc.data?.get("userId").toString(),
                                taskId = doc.data?.get("taskId").toString(),
                                status = doc.data?.get("status").toString()

                            )
                            list.add(task)
                        }
                    }

                    withContext(Dispatchers.Main) {
                        mainActivityViewModel.setValue(list, "tasks")
                    }
                }


            }

        }

    }




    var firstName by remember {
        mutableStateOf("")
    }

    var lastName by remember {
        mutableStateOf("")
    }

    var selectedTaskIndex by remember {
        mutableIntStateOf(0)
    }

    var selectedTask by remember {
        mutableStateOf("To Do")
    }



    LaunchedEffect(key1 =true) {
        scope.launch {

            val doc = db.collection("Users").document(currentUser).get().await()
            if(doc!=null){
                firstName=doc.data!!["FirstName"].toString()
                lastName=doc.data!!["LastName"].toString()
            }
        }

    }

    Scaffold(modifier = Modifier
        .fillMaxSize()
        .background(color = Color.Red),
        floatingActionButton = {
               if(selectedTask=="To Do" && mainActivityViewModel.selectedOption.value=="Task"){
                   if(mainActivityViewModel.todayTasks.value.size==0){
                       FloatingActionButton(onClick = { mainActivityViewModel.setValue("AddTask" , "selectedOption") }, containerColor = customColor ) {
                           Icon(imageVector = Icons.Default.Add, contentDescription = "add", tint=Color.White)
                       }

                   }

               }


        },

        topBar = {

            when(mainActivityViewModel.selectedOption.value){
                in "Task" , "SearchTask"->  Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp),
                    horizontalAlignment = Alignment.Start
                ) {
                    Text(
                        text = if(mainActivityViewModel.selectedOption.value=="Task") "Home" else  "Search Task" ,
                        fontSize = 25.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color.Black,
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Start
                    )
                    if(mainActivityViewModel.selectedOption.value=="Task"){
                        Text(buildAnnotatedString {
                            withStyle(style = SpanStyle(color=Color.Gray,fontWeight= FontWeight.Medium, fontSize = 16.sp)){
                                append("Welcome ")
                            }
                            withStyle(style = SpanStyle(color = customColor , fontWeight = FontWeight.Medium , fontSize = 16.sp)){
                                append("$firstName $lastName")
                            }

                        }, modifier = Modifier.fillMaxWidth() , textAlign = TextAlign.Start)

                        Text(text="Today Tasks" , fontSize = 22.sp  , fontWeight = FontWeight.Bold)
                    }
                    else {

                        val focusManager = LocalFocusManager.current
                        LaunchedEffect(key1 = mainActivityViewModel.tasks.value , key2=selectedTask) {
                            if(mainActivityViewModel.searchDay.value!=null
                                && mainActivityViewModel.searchMonth.value!=null
                                && mainActivityViewModel.searchYear.value!=null
                                ){
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

                            }


                        }


                        TaskDayTime(focusManager=focusManager, selectedTask = selectedTask)


                    }

                    TabRow(
                        selectedTabIndex = selectedTaskIndex,
                        modifier = Modifier.fillMaxWidth(),
                        contentColor = customColor,
                        indicator = { tabPositions ->
                            TabRowDefaults.Indicator(
                                modifier = Modifier
                                    .tabIndicatorOffset(tabPositions[selectedTaskIndex])
                                    .clip(shape = RoundedCornerShape(10.dp)),
                                color = customColor,
                                height = 4.dp
                            )
                        }) {
                        Tab(
                            selected = selectedTaskIndex == 0,
                            onClick = {
                                selectedTaskIndex = 0
                                selectedTask = "To Do"
                            },
                            selectedContentColor = customColor
                        ) {
                            Text(
                                text = "To Do",
                                fontSize = 15.sp,
                                fontWeight = FontWeight.Medium,
                                color = if (selectedTaskIndex == 0) Color.Black else Color.Gray,
                                modifier = Modifier.padding(10.dp)
                            )

                        }
                        Tab(selected = selectedTaskIndex == 1,
                            onClick = {
                                selectedTaskIndex= 1
                                selectedTask = "Past Tasks"
                            },
                            selectedContentColor = customColor
                        ) {
                            Text(
                                text = "Past Tasks",
                                fontSize = 15.sp,
                                fontWeight = FontWeight.Medium,
                                color = if (selectedTaskIndex == 1) Color.Black else Color.Gray,
                                modifier = Modifier.padding(10.dp)
                            )

                        }

                    }


                }

                "AddTask"-> Row(modifier= Modifier
                    .fillMaxWidth()
                    .padding(20.dp)){
                  Text(text= if(mainActivityViewModel.addStatus.value)"Add Task" else "Update Task", fontSize=25.sp , fontWeight = FontWeight.Bold)
                    }



                else -> Row(modifier= Modifier
                    .fillMaxWidth()
                    .padding(20.dp)){
                    Text(text="Settings" , fontSize = 25.sp , fontWeight = FontWeight.Bold)
                }

            }


        },

        bottomBar = {
            BottomAppBar(modifier= Modifier
                .fillMaxWidth()
                .height(70.dp)
                .clip(shape = RoundedCornerShape(topStart = 15.dp, topEnd = 15.dp)), contentPadding = PaddingValues(10.dp), containerColor = Color.LightGray){

                Row(modifier=Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween){
                    IconButton(onClick = {
                       mainActivityViewModel.setValue("Task","selectedOption")

                    }, modifier = Modifier.size(40.dp)) {
                        Icon(painter = painterResource(id = R.drawable.task), contentDescription ="",modifier=Modifier.size(25.dp), tint = if(mainActivityViewModel.selectedOption.value=="Task") customColor else Color.Black)

                    }
                    IconButton(onClick = {  mainActivityViewModel.setValue("AddTask","selectedOption") }, modifier = Modifier.size(40.dp)) {
                        Icon(painter = painterResource(id = R.drawable.addtaskpng), contentDescription ="",modifier=Modifier.size(25.dp),tint = if(mainActivityViewModel.selectedOption.value=="AddTask") customColor else Color.Black)

                    }
                    IconButton(onClick = { mainActivityViewModel.setValue("SearchTask","selectedOption") },modifier = Modifier.size(40.dp)) {
                        Icon(painter = painterResource(id = R.drawable.search), contentDescription ="",modifier=Modifier.size(25.dp),tint = if(mainActivityViewModel.selectedOption.value=="SearchTask") customColor else Color.Black)

                    }
                    IconButton(onClick = {  mainActivityViewModel.setValue("Settings","selectedOption")},modifier = Modifier.size(40.dp)) {
                        Icon(painter = painterResource(id = R.drawable.settings), contentDescription ="",modifier=Modifier.size(25.dp),tint = if(mainActivityViewModel.selectedOption.value=="Settings") customColor else Color.Black )

                    }

                }


            }

        }


        ){it->

        Column(modifier= Modifier
            .padding(it)
            .fillMaxSize()
            ){
            when(mainActivityViewModel.selectedOption.value){
                "Task" -> Task(navController = navController,selectedTask=selectedTask)
                "AddTask" -> AddTask(navController =navController)
                "SearchTask"-> SearchTask(navController=navController , selectedTask=selectedTask)
                else -> Settings(navController=navController)
            }

        }



    }
}