package kg.example.noteapp.ui.fragments.note

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import kg.example.noteapp.App
import kg.example.noteapp.R
import kg.example.noteapp.data.models.NoteModel
import kg.example.noteapp.databinding.FragmentNoteBinding
import kg.example.noteapp.interfaces.OnClickItem
import kg.example.noteapp.ui.adapter.NoteAdapter
import kg.example.noteapp.utils.PreferenceHelper
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


class NoteFragment : Fragment(), OnClickItem {

    private lateinit var binding: FragmentNoteBinding
    private val noteAdapter = NoteAdapter(this, this)
    private val sharedPreferences = PreferenceHelper()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNoteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sharedPreferences.unit(requireContext())
        initialize()
        setupListeners()
        getData()
        resetCount()
    }

    private fun initialize() {
        binding.rvNote.apply {
            adapter = noteAdapter
            layoutManager = if (sharedPreferences.swapLayout) {
                GridLayoutManager(requireContext(), 2)
            } else {
                LinearLayoutManager(requireContext())
            }
        }
    }

    private fun setupListeners() {
        binding.btnAdd.setOnClickListener {
            findNavController().navigate(R.id.action_noteFragment_to_noteDetailFragment)
        }
        binding.btnSwap.setOnClickListener {
            tooggleLayout()
        }
    }

    private fun tooggleLayout() {
        sharedPreferences.swapLayout = !sharedPreferences.swapLayout
        binding.rvNote.layoutManager = if (sharedPreferences.swapLayout) {
            GridLayoutManager(requireContext(), 2)
        } else {
            LinearLayoutManager(requireContext())
        }

    }

    private fun getData() {
        App.appDataBase?.noteDao()?.getAll()?.observe(viewLifecycleOwner) {
            noteAdapter.submitList(it)
        }

    }

    private fun resetCount() {
        sharedPreferences.unit(requireContext())
        binding.btmReset.setOnClickListener {
            Toast.makeText(context, "APP IS RESET", Toast.LENGTH_SHORT).show()
            sharedPreferences.onBoardShow = false
            binding.btmReset.text = "App start " + sharedPreferences.onBoardShow.toString()
        }
    }

    override fun onLongClick(noteModel: NoteModel) {
        val builder = AlertDialog.Builder(requireContext())
        with(builder) {
            setTitle("Вы точно хотите удалить запись?")
            setPositiveButton("Нет") { dialog, which ->
                dialog.cancel()
            }
            setNegativeButton("Да") { dialog, which ->
                App.appDataBase?.noteDao()?.deleteNote(noteModel)
            }
            setNeutralButton("Оставить") { dialog, which ->
                dialog.cancel()
            }
            show()
        }
        builder.create()
    }

    override fun onClick(noteModel: NoteModel) {
        val action = NoteFragmentDirections.actionNoteFragmentToNoteDetailFragment(noteModel.id)
        findNavController().navigate(action)
    }
}