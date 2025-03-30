package devcon.map.adapters

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import devcon.learn.contacts.R
import devcon.map.restapi.KeywordDocument

class HistoryViewHolder(itemView: View, private val onClick: (data: KeywordDocument) -> Unit) :
    RecyclerView.ViewHolder(itemView) {
    private val historyTextView: TextView = itemView.findViewById(R.id.historyTextView)

    fun onInit(item: KeywordDocument) {
        historyTextView.text = item.place_name
        itemView.setOnClickListener {
            onClick(item)
        }
    }
}