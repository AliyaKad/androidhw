package ru.itis.newproject

import Swipe
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    lateinit var adapter: MyAdapter
    private var isGridView: Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_main, container, false)
        recyclerView = view.findViewById(R.id.recycler_view)

        if (savedInstanceState != null) {
            isGridView = savedInstanceState.getBoolean("isGridView", false)
        }

        setRecyclerViewLayoutManager()

        adapter = MyAdapter(DataContainer.items, this)
        recyclerView.adapter = adapter

        val itemTouchHelper = ItemTouchHelper(Swipe(adapter))
        itemTouchHelper.attachToRecyclerView(recyclerView)

        view.findViewById<Button>(R.id.button_list).setOnClickListener {
            setListView()
        }

        view.findViewById<Button>(R.id.button_grid).setOnClickListener {
            setGridView()
        }

        view.findViewById<FloatingActionButton>(R.id.fab).setOnClickListener {
            openBottomSheet()
        }

        return view
    }

    private fun openBottomSheet() {
        val bottomSheet = BottomSheetFragment()
        bottomSheet.show(parentFragmentManager, bottomSheet.tag)
    }

    private fun setListView() {
        isGridView = false
        setRecyclerViewLayoutManager()
        adapter.notifyDataSetChanged()
    }

    private fun setGridView() {
        isGridView = true
        setRecyclerViewLayoutManager()
        adapter.notifyDataSetChanged()
    }

    private fun setRecyclerViewLayoutManager() {
        recyclerView.layoutManager = if (isGridView) {
            GridLayoutManager(context, 3)
        } else {
            LinearLayoutManager(context)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putBoolean("isGridView", isGridView)
    }

    fun openDetailFragment(item: Item) {
        val detailFragment = DetailFragment().apply {
            arguments = Bundle().apply {
                putString("imageUrl", item.imageUrl)
                putString("title", item.title)
                putString("description", item.description)
            }
        }

        parentFragmentManager.beginTransaction()
            .replace(R.id.main_fragment_container, detailFragment)
            .addToBackStack(null)
            .commit()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        recyclerView.adapter = null
    }
}
