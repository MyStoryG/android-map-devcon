package devcon.map.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import devcon.learn.contacts.R
import devcon.map.data.SampleData

class HistoryRecyclerAdapter() : RecyclerView.Adapter<HistoryViewHolder>() {

    var itemList = emptyList<SampleData>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.history_recycler_holder, parent, false)
        return HistoryViewHolder(view)
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
        holder.onInit(itemList[position])
    }

}