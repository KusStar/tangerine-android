package com.kuss.tangerine.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.kuss.tangerine.R
import com.kuss.tangerine.db.Task
import kotlinx.android.synthetic.main.card.view.*
import android.content.Context
import android.graphics.Paint
import com.kuss.tangerine.model.TaskViewModel
import java.util.*
import android.view.View


class TaskListAdapter internal constructor(
    private val context: Context,
    private val taskViewModel: TaskViewModel?
) : RecyclerView.Adapter<TaskListAdapter.TaskViewHolder>() {

    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private var tasks = emptyList<Task>()
    private val viewModel: TaskViewModel? = taskViewModel

    companion object{
        val TYPE_FOOTER = 1//添加Footer
        val TYPE_HEADER = 2//添加Header
        val TYPE_NORMAL = 0//两者都没有添加
        var showHeader = false
        var showFooter = false
    }
    fun setHeaderView(){
        showHeader = true
        notifyItemInserted(0)
    }

    fun setFooterView(){
        showFooter = true
        notifyItemInserted(itemCount - 1)
    }

    inner class TaskViewHolder(val card: View) : RecyclerView.ViewHolder(card)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        if (showHeader && viewType == TYPE_HEADER) {
            val view = inflater.inflate(R.layout.header, parent, false)
            return TaskViewHolder(view)
        }
        if (showFooter  && viewType == TYPE_FOOTER) {
            val view = inflater.inflate(R.layout.footer, parent, false)
            return TaskViewHolder(view)
        }
        val card = inflater.inflate(R.layout.card, parent, false) as CardView
        return TaskViewHolder(card)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        if (getItemViewType(position) == TYPE_HEADER) return
        else if (getItemViewType(position) == TYPE_FOOTER) return
        else {
            val index = if(showHeader == false) position else position - 1
            holder.run {
                card.run {
                    tasks[index].run {
                        textView.text = name
                        checkBox.isChecked = checked
                        if (checked) {
                            textView.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
                        } else {
                            textView.paintFlags = Paint.ANTI_ALIAS_FLAG
                        }
                        imageView.setImageResource(type)
                    }
                    this.checkBox.setOnClickListener {
                        updateTask(tasks[index])
                    }
                }
            }
        }
    }

    private fun deleteTask(task: Task) {
        viewModel!!.delete(task)
    }
    fun insert(task:Task) {
        viewModel!!.insert(task)
    }
    private fun updateTask(task:Task) {
        task.run {
            val newTask = Task(name, type, !checked, Date().time)
            viewModel!!.delete(task)
            viewModel.insert(newTask)
        }
    }
    internal fun setTasks(tasks: List<Task>) {
        this.tasks = tasks
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        if (showHeader == true && showFooter == true)
            return tasks.size + 2
        else if(showHeader == false && showFooter == false)
            return tasks.size
        else
            return tasks.size + 1
    }

    override fun getItemViewType(position: Int): Int {
        if (showHeader == false && showFooter == false)
            return TYPE_NORMAL
        if(position == 0)
            return TYPE_HEADER
        if(position == itemCount-1)
            return TYPE_FOOTER
        return TYPE_NORMAL
    }
}