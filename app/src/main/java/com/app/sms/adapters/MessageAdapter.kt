package com.app.sms.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.app.sms.data.DataItem
import com.app.sms.databinding.ListItemDateBinding
import com.app.sms.databinding.ListItemMessageBinding
import com.app.sms.models.MessageItem


class MessageAdapter(private val listener: ClickListener) : PagingDataAdapter<DataItem, RecyclerView.ViewHolder>(DiffCallback) {

    private  val VIEW_MESSAGE = 0
    private  val VIEW_DATE = 1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {

            VIEW_MESSAGE -> MessageViewHolder.from(parent)
            VIEW_DATE -> DateViewHolder.from(parent)
            else -> throw ClassCastException("Unknown viewType $viewType")
        }
    }

    class MessageViewHolder private constructor(private val binding: ListItemMessageBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(msg: MessageItem, listener: ClickListener) {
            binding.message = msg
            binding.clickListener = listener
            binding.executePendingBindings()
        }
        companion object {
            fun from(parent: ViewGroup): MessageViewHolder {
                val layoutInflater by lazy { LayoutInflater.from(parent.context) }
                val binding by lazy { ListItemMessageBinding.inflate(layoutInflater, parent, false) }
                return MessageViewHolder(binding)
            }
        }
    }

    class DateViewHolder private constructor(private val binding: ListItemDateBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(time: String) {
            binding.time = time
            binding.executePendingBindings()
        }
        companion object {
            fun from(parent: ViewGroup): DateViewHolder {
                val layoutInflater by lazy { LayoutInflater.from(parent.context) }
                val binding by lazy { ListItemDateBinding.inflate(layoutInflater, parent, false) }
                return DateViewHolder(binding)
            }
        }
    }

    companion object DiffCallback : DiffUtil.ItemCallback<DataItem>() {

        override fun areItemsTheSame(oldItem: DataItem, newItem: DataItem): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: DataItem, newItem: DataItem): Boolean {
            return oldItem.id == newItem.id
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        when(holder) {

            is MessageViewHolder -> {
                val item = getItem(position) as DataItem.ItemMessage
                holder.bind(item.message,listener)
            }
            is DateViewHolder -> {
                val item = getItem(position) as DataItem.ItemDate
                holder.bind(item.date)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {

        return  when(getItem(position)){

            is DataItem.ItemDate -> VIEW_DATE
            is DataItem.ItemMessage -> VIEW_MESSAGE
            else -> VIEW_MESSAGE
        }
    }
    class ClickListener(val clickListener: (view: View, message: MessageItem) -> Unit) {
        fun onClick(view: View, message: MessageItem) = clickListener(view, message)
    }

}
