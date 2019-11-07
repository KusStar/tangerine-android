package com.kuss.tangerine.views


import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kuss.tangerine.R
import com.kuss.tangerine.adapter.TaskListAdapter
import com.kuss.tangerine.model.TaskViewModel
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity(){
    private lateinit var taskViewModel: TaskViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(bar)

        taskViewModel = ViewModelProvider(this).get(TaskViewModel::class.java)

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        val adapter = TaskListAdapter(this, taskViewModel)

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.itemAnimator = DefaultItemAnimator()
        recyclerView.addItemDecoration(TaskItemDecoration(this))
        recyclerView.adapter = adapter

        taskViewModel = ViewModelProvider(this).get(TaskViewModel::class.java)
        taskViewModel.allTasks.observe(this, Observer { tasks ->
            tasks?.let { adapter.setTasks(it) }
            recyclerView.scheduleLayoutAnimation()
        })
        fab.setOnClickListener {
            onModal()
        }
    }

    private fun onModal() {
        val modalBottomSheet = Modal(viewModel = taskViewModel)
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

