package com.example.login

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.login.ViewModel.Message
import com.google.firebase.auth.FirebaseAuth

class MessageAdater(
                    val context: Context ,
                    val messageList: ArrayList<Message>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>()  {

    val SENTCODE = 1
    val RECIEVECODE = 2



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        if (viewType == 1){
            val view : View = LayoutInflater.from(context).inflate(R.layout.sendmsg , parent , false)
            return SentViewHolder(view)
        } else {
            val view : View = LayoutInflater.from(context).inflate(R.layout.recmsg , parent , false)
            return RecieveViewHolder(view)
        }

    }

    override fun getItemCount(): Int {
    return messageList.size

    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        val currentMessage = messageList[position]
        if (holder.javaClass == SentViewHolder::class.java){

            val viewHolder = holder as SentViewHolder
            holder.sentMessage.text = currentMessage.message
        } else{
            val viewHolder = holder as RecieveViewHolder
            holder.recieveMessage.text = currentMessage.message

        }
    }

    override fun getItemViewType(position: Int): Int {
        val currentMessage = messageList[position]





        if (FirebaseAuth.getInstance().currentUser?.uid.equals(currentMessage.senderId)) {
            return SENTCODE
        } else {

            return RECIEVECODE
        }
    }

    class SentViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {

        val sentMessage = itemView.findViewById<TextView>(R.id.sendMsg)

    }

    class RecieveViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {

        val recieveMessage = itemView.findViewById<TextView>(R.id.recMsg)

    }

}