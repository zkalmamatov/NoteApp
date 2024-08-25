package kg.example.noteapp.ui.activity

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import kg.example.noteapp.R
import kg.example.noteapp.databinding.ActivityMainBinding
import kg.example.noteapp.databinding.FragmentOnBoardBinding
import kg.example.noteapp.ui.adapter.onBoardAdapter
import kg.example.noteapp.ui.fragments.note.NoteFragment
import kg.example.noteapp.ui.fragments.signin.SignUpFragment
import kg.example.noteapp.utils.PreferenceHelper
import kotlin.math.log

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragment_container) as NavHostFragment

        navController = navHostFragment.navController

        startApp()
    }

    private fun startApp() {
        val sharedPreferences = PreferenceHelper()
        sharedPreferences.unit(this)

        if (sharedPreferences.onBoardShow == false) {
            navController.navigate(R.id.onBoardFragment)
        } else if (sharedPreferences.auth == false) {
            navController.navigate(R.id.signUpFragment)
        } else {
            navController.navigate(R.id.noteFragment)
        }
    }



}