package devcon.map.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import devcon.learn.contacts.R
import devcon.map.abstracts.BaseRecyclerAdapter
import devcon.map.data.SampleData
import kotlinx.coroutines.flow.Flow

class HistoryRecyclerAdapter(
    itemFlow: Flow<List<SampleData>>,
    private val onClick: (id: Long) -> Unit
) : BaseRecyclerAdapter<SampleData, HistoryViewHolder>(itemFlow) {
    override fun areItemsSame(oldItem: SampleData, newItem: SampleData): Boolean {
        return oldItem.id == newItem.id
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.history_recycler_holder, parent, false)
        return HistoryViewHolder(view, { onClick(it) })
    }

    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
        holder.onInit(itemList()[position])
    }

}