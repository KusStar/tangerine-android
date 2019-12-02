package com.kuss.tangerine.views

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kuss.tangerine.R
import com.kuss.tangerine.adapters.TaskListAdapter
import com.kuss.tangerine.adapters.DoneTaskListAdapter
import com.kuss.tangerine.db.Task
import com.kuss.tangerine.model.TaskViewModel
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    private lateinit var taskViewModel: TaskViewModel
    private lateinit var adapter: TaskListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(bar)

        taskViewModel = ViewModelProvider(this).get(TaskViewModel::class.java)

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        adapter = TaskListAdapter(this, taskViewModel)

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.addItemDecoration(TaskItemDecoration(this))
        recyclerView.adapter = adapter

        adapter.setHeaderView()
        adapter.setFooterView()

        taskViewModel = ViewModelProvider(this).get(TaskViewModel::class.java)

        taskViewModel.unDoneTasks.observe(this, Observer { tasks ->
            tasks?.let { adapter.setTasks(it) }
        })
        taskViewModel.doneTasks.observe(this, Observer { tasks ->
            tasks?.let {
                updateDoneRecycler(it)
            }
        })

        fab.setOnClickListener {
            onAddTaskModal()
        }
    }

    private fun updateDoneRecycler(tasks: List<Task>) {
        val doneRecyclerView = findViewById<RecyclerView>(R.id.doneRecyclerView)
        val doneAdapter = DoneTaskListAdapter(this, taskViewModel)
        doneRecyclerView?.let { drv ->
            drv.layoutManager = LinearLayoutManager(this)
            drv.adapter = doneAdapter
            doneAdapter.setTasks(tasks)
        }
    }

    private fun onAddTaskModal() {
        val modalBottomSheet = AddTaskModal(adapter)
        modalBottomSheet.show(supportFragmentManager, AddTaskModal.TAG)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_settings -> true
            R.id.action_remove_done -> {
                taskViewModel.deleteDoneTasks()
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}

