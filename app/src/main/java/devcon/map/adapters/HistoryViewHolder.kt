package devcon.map.adapters

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import devcon.learn.contacts.R
import devcon.map.data.SampleData

class HistoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val historyTextView: TextView = itemView.findViewById(R.id.historyTextView)

    fun onInit(data: SampleData) {
        historyTextView.text = data.title
    }
}