package com.example.mobiledev.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.mobiledev.HomeFragment
import com.example.mobiledev.R
import com.example.mobiledev.fragments.LoginFragment
import com.example.mobiledev.fragments.LoginRegisterFragment
import com.example.mobiledev.fragments.OnboardFragment
import com.example.mobiledev.fragments.RegisterFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            navigateToLoginRegister()
        }
    }

    fun navigateToOnboard() {
        val fragment = OnboardFragment()
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.main_activity_fragment_container, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }

    fun navigateToLoginRegister() {
        val fragment = LoginRegisterFragment()
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.main_activity_fragment_container, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }


    fun navigateToLogin() {
        val fragment = LoginFragment()
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.main_activity_fragment_container, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }

    fun navigateToLogin(fragment: LoginFragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.main_activity_fragment_container, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }


    fun navigateToRegister() {
        val fragment = RegisterFragment()
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.main_activity_fragment_container, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }

    fun navigateToHome() {
        val fragment = HomeFragment()
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.main_activity_fragment_container, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }

}