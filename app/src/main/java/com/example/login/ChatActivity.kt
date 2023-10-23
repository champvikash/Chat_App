package com.example.login

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.login.ViewModel.Message
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.getValue

class ChatActivity : AppCompatActivity() {

    lateinit var recyclerView : RecyclerView
    lateinit var sendBtn : ImageView
    lateinit var msgBox : EditText
    lateinit var msgList: ArrayList<Message>
    lateinit var msgAdater: MessageAdater

    var recieverRoom : String? = null
    var senderRoom : String? = null
    lateinit var  mdRef : DatabaseReference


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        var recieverUid  = intent.getStringExtra("uid").toString()
        var name  = intent.getStringExtra("name").toString()
        mdRef = FirebaseDatabase.getInstance().getReference()

        val senderUid = FirebaseAuth.getInstance().uid
        senderRoom = recieverUid + senderUid

        recieverRoom  = senderUid + recieverUid


        recyclerView = findViewById(R.id.rv)
        sendBtn = findViewById(R.id.send)
        msgBox = findViewById(R.id.edText)
        msgList = ArrayList()


        msgAdater = MessageAdater(this , msgList)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = msgAdater


        //this logic show the msg on UI from database
        mdRef.child("chats").child(senderRoom!!).child("message")
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    msgList.clear()
                    for(postSnapshot in snapshot.children) {

                        val message = postSnapshot.getValue(Message::class.java)
                        msgList.add(message!!)

                    }

                    msgAdater.notifyDataSetChanged()
                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }

            })






        sendBtn.setOnClickListener {

            val msg =  msgBox.text.toString()
            val msgObject = senderUid?.let { it1 -> Message(msg , it1) }

            mdRef.child("chats").child(senderRoom!!).child("message").push()
                .setValue(msgObject).addOnSuccessListener {
                    mdRef.child("chats").child(recieverRoom!!).child("message").push()
                        .setValue(msgObject)
                }

            msgBox.setText("")
        }


    }
}