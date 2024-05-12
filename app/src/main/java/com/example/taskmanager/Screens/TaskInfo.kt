import android.content.ContentValues
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.taskmanager.Classes.Mission
import com.example.taskmanager.ui.theme.customColor
import com.example.test.Screens.mainActivityViewModel
import com.google.firebase.Timestamp
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun TaskInfo(
    navController: NavController,
    id:String
) {

    var task : Mission by remember {
        mutableStateOf(Mission())
    }
    val db = Firebase.firestore

    val taskRef = db.collection("Tasks").document(id)
val scope = rememberCoroutineScope()

    taskRef.addSnapshotListener { snapshot, e ->
        if (e != null) {
            Log.w(ContentValues.TAG, "Listen failed.", e)
            return@addSnapshotListener
        }

        if (snapshot != null) {
            scope.launch(Dispatchers.Default) {
                if (snapshot.data!=null) {
                    val createdAtTimestamp = snapshot.data?.get("dateTime") as Timestamp
                    val createdAtDate = createdAtTimestamp.toDate()
                    val sdf = SimpleDateFormat("dd-MM-yyyy hh:mm:ss a")
                    val formattedDate = sdf.format(createdAtDate)
                    val mission = Mission(
                        title = snapshot.data!!["title"].toString(),
                        description = snapshot.data!!["description"].toString(),
                       dateTime = createdAtDate,
                        userId = snapshot.data!!["userId"].toString(),
                        status = snapshot.data!!["status"].toString(),
                        priority = snapshot.data!!["priority"].toString(),


                    )


                    withContext(Dispatchers.Main) {
                        task = mission
                    }
                }


            }

        }

    }



        LazyColumn(
            modifier = Modifier
                .padding(20.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            item{
                Row(modifier=Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically){
                    IconButton(onClick = {
                        mainActivityViewModel.setValue("Task", "selectedOption")
                        navController.popBackStack() }) {
                        Icon(imageVector = Icons.Filled.KeyboardArrowLeft, contentDescription ="" , modifier=Modifier.size(30.dp))


                    }
                    Text(
                        text = "Task Info",
                        color = Color.Black,
                        fontWeight = FontWeight.Bold,
                        fontSize = 25.sp
                    )

                }
            }

            item{
                Column(verticalArrangement = Arrangement.spacedBy(5.dp)){
                    Text(text ="Title:" , style = title())
                    Text(text =task.title,style=sub())
                }


            }

            item{
                Column(verticalArrangement = Arrangement.spacedBy(5.dp)){
                    Text(text ="Description:" , style = title())
                    Text(text =task.description,style=sub())
                }

            }
           item{
               Column(verticalArrangement = Arrangement.spacedBy(5.dp)){
                   Text(text ="Date:" , style = title())
                   Text(text =task.dateTime.toString(),style=sub())
               }

           }

         item{
             Column(verticalArrangement = Arrangement.spacedBy(5.dp)){
                 Text(text ="Status:" , style = title())
                 Text(text =task.status,style=sub())
             }
         }


          item{
              Column(verticalArrangement = Arrangement.spacedBy(5.dp)){
                  Text(text ="Priority:" , style = title())
                  Text(text =task.priority,style=sub())
              }

          }

            item{
                Spacer(modifier =Modifier.height(15.dp))
                Button(onClick = {

                    scope.launch(Dispatchers.Default){
                        mainActivityViewModel.setValue(task.title , "title")
                        mainActivityViewModel.setValue(task.description , "description")
                        mainActivityViewModel.setValue(task.priority , "priority")
                        val d = parseDateString(task.dateTime.toString())
                        mainActivityViewModel.setValue(d.dayOfMonth.toString() , "day")
                        mainActivityViewModel.setValue(d.monthValue.toString() , "month")
                        mainActivityViewModel.setValue(d.year.toString() , "year")
                        mainActivityViewModel.setValue(d.hour.toString() , "hour")
                        mainActivityViewModel.setValue(d.minute.toString() , "minutes")
                        if(d.hour<12){
                            mainActivityViewModel.setValue("AM" , "dayPeriod")
                        }else{
                            mainActivityViewModel.setValue("PM" , "dayPeriod")
                        }

                        withContext(Dispatchers.Main){
                            mainActivityViewModel.setValue("AddTask", "selectedOption")
                            mainActivityViewModel.setValue(false, "addStatus")
                            mainActivityViewModel.setValue(id ,"taskId")
                            navController.navigate("MainScreen")
                        }



                    }










                } ,shape = RectangleShape, modifier = Modifier
                    .fillMaxWidth()
                    .clip(
                        RoundedCornerShape(10.dp)
                    )
                    .background(color = customColor, shape = RoundedCornerShape(10.dp)), colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Transparent
                    )
                 ) {

                    Row(modifier=Modifier.fillMaxSize(), verticalAlignment = Alignment.CenterVertically , horizontalArrangement = Arrangement.Center){

                        Text(text ="Update", fontSize = 18.sp , fontWeight = FontWeight.Bold , color=Color.White)

                    }

                }
            }


        }

}



fun title(): TextStyle {
    return TextStyle(
        fontSize = 20.sp,
        color = Color.Black,
        fontWeight = FontWeight.Bold
    )
}

fun sub(): TextStyle {
    return TextStyle(
        fontSize = 16.sp,
        color = Color.Gray,
        fontWeight = FontWeight.Medium
    )
}

@RequiresApi(Build.VERSION_CODES.O)
fun parseDateString(dateString: String): LocalDateTime {
    val formatter = DateTimeFormatter.ofPattern("EEE MMM dd HH:mm:ss zzz yyyy") // Adjust the pattern according to your date format
    return LocalDateTime.parse(dateString, formatter)
}

