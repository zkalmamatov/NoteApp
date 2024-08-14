package kg.example.noteapp.ui.fragments.note

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import kg.example.noteapp.R
import kg.example.noteapp.databinding.FragmentNoteBinding
import kg.example.noteapp.utils.PreferenceHelper


class NoteFragment : Fragment() {

    private lateinit var binding: FragmentNoteBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNoteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        setupListeners()
        resetCount()
        txtView()

    }

//    private fun setupListeners() {
//        val sharedPreferences = PreferenceHelper()
//        sharedPreferences.unit(requireContext())
//        binding.btnSave.setOnClickListener {
//            val et = binding.etText.text.toString()
//            sharedPreferences.text = et
//            binding.txtSave.text = et
//        }
//        binding.txtSave.text = sharedPreferences.text
//    }

    private fun resetCount() {
        val sharedPreferences = PreferenceHelper()
        sharedPreferences.unit(requireContext())
        binding.btmReset.setOnClickListener {
            Toast.makeText(context, "BOOLEAN IS RESET", Toast.LENGTH_SHORT).show()
            sharedPreferences.onBoardShow = false
            binding.txtBool.text = sharedPreferences.onBoardShow.toString()
        }
    }

    private fun txtView() {
        val sharedPreferences = PreferenceHelper()
        sharedPreferences.unit(requireContext())
        binding.txtBool.text = sharedPreferences.onBoardShow.toString()
    }


}