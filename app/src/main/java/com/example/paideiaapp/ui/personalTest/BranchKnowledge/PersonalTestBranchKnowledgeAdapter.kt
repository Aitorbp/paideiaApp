package com.example.paideiaapp.ui.personalTest.BranchKnowledge


import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.paideiaapp.R
import com.example.paideiaapp.commons.basicDiffUtil
import com.example.paideiaapp.commons.inflate
import com.example.paideiaapp.databinding.ListItemBinding
import com.example.paideiaapp.models.appModels.BranchKnowledge


class PersonalTestBranchKnowledgeAdapter(private val listener: ((BranchKnowledge) -> Unit)) :
    ListAdapter<BranchKnowledge, PersonalTestBranchKnowledgeAdapter.ViewHolder>(
        basicDiffUtil { old, new -> old.branch == new.branch }
    ) {
    val selectedItems = ArrayList<BranchKnowledge>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = parent.inflate(R.layout.list_item, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val branch = getItem(position)
        holder.bind(branch)
        holder.bindSelection(branch)

        holder.itemView.setOnClickListener {
            if (selectedItems.size == 2 && !selectedItems.contains(branch)) {
                selectedItems.removeAt(0) // Deselecciona el primer elemento seleccionado
            }
            toggleSelection(branch)
            holder.bindSelection(branch)
            listener(branch)
        }
    }


    private fun toggleSelection(branch: BranchKnowledge) {
        if (selectedItems.contains(branch)) {
            selectedItems.remove(branch)
        } else {
            if (selectedItems.size == 2) {
                selectedItems.removeAt(0)
            }
            selectedItems.add(branch)
        }
        notifyDataSetChanged()
    }

    fun getSelectedItems(): List<BranchKnowledge> {
        return selectedItems.toList()
    }



    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int,
        payloads: MutableList<Any>
    ) {
        if (payloads.isNotEmpty()) {
            val branch = getItem(position)
            holder.bindSelection(branch)
        } else {
            super.onBindViewHolder(holder, position, payloads)
        }
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val binding = ListItemBinding.bind(view)
        private val originalBackground = view.background
        private val selectedBackground = ContextCompat.getDrawable(view.context, R.drawable.custom_component_selected)

        fun bind(branchKnowledge: BranchKnowledge) {
            binding.branchImage.setImageResource(branchKnowledge.icon)
            binding.branchTitle.text = branchKnowledge.branch
        }

        fun bindSelection(branchKnowledge: BranchKnowledge) {
            if (selectedItems.contains(branchKnowledge)) {
                itemView.background = selectedBackground
            } else {
                itemView.background = originalBackground
            }
        //   /* if (branchKnowledge.selected){
       //         itemView.background = selectedBackground
        //    }
        //    */
        }
    }
}


