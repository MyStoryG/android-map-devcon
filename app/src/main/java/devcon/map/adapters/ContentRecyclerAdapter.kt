package devcon.map.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import devcon.learn.contacts.R
import devcon.map.data.SampleData

class ContentRecyclerAdapter() : RecyclerView.Adapter<ContentViewHolder>() {

    var itemList = emptyList<SampleData>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContentViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.content_recycler_holder, parent, false)
        return ContentViewHolder(view)
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    override fun onBindViewHolder(holder: ContentViewHolder, position: Int) {
        holder.onInit(itemList[position])
    }
}