package ru.itis.newproject

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide

class DetailFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d("DetailFragment", "onCreateView called")
        return inflater.inflate(R.layout.more_information_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val args = arguments
        val imageUrl = args?.getString("imageUrl") ?: ""
        val title = args?.getString("title") ?: "Заголовок"
        val description = args?.getString("description") ?: "Описание"

        val imageView = view.findViewById<ImageView>(R.id.image_view)
        val titleTextView = view.findViewById<TextView>(R.id.title_text_view)
        val descriptionTextView = view.findViewById<TextView>(R.id.description_text_view)
        Glide.with(imageView.context)
            .load(imageUrl)
            .into(imageView)

        titleTextView.text = title
        descriptionTextView.text = description
    }

    override fun onDestroyView() {
        val imageView = view?.findViewById<ImageView>(R.id.image_view)
        imageView?.let {
            Glide.with(it.context).clear(it)
        }
        super.onDestroyView()
    }
}
