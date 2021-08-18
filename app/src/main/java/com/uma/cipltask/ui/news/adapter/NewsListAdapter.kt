package com.uma.cipltask.ui.news.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.uma.cipltask.R
import com.uma.cipltask.data.model.Article
import com.uma.cipltask.databinding.ItemInflateBinding
import com.uma.cipltask.utils.Constant
import com.uma.cipltask.utils.Utils
import javax.inject.Inject

class NewsListAdapter @Inject constructor() :
    RecyclerView.Adapter<NewsListAdapter.NewsListViewHolder>() {

    private lateinit var context: Context
    private lateinit var binding: ItemInflateBinding
    private var newsList: ArrayList<Article> = arrayListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsListViewHolder {
        context = parent.context

        binding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_inflate,
            parent,
            false
        )
        return NewsListViewHolder(binding)
    }

    override fun getItemCount() = newsList.size

    override fun onBindViewHolder(holder: NewsListViewHolder, position: Int) {
        holder.onBind(position)
    }

    fun addItems(list: ArrayList<Article>) {
        newsList.apply {
            clear()
        }
        newsList = list
        notifyDataSetChanged()
    }

    inner class NewsListViewHolder(var v: ItemInflateBinding) : RecyclerView.ViewHolder(v.root) {
        fun onBind(position: Int) {
            v.inflateBinding = newsList[position]

            Glide.with(v.imgProfile.context).load(newsList[position].urlToImage)
                .apply(RequestOptions.circleCropTransform())
                .placeholder(Utils.getImagePlaceHolderLoading(v.imgProfile.context))
                .into(v.imgProfile)
        }
    }

//    interface NewsItemClickListener {
//        fun onClickNewsListener(data: Article, id: ImageView)
//    }
}