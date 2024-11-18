package ru.itis.newproject

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class BottomSheetFragment : BottomSheetDialogFragment() {

    private lateinit var editText: EditText
    private lateinit var addButton: Button
    private lateinit var removeButton: Button
    private lateinit var addSingleButton: Button
    private lateinit var removeSingleButton: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_bottom_sheet_dialog, container, false)

        editText = view.findViewById(R.id.edit_text)
        addButton = view.findViewById(R.id.button_add)
        removeButton = view.findViewById(R.id.button_remove)
        addSingleButton = view.findViewById(R.id.button_add_single)
        removeSingleButton = view.findViewById(R.id.button_remove_single)

        addButton.setOnClickListener { addRandomItems() }
        removeButton.setOnClickListener { removeRandomItems() }
        addSingleButton.setOnClickListener { addSingleItem() }
        removeSingleButton.setOnClickListener { removeSingleItem() }

        return view
    }

    private fun addRandomItems() {
        val count = editText.text.toString().toIntOrNull()
        if (count != null && count > 0) {
            DataContainer.addItems(count)
            notifyAdapter()
            dismiss()
        } else {
            Log.e("BottomSheetFragment", "Invalid input for addRandomItems: $count")
        }
    }

    private fun removeRandomItems() {
        val count = editText.text.toString().toIntOrNull()
        if (count != null && count > 0) {
            DataContainer.removeItems(count)
            notifyAdapter()
            dismiss()
        } else {
            Log.e("BottomSheetFragment", "Invalid input for removeRandomItems: $count")
        }
    }

    private fun addSingleItem() {
        DataContainer.addItems(1)
        notifyAdapter()
        dismiss()
    }

    private fun removeSingleItem() {
        if (DataContainer.items.isNotEmpty()) {
            val randomPosition = (0 until DataContainer.items.size).random()
            DataContainer.items.removeAt(randomPosition)
            notifyAdapter()
            dismiss()
        } else {
            Log.e("BottomSheetFragment", "No items to remove")
        }
    }

    private fun notifyAdapter() {
        val mainFragment = parentFragment as? MainFragment
        if (mainFragment != null) {
            mainFragment.adapter.notifyDataSetChanged()
        } else {
            Log.e("BottomSheetFragment", "Parent fragment is null")
        }
    }
}

