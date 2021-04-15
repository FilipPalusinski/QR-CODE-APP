package com.example.skanerqrkodw.fragments.history.recyclerview

import android.app.Application
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.RecyclerView
import com.example.skanerqrkodw.R
import com.example.skanerqrkodw.fragments.history.database.History
import com.example.skanerqrkodw.fragments.history.database.HistoryDao
import com.example.skanerqrkodw.fragments.history.database.HistoryDatabase
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.Date.from
import javax.sql.DataSource

class HistoryAdapter(val database: HistoryDao, application: Application): RecyclerView.Adapter<HistoryAdapter.ViewHolder>() {

    var data =  listOf<History>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = data[position]
        holder.textField.text = item.scannedString.toString()
        holder.deleteButton.setOnClickListener {

            GlobalScope.launch {
                database.deleteById(item.id)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.text_item_view, parent, false)
        return ViewHolder(view)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val textField: TextView = itemView.findViewById(R.id.textView3)
        val deleteButton: TextView = itemView.findViewById(R.id.delete)
    }

}

