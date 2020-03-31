package com.qifan.powerpermission

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.qifan.powerpermission.activity.ExampleActivity
import com.qifan.powerpermission.databinding.ActivityMainBinding
import com.qifan.powerpermission.fragment.ExampleFragment

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val requestActivityButton get() = binding.requestActivity
    private val requestFragmentButton get() = binding.requestFragment
    private val requestChildFragmentButton get() = binding.requestChildFragment
    private val requestFragmentContainer get() = binding.exampleFragmentContainer


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        requestActivityButton.setOnClickListener { navigateToRequestActivity() }
        requestFragmentButton.setOnClickListener { navigateToRequestFragment() }
        requestChildFragmentButton.setOnClickListener { navigateToRequestChildFragment() }
    }

    private fun navigateToRequestActivity() {
        Intent(this@MainActivity, ExampleActivity::class.java).apply {
            startActivity(this)
        }
    }

    private fun navigateToRequestFragment() {
        requestFragmentContainer.visibility = View.VISIBLE
        requestActivityButton.visibility = View.GONE
        requestFragmentButton.visibility = View.GONE
        requestChildFragmentButton.visibility = View.GONE
        supportFragmentManager.beginTransaction()
            .add(R.id.example_fragment_container, ExampleFragment())
            .commit()
    }

    private fun navigateToRequestChildFragment() {
        //TODO("not implemented yet")
        supportFragmentManager.beginTransaction()
            .add(R.id.example_fragment_container, ExampleFragment())
            .commit()
    }
}
