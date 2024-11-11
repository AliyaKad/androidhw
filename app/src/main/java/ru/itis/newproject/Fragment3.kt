package ru.itis.newproject

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import ru.itis.newproject.Fragment2.Companion
import ru.itis.newproject.databinding.Fragment3Binding

class Fragment3 : Fragment() {

    private var viewBinding: Fragment3Binding? = null

    companion object {
        private const val ARG_TEXT = "arg_text"
        fun newInstance(text: String): Fragment3 {
            val fragment = Fragment3()
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
        viewBinding = Fragment3Binding.inflate(inflater, container, false)
        val inputText = arguments?.getString(Fragment3.ARG_TEXT)
        viewBinding?.textView2?.text = inputText


        return viewBinding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    override fun onDestroyView() {
        viewBinding = null
        super.onDestroyView()
    }
}

