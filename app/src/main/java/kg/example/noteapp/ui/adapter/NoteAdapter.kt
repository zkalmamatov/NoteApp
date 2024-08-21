package kg.example.noteapp.ui.adapter

import android.graphics.Color
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.LayerDrawable
import android.graphics.drawable.ShapeDrawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import kg.example.noteapp.R
import kg.example.noteapp.data.models.NoteModel
import kg.example.noteapp.databinding.ItemNoteBinding
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class NoteAdapter : ListAdapter<NoteModel, NoteAdapter.ViewHolder>(DiffCallback()) {
    class ViewHolder(private val binding: ItemNoteBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: NoteModel) {
            binding.txtTitle.text = item.title
            binding.txtDescription.text = item.description
            val bgDraw = ContextCompat.getDrawable(
                binding.root.context,
                R.drawable.bg_rounded
            ) as LayerDrawable

            if (!item.color.isNullOrEmpty()) {
                val color = try {
                    Color.parseColor(item.color)
                } catch (e: IllegalArgumentException) {
                    Color.parseColor(R.drawable.btn_grey.toString())
                }
                val shapeDrawable = bgDraw.getDrawable(0) as? GradientDrawable
                shapeDrawable?.setColor(color)

                binding.itemColor.background = shapeDrawable
                binding.txtDate.text = item.date
                binding.txtTime.text = item.time
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemNoteBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class DiffCallback : DiffUtil.ItemCallback<NoteModel>() {
        override fun areItemsTheSame(oldItem: NoteModel, newItem: NoteModel): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: NoteModel, newItem: NoteModel): Boolean {
            return oldItem == newItem
        }

    }


}