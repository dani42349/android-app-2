package com.example.app20242

import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.textfield.TextInputEditText

class ToDoListActivity : AppCompatActivity() {

    private lateinit var recyclerViewTasks: RecyclerView
    private lateinit var tasksAdapter: TasksAdapter
    private lateinit var tasksList: MutableList<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_to_do_list)


        recyclerViewTasks = findViewById(R.id.recyclerViewTasks)
        tasksList = mutableListOf()
        tasksAdapter = TasksAdapter(tasksList)
        recyclerViewTasks.adapter = tasksAdapter
        recyclerViewTasks.layoutManager = LinearLayoutManager(this)


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


        val buttonAddTask = findViewById<Button>(R.id.buttonAddTask)
        buttonAddTask.setOnClickListener {
            showAddTaskDialog()
        }


        val buttonInstructions = findViewById<Button>(R.id.buttonInstructions)
        buttonInstructions.setOnClickListener {
            showInstructionsDialog()
        }


        val buttonClearTasks = findViewById<Button>(R.id.buttonClearTasks)
        buttonClearTasks.setOnClickListener {
            clearTasks()
        }
    }

    private fun showAddTaskDialog() {
        val dialogView = layoutInflater.inflate(R.layout.dialog_add_task, null)
        val editTextTaskName = dialogView.findViewById<TextInputEditText>(R.id.editTextTaskName)

        MaterialAlertDialogBuilder(this)
            .setTitle("Add New Task")
            .setView(dialogView)
            .setPositiveButton("Add") { dialog, which ->
                val taskName = editTextTaskName.text.toString()
                addTask(taskName)
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    private fun showInstructionsDialog() {
        MaterialAlertDialogBuilder(this)
            .setTitle("Instructions")
            .setMessage("Add tasks by clicking 'Add Task'. Clear tasks using the 'Clear Tasks' button.")
            .setPositiveButton("OK", null)
            .show()
    }

    private fun addTask(taskName: String) {
        tasksList.add(taskName)
        tasksAdapter.notifyItemInserted(tasksList.size - 1)
    }

    private fun clearTasks() {
        tasksList.clear()
        tasksAdapter.notifyDataSetChanged()
    }
}
