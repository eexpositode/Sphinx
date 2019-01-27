package com.myprojects.eduardoexposito.sphinx.ui.viewmodels

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.ViewModel
import org.koin.dsl.module.applicationContext

class ConnectivityViewModel(application: Application) : AndroidViewModel(application) {

    var connectivityLiveData = ConnectivityLiveData(application)
}
