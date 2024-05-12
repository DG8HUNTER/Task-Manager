package com.example.taskmanager.Screens

import android.util.Log
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*

import androidx.compose.material.icons.Icons
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.onFocusEvent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

import com.example.taskmanager.R
import com.example.taskmanager.ui.theme.customColor
import com.example.taskmanager.ui.theme.mediumGray
import com.example.taskmanager.ui.theme.onSurface
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PasswordSecurity(navController: NavController) {
    val auth = Firebase.auth
    val currentUser=auth.currentUser
    var oldPassword: String? by remember {
        mutableStateOf(null)
    }
    val userUi = Firebase.auth.currentUser?.uid.toString()
    val db = Firebase.firestore
    val focusManager = LocalFocusManager.current


    var oldPasswordVisibility: Boolean by remember {
        mutableStateOf(false)
    }
    var oldPasswordRequirementError: Boolean by remember {
        mutableStateOf(false)
    }
    var oldPasswordErrorMessage: String? by remember {
        mutableStateOf(null)
    }
    val iconColor by animateColorAsState(
        targetValue = if (oldPassword != null) mediumGray else Color.Transparent,
        animationSpec = tween(durationMillis = 500, easing = FastOutSlowInEasing)
    )


    Column(
        modifier = Modifier.fillMaxSize().padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(1.dp)
        ) {
            IconButton(onClick = {
                navController.navigate(route = "MainScreen") {
                    popUpTo(0)

                }

            }
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.arrow_left),
                    contentDescription = "arrow left icon",
                    tint = Color.Black,
                    modifier = Modifier.size(25.dp)
                )

            }
            Text(
                text ="Security Phase", //"Password Reset",
                fontSize = 25.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Start
            )
        }

        Text(
            text = "For your security please enter your old password",
            fontSize = 17.sp,
            fontWeight = FontWeight.Medium,
            color = mediumGray,
            modifier = Modifier.fillMaxWidth(7f),
            textAlign = TextAlign.Center
        )


        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(2.dp),
            horizontalAlignment = Alignment.Start
        ) {
            OutlinedTextField(
                value = if (oldPassword != null) oldPassword.toString() else "",
                onValueChange = {
                    oldPassword = if (it.isNotEmpty()) it else null
                },
                label = {
                    Text(
                        text = "Password",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Medium,

                        )
                },
                placeholder = {
                    Text(
                        text = " Password",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color.LightGray
                    )
                },
                leadingIcon = {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_lock),
                        contentDescription = "Lock icon",
                        tint = mediumGray,

                        )
                },
                trailingIcon = {
                    IconButton(onClick = {
                        oldPasswordVisibility = !oldPasswordVisibility
                    }) {
                        Icon(
                            painter = painterResource(id = if (oldPasswordVisibility) R.drawable.ic_visibility else R.drawable.ic_visibility_off),
                            contentDescription = if (oldPasswordVisibility) "visibility icon" else "visibility off icon",
                            tint = iconColor
                        )
                    }
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password,
                    imeAction = ImeAction.Done
                ),

                keyboardActions = KeyboardActions(
                    onDone = {
                        focusManager.clearFocus()
                    }
                ),
                visualTransformation = if (oldPasswordVisibility) VisualTransformation.None else PasswordVisualTransformation(),
                modifier = Modifier.onFocusEvent { focusState ->
                    if (focusState.isFocused) {
                        oldPasswordRequirementError = false
                        oldPasswordErrorMessage = null
                    }
                }
                    .fillMaxWidth()
                    .clip(shape = RoundedCornerShape(5.dp))
                    .background(color = Color.Transparent, shape = RoundedCornerShape(5.dp)),
                colors = TextFieldDefaults.textFieldColors(
                    focusedTextColor= onSurface,
                    containerColor = Color.Transparent,
                    unfocusedIndicatorColor = mediumGray,
                    focusedIndicatorColor = customColor,
                    cursorColor = onSurface,
                    focusedLabelColor = customColor,
                    unfocusedLabelColor = mediumGray,

                ), isError = oldPasswordRequirementError
            )
            if (oldPasswordRequirementError) {

                Text(
                    text = "$oldPasswordErrorMessage",
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(start = 16.dp, top = 0.dp)
                )
            }
        }
        Button(
            onClick = {
                focusManager.clearFocus()
                if (oldPassword == null) {
                    oldPasswordRequirementError = true
                    oldPasswordErrorMessage = "Required Field"
                }
//                if (mainActivityViewModel.password.value != oldPassword) {
//                Log.d("pass", mainActivityViewModel.password.value.toString())
//                oldPasswordRequirementError = true
//                oldPasswordErrorMessage = "Wrong password !"
//                } else {
//                    navController.navigate(route = "ResetPassword?userUi=$userUi&oldPassword=$oldPassword/PasswordSecurity") {
//
//                        popUpTo(route = "PasswordSecurity/$userUi")
//                        launchSingleTop = true
//                    }
//                }
//                db.collection("Users").document(userUi)
//                    .get()
//                    .addOnSuccessListener { document ->
//                        if (document != null) {
//                            if (document.data?.get("password")!= oldPassword
//                            ) {
//                                oldPasswordRequirementError = true
//                                oldPasswordErrorMessage = "Wrong password !"
//                            } else {
//                                navController.navigate(route = "ResetPassword?userUi=$userUi&oldPassword=$oldPassword/PasswordSecurity") {
//                                    popUpTo(route = "PasswordSecurity/$userUi")
//                                    launchSingleTop = true
//                                }
//                            }
//                        }
//                    }
                val credential = EmailAuthProvider.getCredential(currentUser?.email.toString(), oldPassword.toString())

                Log.d("provide data" , currentUser?.providerData.toString())


                currentUser!!.reauthenticate(credential)
                    .addOnSuccessListener {
                        navController.navigate(route = "ResetPassword?oldPassword=$oldPassword/PasswordSecurity") {
                                    popUpTo(route = "PasswordSecurity")
                                   launchSingleTop = true }
                    }
                    .addOnFailureListener{
                        oldPasswordRequirementError = true
                        oldPasswordErrorMessage = "Wrong password !"
                    }

                            Log.d("Current", currentUser.email.toString().toString())


                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.Transparent
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(shape = RoundedCornerShape(10.dp)),
                        contentPadding = PaddingValues()
                        ) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(55.dp)
                                .clip(shape = RoundedCornerShape(10.dp))
                                .background(
                                    color = customColor,
                                    shape = RoundedCornerShape(10.dp)
                                ),
                            contentAlignment = Alignment.Center,
                        ) {
                            Text(
                                text = "Submit",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Medium,
                                color = Color.White
                            )

                        }

                    }
                    }

    }





