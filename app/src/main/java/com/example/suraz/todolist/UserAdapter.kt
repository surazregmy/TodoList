package com.example.suraz.todolist

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView

/**
 * Created by suraz on 2/17/18.
 */
class UserAdapter(val mcontext : Context,val layoutResId : Int, val userList :List<User>)
    : ArrayAdapter<User>(mcontext,layoutResId,userList) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val layoutInflater : LayoutInflater = LayoutInflater.from(mcontext)
        val view : View = layoutInflater.inflate(layoutResId,null)

        //Getting the textview from the layout
        val user  = view.findViewById<TextView>(R.id.custom_username)
        val pass = view.findViewById<TextView>(R.id.custom_password)

        val user_in_ulist = userList[position]

        user.text = user_in_ulist.username
        pass.text = user_in_ulist.password
        return view
    }
}