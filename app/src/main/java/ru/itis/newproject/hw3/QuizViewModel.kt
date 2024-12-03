package ru.itis.newproject.hw3

import androidx.lifecycle.ViewModel

class QuizViewModel : ViewModel() {
    val selectedAnswers = mutableListOf<String?>(null, null, null, null, null, null, null, null, null, null, null)
    var isQuizCompleted = false
    fun areAllAnswersFilled(): Boolean {
        return !selectedAnswers.contains(null)
    }
}
