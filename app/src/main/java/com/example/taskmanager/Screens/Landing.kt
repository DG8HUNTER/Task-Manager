package com.example.taskmanager.Screens

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
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
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.taskmanager.R
import com.example.taskmanager.ui.theme.customColor
import kotlinx.coroutines.delay

@Composable

fun Landing(navController: NavController){

    var isLaunched by remember {
        mutableStateOf(false)
    }
    val rotation = animateFloatAsState(targetValue =if(!isLaunched)0f else 360f , label = "", animationSpec = tween(1000, easing = FastOutSlowInEasing))
    LaunchedEffect(key1 = true) {
        delay(1000)
        isLaunched=true
        delay(2000)
        navController.navigate("WelcomeScreen")

    }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,


        ) {


        Icon(
            painter = painterResource(id = R.drawable.task),
            contentDescription = "marketplace",
            tint = customColor,
            modifier = Modifier
                .size(110.dp)
                .rotate(rotation.value)
        )



        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = "Task Manager",
            fontFamily = FontFamily.Serif,
            fontWeight = FontWeight.Bold,
            color = customColor,
            fontSize = 22.sp
        )


    }


}