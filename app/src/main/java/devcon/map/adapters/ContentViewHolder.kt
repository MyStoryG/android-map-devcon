package devcon.map.adapters

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import devcon.learn.contacts.R
import devcon.map.restapi.KeywordDocument

class ContentViewHolder(itemView: View, private val onClick: (data: KeywordDocument) -> Unit) :
    RecyclerView.ViewHolder(itemView) {
    //  private val contentImageView: ImageView = itemView.findViewById(R.id.contentImageView)
    private val contentTextView: TextView = itemView.findViewById(R.id.contentTextView)

    fun onInit(item: KeywordDocument) {
        contentTextView.text = item.place_name
        itemView.setOnClickListener {
            onClick(item)
        }
    }
}