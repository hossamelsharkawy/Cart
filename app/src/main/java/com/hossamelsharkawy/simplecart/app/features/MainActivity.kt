package com.hossamelsharkawy.simplecart.app.features

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.elsharkawe.base.extension.gone
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.hossamelsharkawy.simplecart.R
import com.hossamelsharkawy.simplecart.domain.ICartRepository
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var navController: NavController

    @Inject
    lateinit var repo: ICartRepository


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.toolbar))

        navController = findNavController(R.id.nav_host_fragment)

        findViewById<FloatingActionButton>(R.id.fab).gone()
        /*findViewById<FloatingActionButton>(R.id.fab).setOnClickListener { view ->
            //navController.navigate(R.id.action_ProductsFragment_to_bottomSheet)
        }*/

    }


}

