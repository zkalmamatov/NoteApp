package kg.example.noteapp.ui.adapter

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import kg.example.noteapp.ui.fragments.onboard.OnBoardPagingFragment
import kg.example.noteapp.ui.fragments.onboard.OnBoardPagingFragment.Companion.ARG_ONBOARD_POSITION

class onBoardAdapter (
    fragment: Fragment
) : FragmentStateAdapter (fragment) {
    override fun getItemCount(): Int {
        return 3
    }

    override fun createFragment(position: Int) = OnBoardPagingFragment ().apply {
        arguments = Bundle().apply {
            putInt(ARG_ONBOARD_POSITION, position)
        }
    }


}