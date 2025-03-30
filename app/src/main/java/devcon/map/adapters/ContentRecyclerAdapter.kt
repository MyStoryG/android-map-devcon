package devcon.map.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import devcon.learn.contacts.R
import devcon.map.abstracts.BaseRecyclerAdapter
import devcon.map.restapi.KeywordDocument
import kotlinx.coroutines.flow.Flow

class ContentRecyclerAdapter(
    itemFlow: Flow<List<KeywordDocument>>,
    private val onClick: (data: KeywordDocument) -> Unit
) : BaseRecyclerAdapter<KeywordDocument, ContentViewHolder>(itemFlow) {

    override fun areItemsSame(oldItem: KeywordDocument, newItem: KeywordDocument): Boolean {
        return (oldItem.place_name == newItem.place_name) && (oldItem.address_name == newItem.address_name)
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