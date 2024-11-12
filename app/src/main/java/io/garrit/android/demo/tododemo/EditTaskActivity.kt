package io.garrit.android.demo.tododemo

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

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

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun EditTaskScreen(task: Task) {
        val newTitle = remember { mutableStateOf(task.title) }
        val newDescription = remember { mutableStateOf(task.description) }
        val context = LocalContext.current as ComponentActivity

        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            TopAppBar(
                title = { Text("Edit Task") },
                navigationIcon = {
                    IconButton(onClick = { finish() }) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = "Back",
                            tint = Color.White
                        )
                    }
                },
                colors = TopAppBarDefaults.smallTopAppBarColors(
                    containerColor = Color.DarkGray,
                    titleContentColor = Color.White,
                    navigationIconContentColor = Color.White
                )
            )

            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxSize()
            ) {
                OutlinedTextField(
                    value = newTitle.value,
                    onValueChange = { newTitle.value = it },
                    label = { Text("Title") },
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = newDescription.value,
                    onValueChange = { newDescription.value = it },
                    label = { Text("Description") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp)
                        .weight(1f)
                )

                Spacer(modifier = Modifier.weight(0.1f))

                Button(
                    onClick = {
                        if (newTitle.value.isEmpty() || newDescription.value.isEmpty()) {
                            Toast.makeText(
                                context,
                                "Please do not leave fields empty",
                                Toast.LENGTH_SHORT
                            ).show()
                        } else if (newTitle.value.length < 3 || newDescription.value.length < 5) {
                            Toast.makeText(
                                context,
                                "Description must be at least 10 characters and Title must be at least 3 characters",
                                Toast.LENGTH_SHORT
                            ).show()
                        } else {
                            val intent = Intent().apply {
                                putExtra("task_title", newTitle.value)
                                putExtra("task_description", newDescription.value)
                            }
                            setResult(RESULT_OK, intent)
                            finish()
                        }
                    },
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(top = 16.dp)
                ) {
                    Text("Save")
                }
            }
        }
    }
}