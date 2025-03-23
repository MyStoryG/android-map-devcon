import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import devcon.learn.contacts.databinding.ItemSavedSearchBinding

class MainSearchSavedAdapter(
    private val onItemClick: (String) -> Unit
) :
    ListAdapter<String, MainSearchSavedAdapter.MainSearchSavedAdapterHolder>(
        DiffCallback
    ) {

    companion object {
        val DiffCallback = object : DiffUtil.ItemCallback<String>() {
            override fun areItemsTheSame(
                oldItem: String,
                newItem: String
            ): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: String,
                newItem: String
            ): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MainSearchSavedAdapterHolder {
        val binding =
            ItemSavedSearchBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        return MainSearchSavedAdapterHolder(binding)
    }

    override fun onBindViewHolder(holder: MainSearchSavedAdapterHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class MainSearchSavedAdapterHolder(
        private val binding: ItemSavedSearchBinding
    ) : ViewHolder(binding.root) {

        fun bind(item: String) {
            binding.tvTitle.text = item
            binding.root.setOnClickListener {
                onItemClick(item)
            }
        }
    }
}