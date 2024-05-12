package com.example.taskmanager

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import com.example.taskmanager.Classes.Mission
import com.example.taskmanager.Classes.Priority
import com.google.android.gms.tasks.Task
import java.time.LocalDate
import java.time.LocalTime

class MainActivityViewModel {

    private val _firstName: MutableState<String?> = mutableStateOf(null)
    val firstName : State<String?> = _firstName

    private val  _lastName :MutableState<String?> = mutableStateOf(null)

    val lastName :State<String?> = _lastName

    private val _phoneNumber :MutableState<String?> = mutableStateOf(null)

    val phoneNumber :State<String?> = _phoneNumber

    private  val _password:MutableState<String?> = mutableStateOf(null)
    val password:State<String?> =_password

    private val _firstNameError :MutableState<Boolean> = mutableStateOf(false)
    val firstNameError :State<Boolean> = _firstNameError

    private val _lastNameError :MutableState<Boolean> = mutableStateOf(false)
    val lastNameError :State<Boolean> = _lastNameError

    private val _phoneNumberError :MutableState<Boolean> = mutableStateOf(false)
    val phoneNumberError :State<Boolean> = _phoneNumberError

    private val _phoneNumberErrorMessage : MutableState<String> = mutableStateOf("Required Field")
    val phoneNumberErrorMessage:State<String> =_phoneNumberErrorMessage

    private val  _title :MutableState<String?> = mutableStateOf(null)

    val title :State<String?> = _title


    private val _titleError :MutableState<Boolean> = mutableStateOf(false)
    val titleError :State<Boolean> = _titleError

    private val  _description :MutableState<String?> = mutableStateOf(null)

    val description :State<String?> = _description


    private val _descriptionError :MutableState<Boolean> = mutableStateOf(false)
    val descriptionError :State<Boolean> = _descriptionError

    private val _day :MutableState<String?> = mutableStateOf(null)
    val day :State<String?> = _day

    private val _dayError :MutableState<Boolean> = mutableStateOf(false)
    val dayError :State<Boolean> = _dayError

    private val _month :MutableState<String?> = mutableStateOf(null)
    val month :State<String?> = _month

    private val _monthError :MutableState<Boolean> = mutableStateOf(false)
    val monthError :State<Boolean> = _monthError

    private val _year :MutableState<String?> = mutableStateOf(null)
    val year :State<String?> = _year

    private val _yearError :MutableState<Boolean> = mutableStateOf(false)
    val yearError :State<Boolean> = _yearError

    private val _hour :MutableState<String?> = mutableStateOf(null)
    val hour :State<String?> = _hour

    private val _minutes :MutableState<String?> = mutableStateOf(null)
    val minutes :State<String?> = _minutes

    private val _dayPeriod :MutableState<String> = mutableStateOf("AM")
    val dayPeriod :State<String> = _dayPeriod

    private val _hourError :MutableState<Boolean> = mutableStateOf(false)
    val hourError :State<Boolean> = _hourError

    private val _minutesError :MutableState<Boolean> = mutableStateOf(false)
    val minutesError :State<Boolean> = _minutesError

    private val _priority :MutableState<String> = mutableStateOf(Priority.Low.toString())
    val priority :State<String> = _priority

    private val _tasks :MutableState<MutableList<Mission>> = mutableStateOf(mutableListOf())
    val tasks :State<MutableList<Mission>> = _tasks


    private val _searchDay :MutableState<String?> = mutableStateOf(null)
    val searchDay :State<String?> = _searchDay

    private val _searchMonth :MutableState<String?> = mutableStateOf(null)
    val searchMonth :State<String?> = _searchMonth

    private val _searchYear :MutableState<String?> = mutableStateOf(null)
    val searchYear :State<String?> = _searchYear

    private val _searchTasks :MutableState<MutableList<Mission>> = mutableStateOf(mutableListOf())

    val searchTasks :MutableState<MutableList<Mission>> = _searchTasks

    private val _todayTasks :MutableState<MutableList<Mission>> = mutableStateOf(mutableListOf())
    val todayTasks :MutableState<MutableList<Mission>> = _todayTasks

    @RequiresApi(Build.VERSION_CODES.O)
    private val _dayOfMonth : MutableState<Int> = mutableIntStateOf(LocalDate.now().dayOfMonth)
    @RequiresApi(Build.VERSION_CODES.O)
    val dayOfMonth :State<Int> = _dayOfMonth

    @RequiresApi(Build.VERSION_CODES.O)
    private val _monthOfYear : MutableState<Int> = mutableIntStateOf(LocalDate.now().monthValue)
    @RequiresApi(Build.VERSION_CODES.O)
    val monthOfYear :State<Int> = _monthOfYear

    @RequiresApi(Build.VERSION_CODES.O)
    private val _yearOf : MutableState<Int> = mutableIntStateOf(LocalDate.now().year)
    @RequiresApi(Build.VERSION_CODES.O)
    val yearOf :State<Int> = _yearOf

    private val _selectedOption :MutableState<String> = mutableStateOf("Task")
     val selectedOption :State<String> = _selectedOption

    private val _addStatus : MutableState<Boolean> = mutableStateOf(true)

  val addStatus : MutableState<Boolean> =_addStatus

     private val _taskId :MutableState<String?> = mutableStateOf(null)
    val taskId :State<String?> =_taskId







    @RequiresApi(Build.VERSION_CODES.O)
    fun setValue(newValue:Any?, name:String){

        when(name){
            "firstName"-> if(newValue!=null)_firstName.value=newValue.toString() else _firstName.value=null
            "lastName"-> if(newValue!=null)_lastName.value=newValue.toString() else _lastName.value=null
            "phoneNumber"-> if(newValue!=null)_phoneNumber.value=newValue.toString() else _phoneNumber.value=null
            "password"->if(newValue!=null) _password.value=newValue.toString() else _password.value=null
            "firstNameError"-> if(newValue!=null)_firstNameError.value= newValue as Boolean
            "lastNameError" -> if(newValue!=null)_lastNameError.value=newValue as Boolean
            "phoneNumberError"-> if(newValue!=null)_phoneNumberError.value=newValue as Boolean
            "phoneNumberErrorMessage"-> if (newValue!=null) _phoneNumberErrorMessage.value=newValue as String
            "title" -> if(newValue!=null) _title.value =newValue as  String else _title.value =null
            "titleError"->if(newValue!=null) _titleError.value= newValue as Boolean
            "description"-> if(newValue!=null) _description.value = newValue as String else _description.value=null
            "descriptionError"->if(newValue!=null) _descriptionError.value= newValue as Boolean
            "day"->if(newValue!=null) _day.value=newValue as String else _day.value=null
            "month"->if(newValue!=null) _month.value=newValue as String else _month.value=null
            "year"->if(newValue!=null) _year.value=newValue as String else _year.value=null
            "hour"->if(newValue!=null) _hour.value=newValue as String else _hour.value=null
            "minutes"->if(newValue!=null) _minutes.value=newValue as String else _minutes.value=null
            "dayPeriod"->if(newValue!=null) _dayPeriod.value=newValue as String
            "priority" -> if(newValue!=null) _priority.value=newValue as String
            "hourError" -> if(newValue!=null) _hourError.value = newValue as Boolean
            "minutesError" -> if(newValue!=null) _minutesError.value = newValue as Boolean
            "dayError"->if(newValue!=null) _dayError.value = newValue as Boolean
            "monthError" ->if(newValue!=null) _monthError.value = newValue as Boolean
            "yearError"->if(newValue!=null) _yearError.value = newValue as Boolean
            "tasks" -> if(newValue!=null) _tasks.value = newValue as MutableList<Mission>
            "searchDay"-> if(newValue!=null) _searchDay.value = newValue as String else _searchDay.value=null
            "searchMonth"-> if(newValue!=null) _searchMonth.value = newValue as String else _searchMonth.value=null
            "searchYear"-> if(newValue!=null) _searchYear.value = newValue as String else _searchYear.value=null
            "searchTasks" -> if(newValue!=null) _searchTasks.value = newValue as MutableList<Mission>
            "todayTasks" -> if(newValue!=null) _todayTasks.value = newValue as MutableList<Mission>
            "selectedOption"->if(newValue!=null) _selectedOption.value=newValue as String
            "addStatus"-> if(newValue!=null) _addStatus.value =newValue as Boolean
            "taskId" -> if (newValue!=null) _taskId.value =newValue as String




        }
    }


}