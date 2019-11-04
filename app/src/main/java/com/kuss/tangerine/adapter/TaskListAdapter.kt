package com.kuss.tangerine.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.kuss.tangerine.R
import com.kuss.tangerine.db.Task
import kotlinx.android.synthetic.main.card.view.*
import android.content.Context
import com.kuss.tangerine.model.TaskViewModel
import java.util.*


class TaskListAdapter internal constructor(
    context: Context,
    private val taskViewModel: TaskViewModel?
) : RecyclerView.Adapter<TaskListAdapter.TaskViewHolder>() {

    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private var tasks = emptyList<Task>()
    private val viewModel: TaskViewModel? = taskViewModel

    inner class TaskViewHolder(val card: CardView) : RecyclerView.ViewHolder(card)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val card = inflater.inflate(R.layout.card, parent, false) as CardView
        return TaskViewHolder(card)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, index: Int) {
        holder.run {
            card.run {
                tasks[index].run {
                    textView.text = name
                    checkBox.isChecked = checked
                    imageView.setImageResource(type)
                }
                this.checkBox.setOnClickListener {
                    updateTask(tasks[index])
                }
                this.setOnClickListener{
                    //                    viewModel!!.delete(tasks[index])
                    updateTask(tasks[index])

                }
            }
        }
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

    override fun getItemCount() = tasks.size
}