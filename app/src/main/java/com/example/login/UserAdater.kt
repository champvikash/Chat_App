package com.example.login

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.login.ViewModel.User
import com.google.firebase.auth.FirebaseAuth

class UserAdater( private val  context: Context , val userList: ArrayList<User> ) : RecyclerView.Adapter<UserAdater.UserViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {

     val view : View = LayoutInflater.from(context).inflate(R.layout.userlayout , parent , false)
        return UserViewHolder(view)

    }

    override fun getItemCount(): Int {

        return userList.size
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {

        val currentName = userList[position]

        holder.textName.text = currentName.name

        holder.itemView.setOnClickListener {

            val i = Intent(context , ChatActivity::class.java)
            i.putExtra("uid" , currentName.uid)
            i.putExtra("name" , currentName.name)
            context.startActivity(i)
        }
    }

    class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    val textName = itemView.findViewById<TextView>(R.id.userText)


    }

}