package devcon.map.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil.ItemCallback
import androidx.recyclerview.widget.ListAdapter
import devcon.learn.contacts.databinding.ItemKeywordBinding
import devcon.map.model.Keyword

class KeywordAdapter(
    private val onItemDelete: (Keyword) -> Unit,
) : ListAdapter<Keyword, KeywordViewHolder>(KeywordDiffCallback()) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): KeywordViewHolder {
        val binding = ItemKeywordBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return KeywordViewHolder(binding, onItemDelete)
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
    private val onItemDelete: (Keyword) -> Unit,
) : BaseViewHolder<Keyword>(binding) {
    override fun onBind(item: Keyword) {
        binding.apply {
            textviewKeyword.text = item.word
            buttonDelete.setOnClickListener { onItemDelete(item) }
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
