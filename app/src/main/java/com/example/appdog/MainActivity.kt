package com.example.appdog

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.example.appdog.databinding.ActivityMainBinding
import com.example.appdog.viewmodel.DogViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var mBinding : ActivityMainBinding
    val viewModel : DogViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mBinding.root)

        launchFragment()

    }

    private fun launchFragment() {
        supportFragmentManager.beginTransaction()
            .replace(mBinding.frame.id, BreedFragment())
            .addToBackStack(null)
            .commit()
    }


}