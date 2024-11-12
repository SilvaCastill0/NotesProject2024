package io.garrit.android.demo.tododemo

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import io.garrit.android.demo.tododemo.ui.theme.TodoDemoTheme
import androidx.compose.foundation.layout.Spacer
import androidx.compose.ui.Alignment
import android.widget.Toast
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.ui.platform.LocalContext


class TaskActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TodoDemoTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Color.White
                ) {
                    TaskInputScreen{ title, description ->
                        val resultIntent = Intent()
                        resultIntent.putExtra("task_title", title)
                        resultIntent.putExtra("task_description", description)
                        setResult(Activity.RESULT_OK, resultIntent)
                        finish()
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskInputScreen(onSubmit: (String, String) -> Unit) {
    val titleState = remember { mutableStateOf("") }
    val descriptionState = remember { mutableStateOf("") }
    val context = LocalContext.current as ComponentActivity



    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        TopAppBar(
            title = { Text("Edit Task") },
            navigationIcon = {
                IconButton(onClick = { context.finish() }) {
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
                value = titleState.value,
                onValueChange = { titleState.value = it },
                label = { Text("Title", color = Color.Black) },
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = descriptionState.value,
                onValueChange = { descriptionState.value = it },
                label = { Text("Description", color = Color.Black) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp)
                    .weight(1f)
            )

            Spacer(modifier = Modifier.weight(0.1f))

            Button(
                onClick = {
                    if (titleState.value.isEmpty() || descriptionState.value.isEmpty()) {
                        Toast.makeText(
                            context,
                            "Please do not leave fields empty",
                            Toast.LENGTH_SHORT
                        ).show()
                    } else if (titleState.value.length < 3 || descriptionState.value.length < 5) {
                        Toast.makeText(
                            context,
                            "Title must be at least 3 characters and Description must be at least 10 characters",
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        onSubmit(titleState.value, descriptionState.value)
                    }
                },
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(top = 16.dp)
            ) {
                Text("Add Task", color = Color.White)
            }
        }
    }
}