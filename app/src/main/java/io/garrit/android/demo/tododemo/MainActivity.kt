package io.garrit.android.demo.tododemo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import io.garrit.android.demo.tododemo.ui.theme.ColorDarkBlue
import io.garrit.android.demo.tododemo.ui.theme.TodoDemoTheme
import androidx.compose.ui.graphics.Color
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.compose.ui.unit.dp
import android.content.Intent
import android.widget.Button
import androidx.activity.result.ActivityResult
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.*
import androidx.compose.ui.platform.LocalContext
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import java.util.UUID

data class Task(
    val title: String,
    val description: String = ""
)


class MainActivity : ComponentActivity() {
    private val taskList = mutableStateListOf<Task>()
    private val taskLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == RESULT_OK) {
            val title = result.data?.getStringExtra("task_title") ?: ""
            val description = result.data?.getStringExtra("task_description") ?: ""
            taskList.add(Task(title, description))
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TodoDemoTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Color.White
                ) {
                    MainScreen(taskList){
                        val intent = Intent(this, TaskActivity::class.java)
                        taskLauncher.launch(intent)
                    }
                }
            }
        }
    }
}

@Composable
fun MainScreen(taskList: List<Task>, onAddTask: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Bottom
    ) {
        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .padding(bottom = 80.dp)
        ) {
            items(taskList) { task ->
                TaskRow(task)
            }
        }

        var isExpanded by remember { mutableStateOf(false) }

        val surfaceColor by animateColorAsState(
            if (isExpanded) ColorDarkBlue else Color.White,
            label = ""
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End
        ){
            Button(
                onClick = onAddTask, modifier = Modifier.padding(16.dp)
            ) {
                Text("+")
            }
        }
    }
}


@Composable
fun TaskRow(task: Task) {
    Column(modifier = Modifier.padding(8.dp)) {
        Text(text = "Title: ${task.title}", color = Color.Black)
        if(task.description.isNotBlank()){
            Text(text = "Description: ${task.description}", color = Color.Black)
        }
    }
}
