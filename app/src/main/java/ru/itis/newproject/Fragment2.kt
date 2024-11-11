package ru.itis.newproject

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import ru.itis.newproject.databinding.Fragment2Binding

class Fragment2 : Fragment() {

    private var viewBinding: Fragment2Binding? = null

    companion object {
        private const val ARG_TEXT = "arg_text"

        fun newInstance(text: String): Fragment2 {
            val fragment = Fragment2()
            val args = Bundle()
            args.putString(ARG_TEXT, text)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding = Fragment2Binding.inflate(inflater, container, false)

        val inputText = arguments?.getString(ARG_TEXT)
        viewBinding?.textView?.text = inputText

        viewBinding?.button?.setOnClickListener {
            val fragment3 = Fragment3.newInstance(inputText ?: "")
            val fragmentManager = parentFragmentManager
            fragmentManager.beginTransaction()
                .replace(R.id.main_fragment_container, fragment3)
                .addToBackStack(null)
                .commit()
        }
        return viewBinding?.root
    }

    override fun onDestroyView() {
        viewBinding = null
        super.onDestroyView()
    }
}