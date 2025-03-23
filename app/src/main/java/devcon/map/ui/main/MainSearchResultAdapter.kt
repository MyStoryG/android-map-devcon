package devcon.map.ui.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import devcon.learn.contacts.databinding.ItemSearchResultBinding
import devcon.map.data.model.CafeDTO

class MainSearchResultAdapter(
    private val onItemClick: (String) -> Unit
) :
    ListAdapter<CafeDTO, MainSearchResultAdapter.MainSearchResultAdapterHolder>(
        DiffCallback
    ) {

    companion object {
        val DiffCallback = object : DiffUtil.ItemCallback<CafeDTO>() {
            override fun areItemsTheSame(
                oldItem: CafeDTO,
                newItem: CafeDTO
            ): Boolean {
                return oldItem.title == newItem.title
            }

            override fun areContentsTheSame(
                oldItem: CafeDTO,
                newItem: CafeDTO
            ): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MainSearchResultAdapterHolder {
        val binding =
            ItemSearchResultBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        return MainSearchResultAdapterHolder(binding)
    }

    override fun onBindViewHolder(holder: MainSearchResultAdapterHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class MainSearchResultAdapterHolder(
        private val binding: ItemSearchResultBinding
    ) : ViewHolder(binding.root) {

        fun bind(item: CafeDTO) {
            binding.tvTitle.text = item.title
            binding.tvAddress.text = item.address
            binding.root.setOnClickListener {
                onItemClick(item.title)
            }
        }
    }
}