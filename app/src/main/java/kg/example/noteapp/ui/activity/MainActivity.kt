package kg.example.noteapp.ui.activity

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
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
    private lateinit var requestNotificationPermissionLauncher: ActivityResultLauncher<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragment_container) as NavHostFragment
        navController = navHostFragment.navController

        val sharedPreference = PreferenceHelper().apply {
            unit(this@MainActivity)
        }

        requestNotificationPermissionLauncher = registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            sharedPreference.notificationsEnabled = isGranted
            if (isGranted) {
                Toast.makeText(this, "Notification permission granted", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Notification permission denied", Toast.LENGTH_SHORT).show()
            }
            startApp()
        }
        if (checkSelfPermission(Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            requestNotificationPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
        }else{
            sharedPreference.notificationsEnabled = true
            startApp()
        }
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