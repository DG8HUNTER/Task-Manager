package com.example.taskmanager

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.input.key.Key.Companion.Home
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.taskmanager.Screens.Landing
import com.example.taskmanager.Screens.Main
import com.example.taskmanager.Screens.Welcome
import com.example.test.Screens.PersonalInfo
import com.example.test.Screens.SignIn
import com.example.test.Screens.SignUp

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun Navigation(navController: NavController , startDestination:String){

    NavHost(navController =navController as NavHostController, startDestination =startDestination ) {

        composable(route="LandingScreen"){
            Landing(navController=navController)
        }

        composable(route="WelcomeScreen"){
            Welcome(navController=navController)
        }

        composable(route = "SignUpScreen") {
            SignUp(navController = navController)


        }

        composable(route = "SignInScreen") {
            SignIn(navController = navController)


        }
        composable(route = "PersonalInfo/{screen}" , arguments=listOf(
            navArgument(name="screen"){
                type= NavType.StringType
                nullable=false
            }
        )) {
                backStackEntry->

            PersonalInfo(navController, screen=backStackEntry.arguments?.get("screen").toString())
        }

        composable(route="MainScreen"){
            Main(navController=navController)
        }

    }


}