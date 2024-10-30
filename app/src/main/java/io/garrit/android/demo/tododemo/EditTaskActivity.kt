package io.garrit.android.demo.tododemo

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Alignment

class EditTaskActivity : ComponentActivity() {
    private lateinit var task: Task

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val title = intent.getStringExtra("task_title") ?: ""
        val description = intent.getStringExtra("task_description") ?: ""

        task = Task(title, description)

        setContent {
            EditTaskScreen(task)
        }
    }

    @Composable
    fun EditTaskScreen(task: Task) {
        var newTitle by remember { mutableStateOf(task.title) }
        var newDescription by remember { mutableStateOf(task.description) }

        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxSize()
        ) {
            OutlinedTextField(
                value = newTitle,
                onValueChange = { newTitle = it },
                label = { Text("Title") },
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = newDescription,
                onValueChange = { newDescription = it },
                label = { Text("Description") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp)
                    .weight(1f)
            )

            Spacer(modifier = Modifier.weight(0.1f))

            Button(
                onClick = {
                    val intent = Intent().apply {
                        putExtra("task_title", newTitle)
                        putExtra("task_description", newDescription)
                    }
                    setResult(RESULT_OK, intent)
                    finish()
                    },
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(top = 16.dp)
            ){
                Text("Save")
            }
        }
    }
}