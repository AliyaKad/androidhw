package ru.itis.newproject.hw3

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class QuestionAdapter(activity: FragmentActivity, private val quizViewModel: QuizViewModel) : FragmentStateAdapter(activity) {

    private val questions = listOf(
        QuestionData("2+2=", listOf("1", "5", "4", "2"), "4"),
        QuestionData("3!=", listOf("6", "3", "9"), "6"),
        QuestionData("6*7=", listOf("41", "47", "42", "48"), "42"),
        QuestionData("7-5=", listOf("1", "2", "3"), "2"),

        QuestionData("40*5=", listOf("20", "2", "400", "200", "80"), "200"),
        QuestionData("6*0=", listOf("6", "0"), "0"),
        QuestionData("100/10=", listOf("50", "1", "1000", "10"), "10"),
        QuestionData("12.6/21=", listOf("0.6", "0.7", "0.42", "0.2"), "0.6"),

        QuestionData("10/2.5=", listOf("6", "25", "4"), "4"),
        QuestionData("7*0.4=", listOf("3.5", "2.5", "8", "0", "74", "2.8"), "2.8"),
        QuestionData("15.6/13=", listOf("12", "5", "1.2", "48"), "1.2")
    )

    override fun getItemCount(): Int = questions.size

    override fun createFragment(position: Int): Fragment {
        val question = questions[position]
        val selectedAnswer = quizViewModel.selectedAnswers[position]
        return QuestionFragment.newInstance(
            question.question,
            question.answers,
            question.correctAnswer,
            selectedAnswer,
            position,
            totalQuestions = questions.size
        )
    }
}