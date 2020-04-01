package com.qifan.powerpermission

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.qifan.powerpermission.activity.ExampleActivity
import com.qifan.powerpermission.childfragment.ExampleChildContainerFragment
import com.qifan.powerpermission.coroutines.CoroutinesActivity
import com.qifan.powerpermission.databinding.ActivityMainBinding
import com.qifan.powerpermission.fragment.ExampleFragment
import com.qifan.powerpermission.rx.RxJava2Activity
import com.qifan.powerpermission.rx.RxJava3Activity

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val requestActivityButton get() = binding.requestActivity
    private val requestFragmentButton get() = binding.requestFragment
    private val requestChildFragmentButton get() = binding.requestChildFragment
    private val requestRxJava2Button get() = binding.requestRxjava2Activity
    private val requestRxJava3Button get() = binding.requestRxjava3Activity
    private val requestCoroutineButton get() = binding.requestCoroutineActivity
    private val requestLiveDataButton get() = binding.requestLivedataActivity
    private val requestFragmentContainer get() = binding.exampleFragmentContainer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        requestActivityButton.setOnClickListener { navigateToRequestActivity() }
        requestFragmentButton.setOnClickListener { navigateToRequestFragment() }
        requestChildFragmentButton.setOnClickListener { navigateToRequestChildFragment() }
        requestRxJava2Button.setOnClickListener { navigateToRxJava2Activity() }
        requestRxJava3Button.setOnClickListener { navigateToRxJava3Activity() }
        requestCoroutineButton.setOnClickListener { navigateToCoroutineActivity() }
        requestLiveDataButton.setOnClickListener { navigateToLiveDataActivity() }
    }

    private fun navigateToLiveDataActivity() {
        Intent(this@MainActivity, CoroutinesActivity::class.java).apply {
            startActivity(this)
        }
    }

    private fun navigateToCoroutineActivity() {
        Intent(this@MainActivity, CoroutinesActivity::class.java).apply {
            startActivity(this)
        }
    }

    private fun navigateToRxJava3Activity() {
        Intent(this@MainActivity, RxJava3Activity::class.java).apply {
            startActivity(this)
        }
    }

    private fun navigateToRxJava2Activity() {
        Intent(this@MainActivity, RxJava2Activity::class.java).apply {
            startActivity(this)
        }
    }

    private fun navigateToRequestActivity() {
        Intent(this@MainActivity, ExampleActivity::class.java).apply {
            startActivity(this)
        }
    }

    private fun navigateToRequestFragment() {
        displayFragment(show = true)
        supportFragmentManager.beginTransaction()
            .add(R.id.example_fragment_container, ExampleFragment())
            .addToBackStack("RequestFragment")
            .commit()
    }

    private fun navigateToRequestChildFragment() {
        displayFragment(show = true)
        supportFragmentManager.beginTransaction()
            .add(R.id.example_fragment_container, ExampleChildContainerFragment())
            .addToBackStack("RequestChildFragment")
            .commit()
    }

    private fun displayFragment(show: Boolean) {
        if (show) {
            requestFragmentContainer.visibility = View.VISIBLE
            requestActivityButton.visibility = View.GONE
            requestFragmentButton.visibility = View.GONE
            requestChildFragmentButton.visibility = View.GONE
            requestRxJava2Button.visibility = View.GONE
            requestRxJava3Button.visibility = View.GONE
            requestCoroutineButton.visibility = View.GONE
            requestLiveDataButton.visibility = View.GONE
        } else {
            requestFragmentContainer.visibility = View.GONE
            requestActivityButton.visibility = View.VISIBLE
            requestFragmentButton.visibility = View.VISIBLE
            requestChildFragmentButton.visibility = View.VISIBLE
            requestRxJava2Button.visibility = View.VISIBLE
            requestRxJava3Button.visibility = View.VISIBLE
            requestCoroutineButton.visibility = View.VISIBLE
            requestLiveDataButton.visibility = View.VISIBLE
            supportActionBar?.setTitle(R.string.app_name)
        }
    }

    override fun onBackPressed() {
        if (supportFragmentManager.fragments.isNotEmpty()) {
            displayFragment(show = false)
            supportFragmentManager.popBackStack()
        } else {
            super.onBackPressed()
        }
    }
}
