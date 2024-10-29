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
import io.garrit.android.demo.tododemo.ui.theme.ColorBlack
import io.garrit.android.demo.tododemo.ui.theme.TodoDemoTheme
import androidx.compose.ui.graphics.Color
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.compose.ui.unit.dp
import android.content.Intent
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.*
import androidx.compose.ui.platform.LocalContext
import java.util.UUID

data class Task(
    val id: String = UUID.randomUUID().toString(),
    val title: String,
    var isChecked: MutableState<Boolean> = mutableStateOf(false),
)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val list = remember { mutableStateListOf<Task>() }

            TodoDemoTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = ColorBlack
                ) {
                    MainScreen(list = list)
                }
            }
        }
    }
}

@Composable
fun MainScreen(list: MutableList<Task>, modifier: Modifier = Modifier) {
    val context = LocalContext.current

    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        LazyColumn(
            modifier = Modifier
                .weight(1f)
//                .padding(bottom = 80.dp)
        ) {
            items(list) { task ->
                RowView(task)
            }
        }

        TextInputView(list = list)

        Button(
            onClick = {
                val intent = Intent(context, TaskActivity::class.java)
                context.startActivity(intent)
            },
            modifier = Modifier.padding(16.dp)
        ) {
            Text("Go to New Activity")
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TextInputView(list: MutableList<Task> ) {
    var text by rememberSaveable { mutableStateOf("") }

    ConstraintLayout(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ){
        val (button, textField) = createRefs()

        OutlinedTextField(
            value = text,
            onValueChange = { text = it },
            modifier = Modifier.constrainAs(textField) {
                bottom.linkTo(parent.bottom, margin = 16.dp)
                end.linkTo(parent.end, margin = 16.dp)
                end.linkTo(button.start, margin = 8.dp)
            },
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedTextColor = Color.White,
                unfocusedTextColor = Color.White,
                focusedBorderColor = Color.Blue,
                unfocusedBorderColor = Color.Gray
            )
        )

        Button(
            onClick = {
                if (text.isNotBlank()){
                    list.add(Task(title = text))
                    text = ""
                }
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Green,
                contentColor = Color.White
            ),
            modifier = Modifier.constrainAs(button) {
                bottom.linkTo(textField.bottom)
                start.linkTo(textField.end, margin = 8.dp)
            }
        ){
            Text("+")
        }

    }
}


@Composable
fun RowView(task: Task) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Checkbox(
            checked = task.isChecked.value,
            onCheckedChange = { task.isChecked.value = !task.isChecked.value },
            colors = CheckboxDefaults.colors(
                checkedColor = Color.Green,
                uncheckedColor = Color.Green
            )
        )
        Text(
            task.title,
            color = Color.White
        )
    }
}

@Preview(showBackground = true)
@Composable
fun MainActivityPreview() {
    val list = remember { mutableStateListOf(
        Task (title = "Sample task 1"),
        Task(title = "Sample task 2"))
    }

    TodoDemoTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = ColorBlack
        ) {
            MainScreen(list = list)
        }
    }
}
