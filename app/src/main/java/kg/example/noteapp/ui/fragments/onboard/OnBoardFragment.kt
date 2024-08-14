package kg.example.noteapp.ui.fragments.onboard

import android.content.res.ColorStateList
import android.graphics.drawable.RippleDrawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import kg.example.noteapp.R
import kg.example.noteapp.databinding.FragmentOnBoardBinding
import kg.example.noteapp.ui.adapter.onBoardAdapter
import kg.example.noteapp.ui.fragments.note.NoteFragment
import kg.example.noteapp.utils.PreferenceHelper


class OnBoardFragment : Fragment() {

    private lateinit var binding: FragmentOnBoardBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentOnBoardBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initialize()
        setupListeners()
    }


    private fun initialize() {
        binding.viewpager2.adapter = onBoardAdapter(this@OnBoardFragment)

        TabLayoutMediator(binding.tabLayout, binding.viewpager2) { tab, position ->

        }.attach()

    }

    private fun setupListeners() = with(binding.viewpager2) {
        registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                if (position == 2) {
                    binding.txtSkip.visibility = View.INVISIBLE
                    binding.txtStart.visibility = View.VISIBLE
                } else {
                    binding.txtSkip.visibility = View.VISIBLE
                    binding.txtStart.visibility = View.INVISIBLE
                }
            }
        })

        binding.txtSkip.setOnClickListener {
            if (currentItem < 3) {
                setCurrentItem(currentItem + 2, true)
            }
        }

        val sharedPreferences = PreferenceHelper()
        sharedPreferences.unit(requireContext())
        binding.txtStart.setOnClickListener {
            Toast.makeText(context, "App Start", Toast.LENGTH_SHORT).show()
            sharedPreferences.onBoardShow = true
            val noteFragment = NoteFragment()

            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, noteFragment)
                .commit()


        }
    }
}