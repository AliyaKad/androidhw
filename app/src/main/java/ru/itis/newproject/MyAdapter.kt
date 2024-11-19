package ru.itis.newproject

import android.app.AlertDialog
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class MyAdapter(private val items: MutableList<Item>, private val fragment: MainFragment) : RecyclerView.Adapter<MyAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textView: TextView = view.findViewById(R.id.header_tv)
        val imageView: ImageView = view.findViewById(R.id.picture_iv)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_view, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.textView.text = item.title

        Glide.with(holder.imageView.context)
            .load(item.imageUrl)
            .into(holder.imageView)

        holder.itemView.setOnClickListener {
            fragment.openDetailFragment(item)
        }

        holder.itemView.setOnLongClickListener {
            showDeleteConfirmationDialog(position)
            true
        }
    }

    private fun showDeleteConfirmationDialog(position: Int) {
        val dialogBuilder = AlertDialog.Builder(fragment.requireContext())
        dialogBuilder.setMessage("Вы уверены, что хотите удалить этот элемент?")
            .setCancelable(false)
            .setPositiveButton("Да") { dialog, id ->
                removeItem(position)
            }
            .setNegativeButton("Нет") { dialog, id ->
                dialog.dismiss()
            }

        val alert = dialogBuilder.create()
        alert.show()
    }

    fun removeItem(position: Int) {
        items.removeAt(position)
        notifyItemRemoved(position)
    }


    override fun getItemCount(): Int = items.size
}