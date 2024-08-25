package kg.example.noteapp.ui.fragments.note

import android.annotation.SuppressLint
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.graphics.toColorInt
import androidx.navigation.fragment.findNavController
import com.airbnb.lottie.parser.ColorParser
import kg.example.noteapp.App
import kg.example.noteapp.R
import kg.example.noteapp.data.models.NoteModel
import kg.example.noteapp.databinding.FragmentNoteDetailBinding
import kg.example.noteapp.ui.adapter.NoteAdapter
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


class NoteDetailFragment : Fragment() {

    private lateinit var binding: FragmentNoteDetailBinding
    private var noteId: Int = -1

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNoteDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        updateNote()
        setCurrentDateTime()
        setupListeners()
    }

    private fun updateNote() {
        arguments?.let {
            noteId = it.getInt("noteId", -1)
        }
        if (noteId != -1) {
            val argsNote = App.appDataBase?.noteDao()?.getById(noteId)
            argsNote?.let { model ->
                binding.etTitle.setText(model.title)
                binding.etDescription.setText(model.description)
            }
        }
    }

    private fun setCurrentDateTime() {
        val dateFormat = SimpleDateFormat("dd MMMM,", Locale.getDefault())
        val timeFormat = SimpleDateFormat("HH:mm", Locale.getDefault())

        val currentDate = dateFormat.format(Date())
        val currentTime = timeFormat.format(Date())

        binding.txtDate2.text = currentDate
        binding.txtTime2.text = currentTime
    }

    @SuppressLint("ResourceAsColor")
    private fun setupListeners() {
        binding.txtAddNote.setOnClickListener {
            val etTitle = binding.etTitle.text.toString().trim()
            val etDescription = binding.etDescription.text.toString().trim()
            val drawable = getSelectorColor()
            val fragmentColor = String.format("#%06X", (0xFFFFFF and drawable))
            val date = binding.txtDate2.text.toString()
            val time = binding.txtTime2.text.toString()

            if (noteId != -1) {
                val updateNote = NoteModel(etTitle, etDescription, "", "", "")
                updateNote.id = noteId
                App.appDataBase?.noteDao()?.updateNote(updateNote)
            } else {
                App.appDataBase?.noteDao()
                    ?.insertNote(NoteModel(etTitle, etDescription, fragmentColor, date, time))
            }
            findNavController().navigateUp()
        }
        binding.btnBack.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    private fun getSelectorColor(): Int {
        return when (binding.btnColorGroup.checkedRadioButtonId) {
            R.id.btn_white -> Color.parseColor("#EBE4C9")
            R.id.btn_red -> Color.parseColor("#571818")
            else -> Color.parseColor("#191818")
        }
    }
}