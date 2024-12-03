package ru.itis.newproject

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import ru.itis.newproject.hw3.QuestionAdapter
import ru.itis.newproject.hw3.QuestionFragment
import ru.itis.newproject.hw3.QuizViewModel

class MainActivity : AppCompatActivity(), QuestionFragment.OnNextButtonClickListener {

    private lateinit var viewPager: ViewPager2
    private lateinit var adapter: QuestionAdapter
    private val quizViewModel: QuizViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewPager = findViewById(R.id.viewPager)
        adapter = QuestionAdapter(this, quizViewModel)
        viewPager.adapter = adapter
    }

    override fun onAnswerSelected(position: Int, answer: String) {
        quizViewModel.selectedAnswers[position] = answer
    }

    override fun onNextButtonClicked() {
        val nextItem = viewPager.currentItem + 1
        if (nextItem < adapter.itemCount) {
            viewPager.currentItem = nextItem
        }
    }

    override fun onBackButtonClicked() {
        val previousItem = viewPager.currentItem - 1
        if (previousItem >= 0) {
            viewPager.currentItem = previousItem
        }
    }
}