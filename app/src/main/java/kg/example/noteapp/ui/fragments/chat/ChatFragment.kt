package kg.example.noteapp.ui.fragments.chat

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.firestore.ListenerRegistration
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kg.example.noteapp.databinding.FragmentChatBinding
import kg.example.noteapp.ui.adapter.ChatAdapter


class ChatFragment : Fragment() {

    private lateinit var binding: FragmentChatBinding
    private val chatAdapter = ChatAdapter()
    private val db = Firebase.firestore
    private lateinit var query: Query
    private lateinit var listener: ListenerRegistration

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentChatBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initialize()
        setupListener()
        observeMessage()
    }

    private fun initialize() {
        binding.rvChat.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = chatAdapter
        }
    }

    private fun setupListener() {
        binding.sendBtn.setOnClickListener {
            val user = hashMapOf(
                "name" to binding.etMessage.text.toString()
            )
            db.collection("user").add(user).addOnCompleteListener { }
            binding.etMessage.text.clear()
        }
    }

    private fun observeMessage() {
        query = db.collection("user")
        listener = query.addSnapshotListener { value, error ->
            if (error != null) {
                return@addSnapshotListener
            }
            value?.let { snapshot ->
                val messages = mutableListOf<String>()
                for (doc in snapshot.documents) {
                    val message = doc.getString("name")
                    message?.let {
                        messages.add(message)
                    }
                }
                chatAdapter.submitList(messages)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        listener.remove()
    }
}