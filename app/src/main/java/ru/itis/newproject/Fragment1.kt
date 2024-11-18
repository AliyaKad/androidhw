//package ru.itis.newproject
//
//import android.os.Bundle
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import androidx.fragment.app.Fragment
//import androidx.fragment.app.FragmentManager
//import com.google.android.material.bottomsheet.BottomSheetDialogFragment
//import ru.itis.newproject.databinding.Fragment1Binding
//
//class Fragment1 : Fragment() {
//
//    private var viewBinding: Fragment1Binding? = null
//    private var inputText: String? = null
//
//    override fun onCreateView(
//        inflater: LayoutInflater,
//        container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//        viewBinding = Fragment1Binding.inflate(inflater, container, false)
//
//        if (savedInstanceState != null) {
//            inputText = savedInstanceState.getString("inputText")
//            viewBinding?.editText?.setText(inputText)
//        }
//
//        viewBinding?.button1?.setOnClickListener {
//            inputText = viewBinding?.editText?.text.toString()
//            val fragment2 = Fragment2.newInstance(inputText!!)
//
//            val fragmentManager: FragmentManager = parentFragmentManager
//            fragmentManager.beginTransaction()
//                .replace(R.id.main_fragment_container, fragment2)
//                .addToBackStack(null)
//                .commit()
//        }
//
//        viewBinding?.button2?.setOnClickListener {
//            inputText = viewBinding?.editText?.text.toString()
//            val fragment2 = Fragment2.newInstance(inputText!!)
//
//            val fragmentManager: FragmentManager = parentFragmentManager
//            fragmentManager.beginTransaction()
//                .replace(R.id.main_fragment_container, fragment2)
//                .addToBackStack(null)
//                .commit()
//
//            val fragment3 = Fragment3.newInstance(inputText!!)
//            fragmentManager.beginTransaction()
//                .replace(R.id.main_fragment_container, fragment3)
//                .addToBackStack(null)
//                .commit()
//        }
//
//        viewBinding?.button3?.setOnClickListener {
//            val bottomSheet = MyBottomSheetFragment()
//            bottomSheet.onTextEntered = { text ->
//                viewBinding?.editText?.setText(text)
//            }
//            bottomSheet.show(parentFragmentManager, bottomSheet.tag)
//        }
//
//        return viewBinding?.root
//    }
//
//    override fun onSaveInstanceState(outState: Bundle) {
//        super.onSaveInstanceState(outState)
//        outState.putString("inputText", inputText)
//    }
//
//    override fun onDestroyView() {
//        viewBinding = null
//        super.onDestroyView()
//    }
//}