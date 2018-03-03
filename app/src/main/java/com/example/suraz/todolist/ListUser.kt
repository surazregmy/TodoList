package com.example.suraz.todolist

import android.content.DialogInterface
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.view.LayoutInflater
import android.widget.EditText
import android.widget.ListView
import android.widget.Toast
import com.google.firebase.database.*

class ListUser : AppCompatActivity() {

    lateinit var ref : DatabaseReference  //DataBase connection
    lateinit var userList : MutableList<User>
    lateinit var listview : ListView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_user)
        listview = findViewById(R.id.list_users)

        ref = FirebaseDatabase.getInstance().getReference("Users")
        userList = mutableListOf()

        ref.addValueEventListener(object : ValueEventListener {

            override fun onDataChange(p0: DataSnapshot?) {
                if (p0!!.exists()){
                    userList.clear()  // The values shows double if not removed
                    for (h in p0.children){
                        val user = h.getValue(User ::class.java)
                        userList.add(user!!)
                    }
                }
                //Calling the user adapter
                val user_adapter = UserAdapter(this@ListUser,R.layout.custom_user,userList)
                // Adding the called adapter in ListView
                listview.adapter = user_adapter
                listview.setOnItemClickListener { adapterView, view, i, l ->
                   // Toast.makeText(this@ListUser,"Postition Clicked "+i,Toast.LENGTH_SHORT).show()
                   // Toast.makeText(this@ListUser,"Id clicked "+l,Toast.LENGTH_SHORT).show()
                    val user = userList[i]
                    showDialogbox(user)
                }


            }

            fun showDialogbox(user: User){
                val dbUser = FirebaseDatabase.getInstance().getReference("Users")

                val builder = AlertDialog.Builder(this@ListUser)
                val inflater = LayoutInflater.from(this@ListUser)
                val view = inflater.inflate(R.layout.edit_user,null)


                val username = view.findViewById<EditText>(R.id.username)
                val password = view.findViewById<EditText>(R.id.password)

                username.setText(user.username)
                password.setText(user.password)


                builder.setView(view)
                builder.setPositiveButton("update") { p0, p1 ->
                    val usern = username.text.toString().trim()
                    val pass = password.text.toString().trim()

                    if(usern.isEmpty() or pass.isEmpty()){
                        username.error = "Please Don't leave it empty"
                        password.error = "Donot leave password empty"
                        return@setPositiveButton
                    }

                    val user = User(user.user_id, usern, pass)
                    dbUser.child(user.user_id).setValue(user)
                     Toast.makeText(this@ListUser,"User Edited ",Toast.LENGTH_SHORT).show()

                }
                builder.setNeutralButton("Cancel"){p0,p1 ->

                }
                builder.setNegativeButton("Delete") { p0, p1 ->
                    dbUser.child(user.user_id).removeValue()
                }


                var alert = builder.create()
                alert.show()

            }
            override fun onCancelled(p0: DatabaseError?) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }
        })

    }
}
