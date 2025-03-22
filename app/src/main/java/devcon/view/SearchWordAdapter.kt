package devcon.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import devcon.learn.contacts.R

class SearchWordAdapter(
    private val onItemDelete: (String) -> Unit,
    private val onItemClick: (String) -> Unit,
) : ListAdapter<String, SearchWordAdapter.SearchWordHolder>(
        object : DiffUtil.ItemCallback<String>() {
            override fun areItemsTheSame(
                oldItem: String,
                newItem: String,
            ): Boolean = oldItem == newItem

            override fun areContentsTheSame(
                oldItem: String,
                newItem: String,
            ): Boolean = oldItem == newItem
        },
    ) {
    inner class SearchWordHolder(
        itemView: View,
    ) : RecyclerView.ViewHolder(itemView) {
        init {
            itemView.setOnClickListener {
                val place = getItem(bindingAdapterPosition)
                onItemClick(place)
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): SearchWordHolder {
        // MaterialButton을 프로그래밍 방식으로 생성합니다.
        val view =
            LayoutInflater.from(parent.context).inflate(
                R.layout.search_word_item,
                parent,
                false,
            )
        return SearchWordHolder(view)
    }

    override fun onBindViewHolder(
        holder: SearchWordHolder,
        position: Int,
    ) {
        val item = getItem(position)
        val keyword = holder.itemView.findViewById<TextView>(R.id.search_word_keyword)
        val deleteIcon = holder.itemView.findViewById<ImageView>(R.id.delete_icon)

        keyword.text = item
        deleteIcon.setOnClickListener { onItemDelete(item) }
    }
}
