package com.uma.cipltask.ui.news

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.uma.cipltask.R
import com.uma.cipltask.data.model.Article
import com.uma.cipltask.data.network.ApiHelper
import com.uma.cipltask.data.repository.DataManager
import com.uma.cipltask.databinding.FragmentNewsBinding
import com.uma.cipltask.ui.news.adapter.NewsListAdapter
import com.uma.cipltask.utils.NetworkHelper
import com.uma.cipltask.utils.Status
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject

class NewsFragment : Fragment(), AdapterView.OnItemSelectedListener {

    private lateinit var viewModel: NewsListViewModel
    @Inject
    lateinit var viewFactory: NewsListViewFactory
    private lateinit var newsListAdapter: NewsListAdapter
    lateinit var countryList: Array<String>
    lateinit var categoryList: Array<String>
    var country = "in"
    var category = "technology"
    lateinit var binding: FragmentNewsBinding


    override fun onAttach(context: Context) {
        super.onAttach(context)
        AndroidSupportInjection.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_news, container, false)
        viewModel =
            ViewModelProviders.of(
                this,
                viewFactory
            ).get(NewsListViewModel::class.java)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.callNewsListApi(country, category)
        setupRecyclerView()
        setUpSpinner()
        setupObservables()
        binding.spCategory.onItemSelectedListener = this
        binding.spCountry.onItemSelectedListener = this
    }

    private fun setupRecyclerView() {
        newsListAdapter = NewsListAdapter()
        binding.recycleView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = newsListAdapter
        }
    }

    private fun setupObservables() {
        viewModel.newsList.observe(viewLifecycleOwner, Observer {
            when (it.status) {
                Status.LOADING -> {
                    binding.apply {
                        progressBar.show()
                        recycleView.hide()
                        txtError.hide()
                    }
                }
                Status.SUCCESS -> {
                    binding.apply {
                        progressBar.hide()
                        recycleView.show()
                        txtError.hide()
                    }
                    newsListAdapter.addItems(it.data?.articles as ArrayList<Article>)
                }
                Status.ERROR -> {
                    binding.apply {
                        progressBar.hide()
                        recycleView.hide()
                        txtError.show()
                        txtError.text = it.message
                    }
                }
                else -> {
                }
            }
        })
    }


    private fun setUpSpinner() {
        countryList = arrayOf("in", "us")
        val countryAdapter =
            context?.let { ArrayAdapter<CharSequence>(it, R.layout.spiner_text, countryList) }
        countryAdapter?.setDropDownViewResource(R.layout.spiner_drop_down)
        binding.spCountry.adapter = countryAdapter

        categoryList =
            arrayOf("technology", "entertainment", "business", "health", "science", "sports")
        val categoryAdapter =
            context?.let { ArrayAdapter<CharSequence>(it, R.layout.spiner_text, categoryList) }
        categoryAdapter?.setDropDownViewResource(R.layout.spiner_drop_down)
        binding.spCategory.adapter = categoryAdapter
    }

    private fun showSnack(text: String) {
        val snackbar = Snackbar
            .make(binding.recycleView, text, Snackbar.LENGTH_SHORT)
        snackbar.show()
    }

    private fun showSnackWithRetry(text: String) {
        val snackbar = Snackbar
            .make(binding.recycleView, text, Snackbar.LENGTH_INDEFINITE)
            .setAction("Retry") {
            }

        snackbar.show()
    }

    private fun View.show() {
        this.visibility = View.VISIBLE
    }

    private fun View.hide() {
        this.visibility = View.GONE
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        when (parent?.id) {
            R.id.spCountry -> {
                if (country == countryList[position]) {
                    return
                }
                country = countryList[position]
                viewModel.callNewsListApi(country, category)
            }
            R.id.spCategory -> {
                if (category == categoryList[position]) {
                    return
                }
                category = categoryList[position]
                viewModel.callNewsListApi(country, category)
            }
        }
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
    }


}