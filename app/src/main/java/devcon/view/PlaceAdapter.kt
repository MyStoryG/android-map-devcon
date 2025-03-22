package devcon.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import devcon.domain.Place
import devcon.learn.contacts.R

class PlaceAdapter(
    private val onItemClick: (Place) -> Unit,
) : ListAdapter<Place, PlaceAdapter.PlaceHolder>(
        object : DiffUtil.ItemCallback<Place>() {
            override fun areItemsTheSame(
                oldItem: Place,
                newItem: Place,
            ): Boolean = oldItem.id == newItem.id

            override fun areContentsTheSame(
                oldItem: Place,
                newItem: Place,
            ): Boolean = oldItem == newItem
        },
    ) {
    inner class PlaceHolder(
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
    ): PlaceHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(
                R.layout.place_item,
                parent,
                false,
            )
        return PlaceHolder(view)
    }

    override fun onBindViewHolder(
        holder: PlaceHolder,
        position: Int,
    ) {
        val item = getItem(position)
        val title = holder.itemView.findViewById<TextView>(R.id.place_title)
        val address = holder.itemView.findViewById<TextView>(R.id.place_address)
        val type = holder.itemView.findViewById<TextView>(R.id.place_type)

        title.text = item.name
        address.text = item.address
        type.text = item.type
    }
}
