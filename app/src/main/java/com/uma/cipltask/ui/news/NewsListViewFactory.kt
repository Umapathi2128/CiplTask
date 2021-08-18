package com.uma.cipltask.ui.news

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.uma.cipltask.data.repository.DataManager
import com.uma.cipltask.utils.NetworkHelper
import java.lang.IllegalArgumentException
import javax.inject.Inject

class NewsListViewFactory @Inject constructor(var dataManager: DataManager,var networkHelper: NetworkHelper)  : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NewsListViewModel::class.java)){
            return NewsListViewModel(dataManager,networkHelper) as T
        }
        throw IllegalArgumentException("Unknown class exception")
    }
}