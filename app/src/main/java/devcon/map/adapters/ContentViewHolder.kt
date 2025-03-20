package devcon.map.adapters

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import devcon.learn.contacts.R
import devcon.map.data.SampleData

class ContentViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val contentImageView: ImageView = view.findViewById(R.id.contentImageView)
    val contentTextView: TextView = view.findViewById(R.id.contentTextView)

    fun onInit(data: SampleData) {
        contentTextView.text = data.title

    }
}