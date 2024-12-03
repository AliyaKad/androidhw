package ru.itis.newproject.hw3

data class QuestionData(
    val question: String,
    val answers: List<String>,
    val correctAnswer: String
)