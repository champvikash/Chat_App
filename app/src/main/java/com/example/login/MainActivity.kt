package com.example.login

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.login.ViewModel.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {

    lateinit var rv : RecyclerView
    lateinit var userList: ArrayList<User>
    lateinit var adater: UserAdater
    lateinit var auth: FirebaseAuth
    lateinit var mDbref : DatabaseReference


    override fun onCreate(savedInstanceState: Bundle?) {


        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        userList = ArrayList()
        adater = UserAdater(this , userList)
        auth = FirebaseAuth.getInstance()
        rv = findViewById(R.id.rv)
        mDbref = FirebaseDatabase.getInstance().getReference()
        rv.layoutManager = LinearLayoutManager(this)
        rv.adapter = adater






        // this logic add msg in database
        mDbref.child("User").addValueEventListener(object : ValueEventListener{
            @SuppressLint("NotifyDataSetChanged")
            override fun onDataChange(snapshot: DataSnapshot) {

                userList.clear()
                for (postSnapshot in snapshot.children) {
                    val currentUser =postSnapshot.getValue(User::class.java)
                    if (currentUser != null) {
                        if(auth.currentUser?.uid != currentUser.uid ){
                            userList.add(currentUser!!)

                        }
                    }

                }

                println("user$userList")
                adater.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })


    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        menuInflater.inflate(R.menu.menu,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if (item.itemId == R.id.logout) {
            auth.signOut()
            finish()
            return true
        }

        return true
    }
}