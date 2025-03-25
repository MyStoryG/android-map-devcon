package devcon.map.adapters

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import devcon.learn.contacts.R
import devcon.map.data.SampleData

class HistoryViewHolder(itemView: View, private val onClick: (id: Long) -> Unit) :
    RecyclerView.ViewHolder(itemView) {
    private val historyTextView: TextView = itemView.findViewById(R.id.historyTextView)

    fun onInit(item: SampleData) {
        historyTextView.text = item.title
        itemView.setOnClickListener {
            onClick(item.id)
        }
    }
}