package com.uma.cipltask.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.uma.cipltask.data.model.GitRepoResponse
import com.uma.cipltask.data.model.GitRepoResponseItem
import com.uma.cipltask.data.repository.DataManager
import com.uma.cipltask.ui.home.adapter.GitRepoDataSource
import com.uma.cipltask.utils.NetworkHelper
import com.uma.cipltask.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

class HomeViewModel @Inject constructor(var dataManager: DataManager, var networkHelper: NetworkHelper) : ViewModel() {

    private val _repoList = MutableLiveData<Resource<GitRepoResponse>>()
    val repoList: LiveData<Resource<GitRepoResponse>>
        get() = _repoList

     fun callApi() : Flow<PagingData<GitRepoResponseItem>>? {
         return if (networkHelper.isNetworkConnected()) {
             Pager(PagingConfig(pageSize = 10)) {
                 GitRepoDataSource(dataManager)
             }.flow.cachedIn(viewModelScope)
         }else{
             _repoList.postValue(Resource.error(data = null, msg = "No internet"))
             null
         }
    }
}