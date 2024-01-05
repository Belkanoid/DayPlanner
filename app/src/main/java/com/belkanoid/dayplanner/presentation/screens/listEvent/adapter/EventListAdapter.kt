package com.belkanoid.dayplanner.presentation.screens.listEvent.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.belkanoid.dayplanner.data.repository.DateConverter.toHHmm
import com.belkanoid.dayplanner.databinding.EventItemBinding
import com.belkanoid.dayplanner.domain.Event


//тут стоило бы по по solid расскидать каждый класс по отдельным файлам, но тут кодa немного
class EventListAdapter :
    ListAdapter<Event, EventListViewHolder>(EventPlannerSlotDiffUtil) {

    var onInnerEventClick: ((Event) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventListViewHolder {
        val itemView = EventItemBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return EventListViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: EventListViewHolder, position: Int) {
        val eventItem = getItem(position)
        holder.apply {
            onBind(eventItem)
            itemView.setOnClickListener { onInnerEventClick?.invoke(eventItem) }
        }
    }
}

class EventListViewHolder(private val binding: EventItemBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun onBind(event: Event) {
        with(binding) {
            tvStartTime.text = event.startTime.toHHmm()
            tvEventName.text = event.name
        }
    }
}


object EventPlannerSlotDiffUtil : DiffUtil.ItemCallback<Event>() {
    override fun areItemsTheSame(oldItem: Event, newItem: Event) =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: Event, newItem: Event) =
        oldItem == newItem
}