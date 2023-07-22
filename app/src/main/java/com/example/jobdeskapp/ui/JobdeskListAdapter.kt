package com.example.jobdeskapp.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.jobdeskapp.R
import com.example.jobdeskapp.model.Jobdesk

class JobdeskListAdapter(
    private val onItemClickListener: (Jobdesk) -> Unit
): ListAdapter<Jobdesk, JobdeskListAdapter.JobdeskViewModel>(WORDS_COMPARATOR) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): JobdeskViewModel {
        return JobdeskViewModel.create(parent)
    }

    override fun onBindViewHolder(holder: JobdeskViewModel, position: Int) {
        val jobdesk = getItem(position)
        holder.bind(jobdesk)
        holder.itemView.setOnClickListener {
            onItemClickListener(jobdesk)
        }
    }

    class JobdeskViewModel (itemView: View): RecyclerView.ViewHolder(itemView) {
        private val nametextview: TextView = itemView.findViewById(R.id.nametextview)
        private val addrestextview: TextView = itemView.findViewById(R.id.addresstextview)
        private val phonenumbertextview: TextView = itemView.findViewById(R.id.phonenumbertextview)

        fun bind(jobdesk: Jobdesk?) {
            nametextview.text = jobdesk?.name
            addrestextview.text = jobdesk?.address
            phonenumbertextview.text = jobdesk?.phonenumber

        }

        companion object {
            fun create(parent: ViewGroup): JobdeskListAdapter.JobdeskViewModel {
                val view: View = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_jobdesk, parent, false)
                return JobdeskViewModel(view)
            }
        }
    }
    companion object{
        private val WORDS_COMPARATOR = object : DiffUtil.ItemCallback<Jobdesk>(){
            override fun areItemsTheSame(oldItem: Jobdesk, newItem: Jobdesk): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: Jobdesk, newItem: Jobdesk): Boolean {
                return oldItem.id == newItem.id
            }
        }
    }
}
