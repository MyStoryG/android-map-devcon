package devcon.map.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil.ItemCallback
import androidx.recyclerview.widget.ListAdapter
import devcon.learn.contacts.databinding.ItemPlaceBinding
import devcon.map.model.Place

class PlaceAdapter(
    private val onItemClick: (Place) -> Unit,
) : ListAdapter<Place, PlaceViewHolder>(PlaceDiffCallback()) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): PlaceViewHolder {
        val binding = ItemPlaceBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return PlaceViewHolder(binding, onItemClick)
    }

    override fun onBindViewHolder(
        holder: PlaceViewHolder,
        position: Int,
    ) {
        holder.onBind(getItem(position))
    }
}

class PlaceViewHolder(
    private val binding: ItemPlaceBinding,
    private val onItemClick: (Place) -> Unit,
) : BaseViewHolder<Place>(binding) {
    override fun onBind(item: Place) {
        binding.apply {
            textviewName.text = item.name
            textviewCategory.text = item.category
            textviewAddress.text = item.address
            layoutItem.setOnClickListener { onItemClick(item) }
        }
    }
}

private class PlaceDiffCallback : ItemCallback<Place>() {
    override fun areItemsTheSame(
        oldItem: Place,
        newItem: Place,
    ): Boolean = oldItem.id == newItem.id

    override fun areContentsTheSame(
        oldItem: Place,
        newItem: Place,
    ): Boolean = oldItem == newItem
}
