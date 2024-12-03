package ru.itis.newproject.hw3

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.core.view.children
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import ru.itis.newproject.R
import ru.itis.newproject.databinding.FragmentQuestionBinding

class QuestionFragment : Fragment() {

    private var _binding: FragmentQuestionBinding? = null
    private val binding get() = _binding!!

    private var question: String? = null
    private var answers: List<String>? = null
    private var correctAnswer: String? = null
    private var selectedAnswer: String? = null
    private var position: Int = 0
    private var totalQuestions: Int = 0

    private lateinit var viewModel: QuizViewModel

    interface OnNextButtonClickListener {
        fun onNextButtonClicked()
        fun onBackButtonClicked()
        fun onAnswerSelected(position: Int, answer: String)
    }

    private var listener: OnNextButtonClickListener? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnNextButtonClickListener) {
            listener = context
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(requireActivity()).get(QuizViewModel::class.java)

        if (savedInstanceState != null) {
            question = savedInstanceState.getString(arg_question)
            answers = savedInstanceState.getStringArrayList(arg_answers)?.toList()
            correctAnswer = savedInstanceState.getString(correct_answer)
            selectedAnswer = savedInstanceState.getString(selected_answer)
            position = savedInstanceState.getInt(answer_position)
            totalQuestions = savedInstanceState.getInt("total_questions")
        } else {
            arguments?.let {
                question = it.getString(arg_question)
                answers = it.getStringArrayList(arg_answers)?.toList()
                correctAnswer = it.getString(correct_answer)
                selectedAnswer = it.getString(selected_answer)
                position = it.getInt(answer_position)
                totalQuestions = it.getInt("total_questions")
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentQuestionBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setQuestion(question ?: "Вопрос не задан", answers ?: emptyList(), correctAnswer ?: "", selectedAnswer)

        updateNextButtonText()

        binding.nextButton.setOnClickListener {
            if (position == totalQuestions - 1) {
                if (viewModel.areAllAnswersFilled()) {
                    viewModel.isQuizCompleted = true
                    Snackbar.make(view, "Тест сохранен", Snackbar.LENGTH_SHORT).show()
                } else {
                    Snackbar.make(view, "Пожалуйста, ответьте на все вопросы", Snackbar.LENGTH_SHORT).show()
                }
            } else {
                listener?.onNextButtonClicked()
            }
        }

        binding.backButton.setOnClickListener {
            listener?.onBackButtonClicked()
        }

        answers?.let {
            binding.answerLayout.removeAllViews()
            for (answer in it) {
                val answerButton = Button(context).apply {
                    text = answer
                    layoutParams = LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                    )
                    setOnClickListener {
                        handleAnswerClick(answer)
                    }
                    updateButtonColor(this, answer)
                }
                binding.answerLayout.addView(answerButton)
            }
        }

        updateButtonColors()
    }
    private fun updateNextButtonText() {
        if (position == totalQuestions - 1 || viewModel.isQuizCompleted) {
            binding.nextButton.text = "Завершить"
        } else {
            binding.nextButton.text = "Далее"
        }
    }

    override fun onResume() {
        super.onResume()
        updateNextButtonText()
    }

    private fun handleAnswerClick(selected: String) {
        if (selectedAnswer == selected) return

        selectedAnswer = selected
        viewModel.selectedAnswers[position] = selectedAnswer
        listener?.onAnswerSelected(position, selected)
        updateButtonColors()
    }

    private fun updateButtonColors() {
        binding.answerLayout.children.forEach { view ->
            if (view is Button) {
                updateButtonColor(view, view.text.toString())
            }
        }
    }

    private fun updateButtonColor(button: Button, answer: String) {
        when {
            answer == correctAnswer && answer == selectedAnswer -> {
                button.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.GREEN)) // Правильный ответ
            }
            answer == selectedAnswer -> {
                button.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.RED)) // Неправильный ответ
            }
            else -> {
                button.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.white)) // Нейтральный цвет
            }
        }
    }

    fun setQuestion(question: String, answers: List<String>, correctAnswer: String, selectedAnswer: String?) {
        this.question = question
        this.answers = answers
        this.correctAnswer = correctAnswer
        this.selectedAnswer = selectedAnswer

        binding.questionTextView.text = question
        binding.answerLayout.removeAllViews()

        for (answer in answers) {
            val answerButton = Button(context).apply {
                text = answer
                layoutParams = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                )
                setOnClickListener {
                    handleAnswerClick(answer)
                }
                setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.white))
            }
            binding.answerLayout.addView(answerButton)
        }

        updateButtonColors()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(arg_question, question)
        outState.putStringArrayList(arg_answers, ArrayList(answers))
        outState.putString(correct_answer, correctAnswer)
        outState.putString(selected_answer, selectedAnswer)
        outState.putInt(answer_position, position)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private const val arg_question = "question"
        private const val arg_answers = "answers"
        private const val correct_answer = "correct_answer"
        private const val selected_answer = "selected_answer"
        private const val answer_position = "position"

        fun newInstance(question: String, answers: List<String>, correctAnswer: String, selectedAnswer: String? = null, position: Int, totalQuestions: Int): QuestionFragment {
            val fragment = QuestionFragment()
            val args = Bundle().apply {
                putString(arg_question, question)
                putStringArrayList(arg_answers, ArrayList(answers))
                putString(correct_answer, correctAnswer)
                putString(selected_answer, selectedAnswer)
                putInt(answer_position, position)
                putInt("total_questions", totalQuestions)
            }
            fragment.arguments = args
            return fragment
        }
    }
}