package com.example.suraz.todolist

/**
 * Created by suraz on 2/17/18.
 */
class User (val user_id:String, val username : String, val password : String){
    //empty constructor is required for Firebase
    constructor():this("","","")
}
