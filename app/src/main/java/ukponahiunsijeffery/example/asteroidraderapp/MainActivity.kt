package ukponahiunsijeffery.example.asteroidraderapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import ukponahiunsijeffery.example.asteroidraderapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)

        // Find NavController from navHostFragment
        val mainNavController = this.findNavController(R.id.navHostFragment)

        // Link Navigation controller with Actionbar
        NavigationUI.setupActionBarWithNavController(this, mainNavController)
    }


    /**
     * Handle navigating Up within the application's activity hierarchy from the action bar
     */
    override fun onSupportNavigateUp(): Boolean {

        // Find NavController from navHostFragment
        val navController = this.findNavController(R.id.navHostFragment)

        // Navigate up
        return navController.navigateUp()
    }

}