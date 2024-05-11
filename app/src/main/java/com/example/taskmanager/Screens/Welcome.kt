package com.example.taskmanager.Screens

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.taskmanager.R
import com.example.taskmanager.ui.theme.customColor

@Composable


fun Welcome(navController: NavController){
    var launched by remember {
        mutableStateOf(false)
    }
    val rotation by animateFloatAsState(targetValue = if(launched)360f else 0f, animationSpec = tween(2000, easing = FastOutSlowInEasing),
        label = ""
    )
    LaunchedEffect(key1 = true) {
        launched=true
    }
    Column(modifier= Modifier
        .fillMaxSize()
        .padding(20.dp), horizontalAlignment = Alignment.CenterHorizontally){

        Column(modifier=Modifier.fillMaxWidth(),){
            Row(modifier=Modifier.fillMaxWidth() , verticalAlignment = Alignment.CenterVertically){
                Text(text = "Welcome to " , fontSize = 25.sp , fontWeight = FontWeight.Bold)
                Text(text = "Task Manager " , fontSize = 25.sp , fontWeight = FontWeight.Bold, color = customColor,fontFamily = FontFamily.Serif)
                Icon(
                    painter = painterResource(id = R.drawable.task),
                    contentDescription = "marketplace",
                    tint = customColor,
                    modifier = Modifier
                        .size(30.dp)


                )



            }
            Text(text = "Your pocket-friendly task manager " , fontSize = 15.sp, fontWeight = FontWeight.W400 )

        }

            Image(painter = painterResource(id = R.drawable.taskmanager), contentDescription = "", modifier = Modifier.size(350.dp))

        Text(buildAnnotatedString {
            withStyle(style = SpanStyle(color=Color.Gray,fontWeight= FontWeight.Medium, fontSize = 16.sp)){
                append("Stay organized and productive with ")
            }
            withStyle(style = SpanStyle(color = customColor , fontWeight = FontWeight.Medium , fontSize = 16.sp)){
                append("Task Manager")
            }
            withStyle(style = SpanStyle(color=Color.Gray,fontWeight= FontWeight.Medium, fontSize = 16.sp)){
                append(" the ultimate task manager app. Effortlessly manage tasks, deadlines, and priorities in one place."





                )
            }

        }, modifier = Modifier.fillMaxWidth() , textAlign = TextAlign.Start, lineHeight = 20.sp)

Spacer(Modifier.height(35.dp))
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment.CenterVertically){
            Button(onClick = {navController.navigate("SignUpScreen")}, shape = RectangleShape,
                modifier = Modifier
                    .fillMaxWidth(0.9f)
                    .clip(shape = RoundedCornerShape(10.dp)),contentPadding = PaddingValues(), colors = ButtonDefaults.buttonColors(containerColor = Color.Red)){
                Box(modifier = Modifier.fillMaxWidth().clip(shape= RoundedCornerShape(2.dp)).background(color= customColor, shape = RoundedCornerShape(10.dp)).padding(15.dp), contentAlignment = Alignment.Center){
                    Text(text ="Get Started", fontWeight = FontWeight.Bold , fontSize = 18.sp, color=Color.White )

                }

            }

        }



    }


}