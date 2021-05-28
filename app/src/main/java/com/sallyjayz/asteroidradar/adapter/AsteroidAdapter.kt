package com.sallyjayz.asteroidradar.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.sallyjayz.asteroidradar.databinding.AsteroidItemBinding
import com.sallyjayz.asteroidradar.model.Asteroid


class AsteroidAdapter(private val clickListener: AsteroidListener) :
    ListAdapter<Asteroid, AsteroidAdapter.ViewHolder>(AsteroidDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = getItem(position)
        holder.bind(currentItem, clickListener)
    }

    class ViewHolder private constructor(private val binding: AsteroidItemBinding):
        RecyclerView.ViewHolder(binding.root) {

            fun bind(item: Asteroid, clickListener: AsteroidListener) {

                binding.asteroid =item
                binding.asteroidClickListener = clickListener
                binding.executePendingBindings()

            }

            companion object {
                fun from(parent: ViewGroup): ViewHolder {
                    val layoutInflater = LayoutInflater.from(parent.context)
                    val binding = AsteroidItemBinding.inflate(layoutInflater, parent, false)
                    return ViewHolder(binding)
                }
            }
        }
}

class AsteroidDiffCallback: DiffUtil.ItemCallback<Asteroid>() {
    override fun areItemsTheSame(oldItem: Asteroid, newItem: Asteroid): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Asteroid, newItem: Asteroid): Boolean {
        return oldItem == newItem
    }

}

class AsteroidListener(val clickListener: (asteroid: Asteroid) -> Unit) {
    fun onClick(asteroid: Asteroid) = clickListener(asteroid)
}


