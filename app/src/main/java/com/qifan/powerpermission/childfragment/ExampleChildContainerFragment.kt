package com.qifan.powerpermission.childfragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.qifan.powerpermission.R
import com.qifan.powerpermission.databinding.FragmentChildContainerExampleBinding

class ExampleChildContainerFragment : Fragment() {
    private lateinit var binding: FragmentChildContainerExampleBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentChildContainerExampleBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        childFragmentManager.beginTransaction()
            .add(R.id.child_container, ExampleChildFragment())
            .commit()
    }
}
