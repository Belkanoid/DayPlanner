package com.belkanoid.dayplanner.presentation.screens.eventPlanner.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.belkanoid.dayplanner.data.repository.DateConverter.toLocalDateTime
import com.belkanoid.dayplanner.databinding.EventItemBinding
import com.belkanoid.dayplanner.domain.Event
import java.time.format.DateTimeFormatter


//тут стоило бы по по solid расскидать каждый класс по отдельным файлам, но тут код немного
class EventListAdapter :
    ListAdapter<Event, EventListViewHolder>(EventPlannerSlotDiffUtil) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventListViewHolder {
        val itemView = EventItemBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return EventListViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: EventListViewHolder, position: Int) {
        val eventItem = getItem(position)
        holder.onBind(eventItem)
    }
}

class EventListViewHolder(private val binding: EventItemBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun onBind(event: Event) {
        with(binding) {
            tvStartTime.text = event.startTime.toLocalDateTime().format(DateTimeFormatter.ofPattern("hh:mm"))
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