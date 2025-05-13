package com.dicoding.todoapp.ui.detail

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.dicoding.todoapp.R
import com.dicoding.todoapp.data.Task
import com.dicoding.todoapp.ui.ViewModelFactory
import com.dicoding.todoapp.utils.DateConverter
import com.dicoding.todoapp.utils.TASK_ID
import com.google.android.material.textfield.TextInputEditText

class DetailTaskActivity : AppCompatActivity() {

    private lateinit var detailTaskViewModel: DetailTaskViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_task_detail)

        //TODO 11 : Show detail task and implement delete action
        val factory = ViewModelFactory.getInstance(this)
        detailTaskViewModel = ViewModelProvider(this, factory)[DetailTaskViewModel::class.java]

        val taskId = intent.getIntExtra(TASK_ID,-1)

        if (taskId != -1){
            detailTaskViewModel.setTaskId(taskId)
        }else{
            finish()
            return
        }

        observeTaskDetails()

        findViewById<Button>(R.id.btn_delete_task).setOnClickListener {
            deleteTask()
        }

    }

    private fun observeTaskDetails(){
        detailTaskViewModel.task.observe(this){ task ->
            task?.let {
                populateTaskDetails(it)
            }

        }
    }

    private fun populateTaskDetails(task: Task) {
        findViewById<TextInputEditText>(R.id.detail_ed_title).setText(task.title)
        findViewById<TextInputEditText>(R.id.detail_ed_description).setText(task.description)
        val date = DateConverter.convertMillisToString(task.dueDateMillis)
        findViewById<TextInputEditText>(R.id.detail_ed_due_date).setText(date)
    }

    private fun deleteTask() {
        detailTaskViewModel.task.value?.let {
            clearTaskDetails()
            detailTaskViewModel.deleteTask()
            finish()
        }
    }

    private fun clearTaskDetails() {
        findViewById<TextInputEditText>(R.id.detail_ed_title).setText("")
        findViewById<TextInputEditText>(R.id.detail_ed_description).setText("")
        findViewById<TextInputEditText>(R.id.detail_ed_due_date).setText("")
    }
}

