package com.example.project3

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.project3.api.GridItem
import com.example.project3.databinding.ListItemGridBinding

class AstroViewHolder(
    private val binding: ListItemGridBinding
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(gridItem: GridItem, onGridItemClicked: (title: String, description: String, url: String, mediaType: String, date: String) -> Unit) {
        if (gridItem.mediaType == "image") {
            binding.itemImageView.load(gridItem.url)
            binding.titleTextView.text = gridItem.title
        } else if (gridItem.mediaType == "video") {
            binding.itemImageView.setImageResource(R.drawable.placeholder_image)
            binding.titleTextView.text = "${gridItem.title} (Video)"
        }
        binding.root.setOnClickListener {
            onGridItemClicked(gridItem.title, gridItem.description, gridItem.url, gridItem.mediaType, gridItem.date)
        }
    }
}
class AstroListAdapter(
    private val gridItems: List<GridItem>,
    private val onGridItemClicked: (title: String, description: String, url: String, mediaType: String, date: String) -> Unit
) : RecyclerView.Adapter<AstroViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): AstroViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ListItemGridBinding.inflate(inflater, parent, false)
        return AstroViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AstroViewHolder, position: Int) {
        val item = gridItems[position]
        holder.bind(item, onGridItemClicked)
    }

    override fun getItemCount() = gridItems.size
}

