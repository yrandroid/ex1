package com.reiss.yedidya.ex1

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.message_item.view.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {

    private var messages = ArrayList<Message>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        list.layoutManager = LinearLayoutManager(this, LinearLayout.VERTICAL, false)

        refresh()
    }

    private val user = User("The only user here")

    fun sendPressed(view : View) {
        val t = txtMessage.text.trim().toString()
        if (t.isEmpty()) {
            //TODO: present an alert
            toast("Please insert a message before sending")
            return
        }

        toast(t)
        addMessage(t)
    }

    private fun addMessage(text:String) {
        val msg = Message(text,user,Date())
        messages.add(msg)
        refresh()
    }


    private fun dataSource(): ArrayList<Message> {
        val empty = Message("No data yet",User(""),Date())
        val emptyList = ArrayList<Message>()
        emptyList.add(empty)
        return if (messages.isNotEmpty()) messages else emptyList
    }

    private fun refresh() {
        list.adapter = ListAdapter(dataSource())
        list.invalidate()
    }
}




class Message(val message: String, val user: User, val created: Date)
class User(val name: String)


class ListAdapter (val messages: ArrayList<Message>): RecyclerView.Adapter<ListAdapter.ViewHolder>() {

    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
        val m = messages[position]

        val formater = SimpleDateFormat("dd/mm/yyyy hh:MM")

        holder?.itemView?.txtDate?.text = formater.format(m.created)
        holder?.itemView?.txtMessage?.text = m.message
        holder?.itemView?.txtUser?.text = m.user.name
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent?.context).inflate(R.layout.message_item, parent, false)
        return ViewHolder(v);
    }

    override fun getItemCount(): Int {
        return messages.count()
    }

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)

}




