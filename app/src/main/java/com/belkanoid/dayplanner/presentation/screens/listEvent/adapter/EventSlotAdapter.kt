package com.belkanoid.dayplanner.presentation.screens.listEvent.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.belkanoid.dayplanner.databinding.EventListBinding
import com.belkanoid.dayplanner.domain.Event
import com.belkanoid.dayplanner.domain.TimeSlot

//тут стоило бы по по solid расскидать каждый класс по отдельным файлам, но тут кодa немного
class EventSlotAdapter : ListAdapter<TimeSlot, EventSlotAdapter.EventSlotViewHolder>(EventSlotDiffUtil) {

    var onEventClick: ((Event) -> Unit)? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventSlotViewHolder {
        val itemView = EventListBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return EventSlotViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: EventSlotViewHolder, position: Int) {
        val timeSlot = getItem(position)
        holder.onBind(timeSlot)
    }

    inner class EventSlotViewHolder(private val binding: EventListBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun onBind(timeSlot: TimeSlot) {
            val slotAdapter = EventListAdapter()
            slotAdapter.onInnerEventClick = onEventClick
            with(binding) {
                tvInteravlTime.text = timeSlot.interval
                rvEventList.adapter = slotAdapter
            }
            slotAdapter.submitList(timeSlot.events)
        }
    }
}



object EventSlotDiffUtil: DiffUtil.ItemCallback<TimeSlot>() {
    override fun areItemsTheSame(oldItem: TimeSlot, newItem: TimeSlot) =
        oldItem.interval == newItem.interval

    override fun areContentsTheSame(oldItem: TimeSlot, newItem: TimeSlot) =
        oldItem.events == newItem.events

}
