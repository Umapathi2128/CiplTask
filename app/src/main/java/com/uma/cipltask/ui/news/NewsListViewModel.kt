package com.uma.cipltask.ui.news

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.uma.cipltask.data.model.NewsListResponse
import com.uma.cipltask.data.repository.DataManager
import com.uma.cipltask.utils.NetworkHelper
import com.uma.cipltask.utils.Resource
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import javax.inject.Inject


class NewsListViewModel @Inject constructor(private val dataManager: DataManager, var networkHelper: NetworkHelper) : ViewModel() {

    private var _newsList = MutableLiveData<Resource<NewsListResponse>>()
    val newsList: LiveData<Resource<NewsListResponse>>
        get() = _newsList

    private val couroutineExceptionHandling = CoroutineExceptionHandler { _, exception ->
        _newsList.postValue(Resource.error("Something went wrong",data = null))
    }

    fun callNewsListApi(country : String,category: String){
        viewModelScope.launch(couroutineExceptionHandling) {
            _newsList.postValue(Resource.loading(null))
            if (networkHelper.isNetworkConnected()){
                dataManager.getNewsList(country, category).let {
                    if (it.isSuccessful){
                        _newsList.postValue(Resource.success(it.body()))
                    }
                }
            }else {
                _newsList.postValue(Resource.error("No Internet",data = null))
            }
        }
    }
}