package com.kuss.tangerine.adapters

import android.content.Context
import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.kuss.tangerine.R
import com.kuss.tangerine.db.Task
import com.kuss.tangerine.model.TaskViewModel
import kotlinx.android.synthetic.main.card.view.*
import java.util.*


class DoneTaskListAdapter internal constructor(
    private val context: Context,
    private val taskViewModel: TaskViewModel?
) : RecyclerView.Adapter<DoneTaskListAdapter.TaskViewHolder>() {

    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private var tasks = emptyList<Task>()
    private val viewModel: TaskViewModel? = taskViewModel

    inner class TaskViewHolder(val card: View) : RecyclerView.ViewHolder(card)

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
                this.setOnClickListener {
                    deleteTask(tasks[index])
                }

            }
        }
    }

    private fun deleteTask(task: Task) {
        viewModel!!.delete(task)
    }

    private fun updateTask(task: Task) {
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
        return tasks.size
    }
}