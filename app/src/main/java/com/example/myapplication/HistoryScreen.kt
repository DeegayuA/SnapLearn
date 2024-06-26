package com.example.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.room.Room
import com.example.myapplication.data.ItemsDatabase
import com.example.myapplication.presentation.ItemsViewModel
import com.example.myapplication.ui.theme.MyApplicationTheme


// Define your data class here (replace with your actual data structure)
data class QuestionAnswer(val question: String, val answer: String)

class HistoryScreen : ComponentActivity() {

    private val database by lazy {
        Room.databaseBuilder(
            applicationContext,
            ItemsDatabase::class.java,
            name = "items.db"
        ).build()
    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val viewModel by viewModels<ItemsViewModel>(
            factoryProducer = {
                object : ViewModelProvider.Factory {
                    override fun <T : ViewModel> create(modelClass: Class<T>): T {
                        return ItemsViewModel(database.dao) as T
                    }
                }
            }
        )

        // Replace with your actual data fetching logic (e.g., from ViewModel)
        val questionsAndAnswers =
            viewModel.getQuestionsAndAnswers() // Assuming this function exists in ViewModel
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme { // Wrap the content with MyApplicationTheme
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Surface(
                        modifier = Modifier.padding(innerPadding),
                        color = MaterialTheme.colorScheme.surface // Use surface color
                    ) {
                        LazyColumn(modifier = Modifier.fillMaxWidth()) {
                            item {
                                Text(
                                    text = "History",
                                    style = MaterialTheme.typography.headlineLarge,
                                    modifier = Modifier.padding(16.dp)
                                )
                            }
                            items(questionsAndAnswers) { questionAnswer ->
                                QuestionAnswerCard(questionAnswer)
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun QuestionAnswerList(questionsAndAnswers: List<QuestionAnswer>, modifier: Modifier = Modifier) {
    Column(modifier = modifier) {
        questionsAndAnswers.forEach { questionAnswer ->
            QuestionAnswerCard(questionAnswer)
        }
    }
}

@Composable
fun QuestionAnswerCard(questionAnswer: QuestionAnswer, modifier: Modifier = Modifier) {
    Surface(
        modifier = modifier.padding(8.dp).fillMaxWidth(),
        shape = RoundedCornerShape(8.dp),
        color = MaterialTheme.colorScheme.surfaceVariant, // Use surface color
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = questionAnswer.question,
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.onSurface // Use onSurface color
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = questionAnswer.answer,
                color = MaterialTheme.colorScheme.onSurface // Use onSurface color
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun QuestionAnswerListPreview() {
    MyApplicationTheme {
        // Provide some sample data for preview
        val questionsAndAnswers = listOf(
            QuestionAnswer("What is Mobile Computing?", "Mobile computing encompasses human-computer interaction where portable devices like smartphones and tablets are utilized for data processing and transmission, often wirelessly. This field integrates mobile communication, hardware, and software to facilitate on-the-go computing, enabling users to access, store, and manipulate information from any location."),
            QuestionAnswer("What are the reasons behind the significant increase in the use of mobile devices durıng the past decade?", "The last decade has witnessed a surge in mobile device usage due to a confluence of factors. Firstly, advancements in hardware led to powerful, affordable smartphones with constant internet access. Secondly, the proliferation of mobile applications (apps) catering to diverse needs, from communication and entertainment to productivity and education, fueled user engagement. Finally, the expansion of wireless infrastructure ensured seamless connectivity, making mobile devices an indispensable tool for information access and interaction.")
        )
        QuestionAnswerList(questionsAndAnswers)
    }
}
