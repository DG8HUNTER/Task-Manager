package com.example.taskmanager.Components

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.focus.onFocusEvent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.taskmanager.ui.theme.customColor
import com.example.taskmanager.ui.theme.mediumGray
import com.example.taskmanager.ui.theme.onSurface
import com.example.test.Screens.mainActivityViewModel

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TextFieldComponent(
   name: String,
   focusManager:FocusManager

){

   val firstNameColorIcon = animateColorAsState(targetValue = if(mainActivityViewModel.firstName.value==null) Color.Transparent else mediumGray,
      label = "", animationSpec = tween(1000, easing = FastOutSlowInEasing)

   )

   val lastNameColorIcon = animateColorAsState(targetValue = if(mainActivityViewModel.lastName.value==null) Color.Transparent else mediumGray,
      label = "", animationSpec = tween(1000, easing = FastOutSlowInEasing)

   )

   val phoneNumberColorIcon = animateColorAsState(targetValue = if(mainActivityViewModel.phoneNumber.value==null) Color.Transparent else mediumGray,
      label = "", animationSpec = tween(1000, easing = FastOutSlowInEasing)

   )

   val titleColorIcon = animateColorAsState(targetValue = if(mainActivityViewModel.title.value==null) Color.Transparent else mediumGray,
      label = "", animationSpec = tween(1000, easing = FastOutSlowInEasing)

   )

   val descriptionColorIcon = animateColorAsState(targetValue = if(mainActivityViewModel.description.value==null) Color.Transparent else mediumGray,
      label = "", animationSpec = tween(1000, easing = FastOutSlowInEasing)

   )

   var firstNameError:Boolean by remember {
      mutableStateOf(false)
   }

   var lastNameError:Boolean by remember {
      mutableStateOf(false)
   }
   var phoneNumberError:Boolean by remember {
      mutableStateOf(false)
   }
   val firstNameErrorMessage by remember {
      mutableStateOf("Required Field")
   }

   val lastNameErrorMessage by remember {
      mutableStateOf("Required Field")
   }

   var phoneNumberErrorMessage by remember {
      mutableStateOf("Required Field")
   }

   val titleErrorMessage by remember {
      mutableStateOf("Required Field")
   }

   val descriptionErrorMessage by remember {
      mutableStateOf("Required Field")
   }








   Column(modifier=Modifier
      .fillMaxWidth()

   ){



      OutlinedTextField(value =
      when (name) {
         "firstName" -> if (mainActivityViewModel.firstName.value != null) mainActivityViewModel.firstName.value.toString() else ""
         "lastName" -> if (mainActivityViewModel.lastName.value != null) mainActivityViewModel.lastName.value.toString() else ""
         "phoneNumber" -> if (mainActivityViewModel.phoneNumber.value != null) mainActivityViewModel.phoneNumber.value.toString() else ""
         "title" ->  if (mainActivityViewModel.title.value != null) mainActivityViewModel.title.value.toString() else ""
         else ->if (mainActivityViewModel.description.value != null) mainActivityViewModel.description.value.toString() else ""
      }
         , onValueChange = {

            if(it.isNotEmpty()){
               when (name) {
                  "firstName" -> mainActivityViewModel.setValue(it,"firstName")
                  "lastName" -> mainActivityViewModel.setValue(it,"lastName")
                  "phoneNumber" -> mainActivityViewModel.setValue(it , "phoneNumber")
                  "title" -> mainActivityViewModel.setValue(it , "title")
                  else -> mainActivityViewModel.setValue(it , "description")

               }

            }else{
               when (name) {
                  "firstName" -> mainActivityViewModel.setValue(null,"firstName")
                  "lastName" -> mainActivityViewModel.setValue(null,"lastName")
                  "phoneNumber" -> mainActivityViewModel.setValue(null , "phoneNumber")
                  "title"-> mainActivityViewModel.setValue(null , "title")
                  else -> mainActivityViewModel.setValue(null , "description")

               }
            }




         },
         label = {

            Text(text = when(name) {
               "firstName" -> "FirstName"
               "lastName" -> "LastName"
               "phoneNumber"-> "PhoneNumber"
               "title" -> "Title"
               else -> "Description"


            } , fontSize = 15.sp,
               fontWeight = FontWeight.Medium)
         },

         placeholder = {
            Text(text = when(name) {
               "firstName" -> "FirstName"
               "lastName" -> "LastName"
               "phoneNumber"-> "PhoneNumber"
               "title" -> "Title"
               else -> "Description"

            } , fontSize = 15.sp,
               fontWeight = FontWeight.Medium)
         },

         leadingIcon = {
            Icon(imageVector =
            when(name){
               "firstName"-> Icons.Filled.Person
               "lastName"->Icons.Filled.Person
               "phoneNumber" -> Icons.Filled.Phone
               "title" -> Icons.Filled.List
               else -> Icons.Filled.Info
            }
               , contentDescription ="",

               tint= mediumGray

            )

         },

         trailingIcon = {
            IconButton(onClick = { when(name){
               "firstName"->mainActivityViewModel.setValue(null , "firstName")
               "lastName"->mainActivityViewModel.setValue(null , "lastName")
               "phoneNumber" -> mainActivityViewModel.setValue(null,"phoneNumber")
               "title" ->mainActivityViewModel.setValue(null,"title")
               else -> mainActivityViewModel.setValue(null,"description")
            } }) {
               Icon(imageVector = Icons.Filled.Clear, contentDescription ="clearIcon",
                  tint= when(name){
                     "firstName"->firstNameColorIcon.value
                     "lastName"->lastNameColorIcon.value
                     "phoneNumber" -> phoneNumberColorIcon.value
                     "title" ->titleColorIcon.value
                     else -> descriptionColorIcon.value
                  }

               )

            }


         },

         keyboardOptions = KeyboardOptions(
            keyboardType = when(name){
               "firstName"-> KeyboardType.Text
               "lastName"->KeyboardType.Text
               "phoneNumber" -> KeyboardType.Phone
               "title" -> KeyboardType.Text
               else ->  KeyboardType.Text
            },
            imeAction =
            when(name){
               "firstName"-> ImeAction.Next
               "lastName"-> ImeAction.Next
               "phoneNumber" -> ImeAction.Done
               "title" -> ImeAction.Next
               else -> ImeAction.Next
            }


         ), keyboardActions = KeyboardActions(
            onDone = {focusManager.clearFocus()}
         ),
         modifier= Modifier.onFocusEvent {
            focusState ->
            if(focusState.isFocused){
               when(name){
                  "firstName"-> mainActivityViewModel.setValue(false,"firstNameError")
                  "lastName"-> mainActivityViewModel.setValue(false,"lastNameError")
                  "phoneNumber" ->  mainActivityViewModel.setValue(false,"phoneNumberError")
                  "title" ->mainActivityViewModel.setValue(false,"titleError")
                  else -> mainActivityViewModel.setValue(false,"descriptionError")
               }
            }
         }
            .fillMaxWidth()
            .clip(shape = RoundedCornerShape(5.dp))
            .background(shape = RoundedCornerShape(5.dp), color = Color.Transparent)
         ,    colors = TextFieldDefaults.textFieldColors(
            focusedTextColor = onSurface,
            unfocusedIndicatorColor = mediumGray,
            focusedIndicatorColor = customColor,
            cursorColor = onSurface,
            focusedLabelColor = customColor,
            unfocusedLabelColor = mediumGray,
            containerColor = Color.Transparent,
            errorContainerColor = Color.Transparent
         ),

         isError = when(name){
            "firstName"-> mainActivityViewModel.firstNameError.value
            "lastName"-> mainActivityViewModel.lastNameError.value
            "phoneNumber"-> mainActivityViewModel.phoneNumberError.value
            "title" ->  mainActivityViewModel.titleError.value
            else ->  mainActivityViewModel.descriptionError.value
         },
        singleLine = true



      )

      when(name){
         "firstName"-> if(mainActivityViewModel.firstNameError.value) Text(text =firstNameErrorMessage , color = MaterialTheme.colorScheme.error,
            style = MaterialTheme.typography.bodyMedium )
         "lastName"-> if (mainActivityViewModel.lastNameError.value) Text(text = lastNameErrorMessage, color = MaterialTheme.colorScheme.error,
            style = MaterialTheme.typography.bodyMedium )
         "phoneNumber" -> if(mainActivityViewModel.phoneNumberError.value) Text(text= mainActivityViewModel.phoneNumberErrorMessage.value , color = MaterialTheme.colorScheme.error,
            style = MaterialTheme.typography.bodyMedium )
        else -> {}
      }


   }

}








