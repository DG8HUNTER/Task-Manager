package com.example.taskmanager.Screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.taskmanager.Classes.SRow
import com.example.taskmanager.Classes.User
import com.example.taskmanager.Components.SettingRow
import com.example.taskmanager.R
import com.example.taskmanager.ui.theme.darkGray
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

@Composable

fun Settings(navController: NavController){

    val scope = rememberCoroutineScope()
    var user: User by remember {
        mutableStateOf(User())
    }

    val userId = Firebase.auth.currentUser?.uid.toString()


    val db =Firebase.firestore
    val currentUser=Firebase.auth.currentUser?.uid.toString()

    val rows: List<SRow> =
        listOf(SRow.UpdateUserInfo, SRow.ChangePassword, SRow.SwitchAccount, SRow.SignOut)

    LaunchedEffect(key1 =true) {
        scope.launch(Dispatchers.Default){
            lateinit var data :User
            val doc = db.collection("Users").document(currentUser).get().await()
            if(doc!=null){
                 data = User(firstName = doc.data?.get("FirstName").toString(),
                    lastName =  doc.data?.get("LastName").toString(),
                    phoneNumber =  doc.data?.get("PhoneNumber").toString(),
                    userId =  doc.data?.get("UserID").toString()
                    )

            }

            withContext(Dispatchers.Main){
                user=data
            }
        }


    }



    Column(modifier= Modifier.fillMaxSize().padding(20.dp), horizontalAlignment = Alignment.CenterHorizontally){


        Column(
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.account),
                    contentDescription = "Account Icon",
                    modifier = Modifier.size(20.dp)

                )
                Text(
                    text = "Account Info",
                    fontSize = 17.sp,
                    fontWeight = FontWeight.Bold
                )

            }
            Spacer(
                modifier = Modifier
                    .fillMaxWidth(0.95f)
                    .height(1.dp)
                    .background(color = Color.LightGray, shape = RoundedCornerShape(5.dp))
                    .clip(shape = RoundedCornerShape(5.dp))
            )


            Row {
                Text(
                    text = "Name : ",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    color = darkGray
                )
                Text(
                    text = "${user.firstName}  ${user.lastName}",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = darkGray
                )
            }
            Row {
                Text(
                    text = "Email : ",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    color = darkGray
                )
                Text(
                    text = "${Firebase.auth.currentUser?.email}",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = darkGray
                )

            }
            Row {
                Text(
                    text = "PhoneNumber : ",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    color = darkGray
                )
                Text(
                    text = "+961 ${user.phoneNumber}",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = darkGray
                )
            }


            Spacer(
                modifier = Modifier
                    .fillMaxWidth(0.95f)
                    .height(1.dp)
                    .background(color = Color.LightGray, shape = RoundedCornerShape(5.dp))
                    .clip(shape = RoundedCornerShape(5.dp))
            )

            Spacer(modifier = Modifier.height(2.dp))
            rows.forEach { row ->
                SettingRow(navController = navController, userUi = userId, sRow = row)
            }


        }

    }

}