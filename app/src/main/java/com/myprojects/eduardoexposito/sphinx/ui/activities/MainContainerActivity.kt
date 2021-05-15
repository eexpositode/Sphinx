package com.myprojects.eduardoexposito.sphinx.ui.activities

import android.location.Location
import android.os.Bundle
import com.myprojects.eduardoexposito.sphinx.R
import kotlinx.android.synthetic.main.activity_main_container.*

class MainContainerActivity : BaseLocationActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_container)
    }

    //=====================================================================
    //=====================================================================
    override fun onLocationChanged(location: Location?) {
        location?.let { viewGameMainContainer.onLocationChanged(it) }
    }
}
