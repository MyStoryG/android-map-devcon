package devcon.map.feature

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil.ItemCallback
import androidx.recyclerview.widget.ListAdapter
import devcon.map.databinding.ItemKeywordBinding
import devcon.map.model.Keyword
import devcon.map.ui.BaseViewHolder

class KeywordAdapter(
    private val onItemClick: (Keyword) -> Unit,
    private val onItemDelete: (Keyword) -> Unit,
) : ListAdapter<Keyword, KeywordViewHolder>(KeywordDiffCallback()) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): KeywordViewHolder {
        val binding = ItemKeywordBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return KeywordViewHolder(binding, onItemClick, onItemDelete)
    }

    override fun onBindViewHolder(
        holder: KeywordViewHolder,
        position: Int,
    ) {
        holder.onBind(getItem(position))
    }
}

class KeywordViewHolder(
    private val binding: ItemKeywordBinding,
    private val onItemClick: (Keyword) -> Unit,
    private val onItemDelete: (Keyword) -> Unit,
) : BaseViewHolder<Keyword>(binding) {
    override fun onBind(item: Keyword) {
        binding.chipKeyword.apply {
            text = item.word
            setOnClickListener { onItemClick(item) }
            setOnCloseIconClickListener { onItemDelete(item) }
        }
    }
}

private class KeywordDiffCallback : ItemCallback<Keyword>() {
    override fun areItemsTheSame(
        oldItem: Keyword,
        newItem: Keyword,
    ): Boolean = oldItem.id == newItem.id

    override fun areContentsTheSame(
        oldItem: Keyword,
        newItem: Keyword,
    ): Boolean = oldItem == newItem
}
