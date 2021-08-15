package com.uma.cipltask.ui.home

import android.os.Bundle
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import androidx.navigation.Navigation
import androidx.paging.LoadState
import androidx.paging.LoadState.NotLoading
import androidx.paging.PagingDataAdapter
import androidx.paging.PagingSource
import androidx.recyclerview.widget.LinearLayoutManager
import com.uma.cipltask.R
import com.uma.cipltask.data.model.GitRepoResponseItem
import com.uma.cipltask.data.repository.DataManager
import com.uma.cipltask.databinding.FragmentHomeBinding
import com.uma.cipltask.ui.SharedViewModel
import com.uma.cipltask.ui.home.adapter.HomeAdapter
import com.uma.cipltask.utils.NetworkHelper
import com.uma.cipltask.utils.Status
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch


class HomeFragment : Fragment(),HomeAdapter.ItemClickListener {

    lateinit var binding : FragmentHomeBinding
    lateinit var viewModel : HomeViewModel
    lateinit var homeAdapter : HomeAdapter
    lateinit var navController : NavController
    lateinit var model: SharedViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)
        viewModel =
            ViewModelProviders.of(this,
                context?.let { DataManager(it) }?.let {
                    HomeViewFactory(
                        it,
                        NetworkHelper(requireContext())
                    )
                }).get(HomeViewModel::class.java)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        model = ViewModelProvider(requireActivity()).get(SharedViewModel::class.java)
        navController =  Navigation.findNavController(view)
        setUpUi()
        setUpObservers()
        setupView()
    }

    private fun setUpUi() {
        homeAdapter = HomeAdapter(this)
        binding.recycleView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = homeAdapter
        }
    }

    private fun setupView() {
        homeAdapter.addLoadStateListener {
            if (it.refresh == LoadState.Loading){
                binding.progressBar.visibility = View.VISIBLE
                binding.recycleView.visibility = View.GONE
            }else{
                binding.progressBar.visibility = View.GONE
                binding.recycleView.visibility = View.VISIBLE
            }
        }

        lifecycleScope.launch {
            viewModel.callApi()?.collect {
                    homeAdapter.submitData(it)
                if (homeAdapter.itemCount == 0){
                    binding.progressBar.visibility = View.GONE
                    binding.recycleView.visibility = View.GONE
                    binding.txtRepoListError.visibility = View.VISIBLE
                    binding.txtRepoListError.text = "No data available"
                }
            }
        }
    }

    private fun setUpObservers() {
       viewModel.repoList.observe(viewLifecycleOwner, {
           when (it.status) {
               Status.ERROR -> {
                   binding.progressBar.visibility = View.GONE
                   binding.recycleView.visibility = View.GONE
                   binding.txtRepoListError.visibility = View.VISIBLE
                   binding.txtRepoListError.text = it.message
               }
               else -> {}
           }
       })
    }

    override fun onItemClickListener(item: GitRepoResponseItem,postData : String) {
        val bundle = Bundle()
        bundle.putSerializable("data",item)
        val navOptions = NavOptions.Builder().build()

        if (postData.isNotEmpty()) {
            navController.navigate(
                R.id.action_homeFragment_to_detailsFragment
            )
            model.sendItem(item)
            model.sendPostData(postData)
        }else{
            Toast.makeText(context,"Please enter comments",Toast.LENGTH_SHORT).show()
        }
    }
}