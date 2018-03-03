package com.example.suraz.todolist

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.database.FirebaseDatabase

import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    lateinit var edittext_username : EditText
    lateinit var edittext_password : EditText
    lateinit var button_submit     : Button
    lateinit var button_view : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }
        edittext_username = findViewById(R.id.username)
        edittext_password = findViewById(R.id.password)
        button_submit = findViewById(R.id.submit_butoon)
        button_view = findViewById(R.id.view_butoon)

        button_submit.setOnClickListener {
            saveData()
        }
        button_view.setOnClickListener {
            val intent = Intent(this,ListUser::class.java)
            startActivity(intent)
        }
    }
    private fun saveData(){
         val name = edittext_username.text.toString().trim()
         val pass = edittext_password.text.toString().trim()
        if(name.isEmpty() or pass.isEmpty()){
            edittext_username.error = "Please enter a name"
            edittext_password.error = "Password can not be null"
            return
        }

        val ref = FirebaseDatabase.getInstance().getReference("Users")
        val user_id = ref.push().key
        val user1 = User(user_id,name,pass)

        ref.child(user_id).setValue(user1).addOnCompleteListener {
            Toast.makeText(applicationContext,"User Saved !",Toast.LENGTH_SHORT).show()
        }


    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }
}
