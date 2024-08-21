package kg.example.noteapp.ui.fragments.note

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import kg.example.noteapp.App
import kg.example.noteapp.R
import kg.example.noteapp.databinding.FragmentNoteBinding
import kg.example.noteapp.ui.adapter.NoteAdapter
import kg.example.noteapp.utils.PreferenceHelper
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


class NoteFragment : Fragment() {

    private lateinit var binding: FragmentNoteBinding
    private val noteAdapter = NoteAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNoteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initialize()
        setupListeners()
        getData()
        resetCount()

    }

    private fun initialize() {
        binding.rvNote.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = noteAdapter
        }
    }

    private fun setupListeners() {
        binding.btnAdd.setOnClickListener{
            findNavController().navigate(R.id.action_noteFragment_to_noteDetailFragment)
        }
    }

    private fun getData() {
        App.appDataBase?.noteDao()?.getAll()?.observe(viewLifecycleOwner){
            noteAdapter.submitList(it)
        }

    }

    private fun resetCount() {
        val sharedPreferences = PreferenceHelper()
        sharedPreferences.unit(requireContext())
        binding.btmReset.setOnClickListener {
            Toast.makeText(context, "APP IS RESET", Toast.LENGTH_SHORT).show()
            sharedPreferences.onBoardShow = false
            binding.btmReset.text = "App start " + sharedPreferences.onBoardShow.toString()
        }
    }



}