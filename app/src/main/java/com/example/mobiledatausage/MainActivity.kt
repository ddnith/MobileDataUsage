package com.example.mobiledatausage

import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.ProgressBar
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.mobiledatausage.model.repository.MainRepository
import com.example.mobiledatausage.tracking.Tracker
import com.example.mobiledatausage.ui.list.MobileUsageListFragment
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.android.ext.android.inject

class MainActivity : AppCompatActivity() {

    private val tracker by inject<Tracker>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        navigateToFragment(MobileUsageListFragment.newInstance(1), false)
    }

    // Method to perform the fragment txn
    fun navigateToFragment(fragment: Fragment, addToBackStack: Boolean) {
        // Begin the transaction
        val fragmentTransaction = supportFragmentManager.beginTransaction()

        // Replace the contents of the container with the new fragment
        fragmentTransaction.add(R.id.frame_layout, fragment)

        if (addToBackStack) {
            // Replace the contents of the container with the new fragment
            fragmentTransaction.addToBackStack(fragment.tag)
        }

        // Complete the changes added above
        fragmentTransaction.commit()
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        tracker.onRotationEvent()
    }

    fun showLoading() {
        findViewById<ProgressBar>(R.id.progressBar).visibility = VISIBLE
    }

    fun hideLoading() {
        findViewById<ProgressBar>(R.id.progressBar).visibility = GONE
    }

    fun showMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}