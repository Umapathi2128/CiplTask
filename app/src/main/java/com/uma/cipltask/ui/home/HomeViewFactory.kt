package com.uma.cipltask.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.uma.cipltask.data.repository.DataManager
import com.uma.cipltask.utils.NetworkHelper
import java.lang.IllegalArgumentException
import javax.inject.Inject

class HomeViewFactory @Inject constructor(var dataManager: DataManager, var networkHelper: NetworkHelper)  : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)){
            return HomeViewModel(dataManager,networkHelper) as T
        }
        throw IllegalArgumentException("Unknown class exception")
    }
}