package com.kuss.tangerine.views


import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kuss.tangerine.R
import com.kuss.tangerine.adapter.TaskListAdapter
import com.kuss.tangerine.adapter.DoneTaskListAdapter
import com.kuss.tangerine.db.Task
import com.kuss.tangerine.model.TaskViewModel
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity(){
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
            recyclerView.scheduleLayoutAnimation()
        })
        taskViewModel.doneTasks.observe(this, Observer { tasks ->
            tasks?.let {
                updateDoneRecycler(it)
            }

        })

        fab.setOnClickListener {
            onModal()
        }
    }

    private fun updateDoneRecycler(tasks: List<Task>) {
            val doneRecyclerView = findViewById<RecyclerView>(R.id.doneTasks_recyclerView)
            val doneAdapter = DoneTaskListAdapter(this, taskViewModel)
            doneRecyclerView?.let {drv ->
                drv.layoutManager = LinearLayoutManager(this)
                drv.adapter = doneAdapter
                doneAdapter.setTasks(tasks)
            }
    }
    private fun onModal() {
        val modalBottomSheet = Modal(adapter)
        modalBottomSheet.show(supportFragmentManager, Modal.TAG)
    }
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        taskViewModel.deleteAll()
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }
}

