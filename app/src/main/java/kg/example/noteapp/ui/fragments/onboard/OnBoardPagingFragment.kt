package kg.example.noteapp.ui.fragments.onboard

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kg.example.noteapp.R
import kg.example.noteapp.databinding.FragmentOnBoardPagingBinding


class OnBoardPagingFragment : Fragment() {

    private lateinit var binding: FragmentOnBoardPagingBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentOnBoardPagingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initialize()
    }

    private fun initialize() = with(binding) {
        when (requireArguments().getInt(ARG_ONBOARD_POSITION)) {
            0 -> {
                txtOnboard.text = "Очень удобный функционал"
                animationView.setAnimation(R.raw.lotti_01)
            }

            1 -> {
                txtOnboard.text = "Быстрый, качественный продукт"
                animationView.setAnimation(R.raw.lotti_02)
            }

            2 -> {
                txtOnboard.text = "Куча функций и интересных фишек"
                animationView.setAnimation(R.raw.lotti_03)
            }
        }
    }

    companion object {
        const val ARG_ONBOARD_POSITION = "onBoard"
    }
}