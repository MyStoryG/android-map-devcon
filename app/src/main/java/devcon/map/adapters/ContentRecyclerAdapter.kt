package devcon.map.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import devcon.learn.contacts.R
import devcon.map.abstracts.BaseRecyclerAdapter
import devcon.map.data.SampleData
import kotlinx.coroutines.flow.Flow

class ContentRecyclerAdapter(
    itemFlow: Flow<List<SampleData>>,
    private val onClick: (data: SampleData) -> Unit
) : BaseRecyclerAdapter<SampleData, ContentViewHolder>(itemFlow) {

    override fun areItemsSame(oldItem: SampleData, newItem: SampleData): Boolean {
        return oldItem.id == newItem.id
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContentViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.content_recycler_holder, parent, false)
        return ContentViewHolder(view, { onClick(it) })
    }

    override fun onBindViewHolder(holder: ContentViewHolder, position: Int) {
        holder.onInit(itemList()[position])
    }

}