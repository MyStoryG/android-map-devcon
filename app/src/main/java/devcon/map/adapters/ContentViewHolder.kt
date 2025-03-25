package devcon.map.adapters

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import devcon.learn.contacts.R
import devcon.map.data.SampleData

class ContentViewHolder(itemView: View, private val onClick: (data: SampleData) -> Unit) :
    RecyclerView.ViewHolder(itemView) {
    //  private val contentImageView: ImageView = itemView.findViewById(R.id.contentImageView)
    private val contentTextView: TextView = itemView.findViewById(R.id.contentTextView)

    fun onInit(item: SampleData) {
        contentTextView.text = item.title
        itemView.setOnClickListener {
            onClick(item)
        }
    }
}