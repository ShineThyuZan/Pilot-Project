package com.galaxy_techno.uKnow.movie

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.galaxy_techno.uKnow.R
import com.galaxy_techno.uKnow.common.Endpoint
import com.galaxy_techno.uKnow.databinding.ItemListBinding

class MoviePagingDataAdapter(
    private val isDark: Boolean,
    private val onClick: (Int) -> Unit
) : PagingDataAdapter<Movie, RecyclerView.ViewHolder>(MovieComparator) {

    fun getClickItem(position: Int): Movie? = getItem(position)

    object MovieComparator : DiffUtil.ItemCallback<Movie>() {
        override fun areItemsTheSame(oldItem: Movie, newItem: Movie) =
            oldItem.title == newItem.title

        override fun areContentsTheSame(oldItem: Movie, newItem: Movie) = oldItem == newItem
    }

    inner class MovieViewHolder(private val binding: ItemListBinding) :
        RecyclerView.ViewHolder(binding.root) {

        private val placeholder =
            if (isDark) R.drawable.placeholder_dark else R.drawable.place_holder

        fun bind(item: Movie?) = with(binding) {

            item?.let {

                if (it.vote_average >= 5.0) {
                    this.tvStatus.text = binding.root.context.getString(R.string.order_pending)
                    this.tvStatus.background = ContextCompat.getDrawable(
                        binding.root.context,
                        R.drawable.background_item_status_pending
                    )
                }
                if (it.vote_average in 4.0..5.0) {
                    this.tvStatus.text = binding.root.context.getString(R.string.order_processing)
                    this.tvStatus.background = ContextCompat.getDrawable(
                        binding.root.context,
                        R.drawable.background_item_status_processing
                    )
                }
                if (it.vote_average in 3.0..4.0) {
                    this.tvStatus.text = binding.root.context.getString(R.string.order_delivered)
                    this.tvStatus.background = ContextCompat.getDrawable(
                        binding.root.context,
                        R.drawable.background_item_status_delivered
                    )
                }
                if (it.vote_average in 2.0..3.0) {
                    this.tvStatus.text = binding.root.context.getString(R.string.order_refunded)
                    this.tvStatus.background = ContextCompat.getDrawable(
                        binding.root.context,
                        R.drawable.background_item_status_refunded
                    )
                }
                if (it.vote_average in 1.0..2.0) {
                    this.tvStatus.text = binding.root.context.getString(R.string.order_refunding)
                    this.tvStatus.background = ContextCompat.getDrawable(
                        binding.root.context,
                        R.drawable.background_item_status_refunding
                    )
                }
                if (it.vote_average in 0.0..1.0) {
                    this.tvStatus.text = binding.root.context.getString(R.string.order_completed)
                    this.tvStatus.background = ContextCompat.getDrawable(
                        binding.root.context,
                        R.drawable.background_item_status_completed
                    )
                }



                this.tvTitle.text = item.title
                this.tvOrderCount.text = item.vote_average.toString()
                this.tvTotalPrice.text = item.vote_count.toString()
                Glide.with(itemView.context)
                    .load(Endpoint.IMAGE_URL + item.backdrop_path)
                    .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                    .placeholder(placeholder)
                    .into(this.img)
            }
            itemView.setOnClickListener { onClick(absoluteAdapterPosition) }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as MovieViewHolder).bind(getItem(position))
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = ItemListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieViewHolder(binding)
    }
}