package com.uma.cipltask.ui.home.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.EditText
import androidx.databinding.DataBindingUtil
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.uma.cipltask.R
import com.uma.cipltask.data.model.GitRepoResponseItem
import com.uma.cipltask.databinding.RowRepoListBinding
import com.uma.cipltask.ui.home.HomeModel
import com.uma.cipltask.utils.Constant
import com.uma.cipltask.utils.Utils

class HomeAdapter(var itemClickListener: ItemClickListener) :
    PagingDataAdapter<GitRepoResponseItem, HomeAdapter.MyViewHolder>(GitRepoResponseItemsComparator) {

    lateinit var binding: RowRepoListBinding
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder {

        binding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context), R.layout.row_repo_list, parent, false
        )
        return MyViewHolder(
            binding
        )
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item = getItem(position)
        item?.let { holder.onBind(it) }

        val txt = holder.itemView.findViewById<EditText>(R.id.etxtDetails)
        holder.itemView.setOnClickListener {
            val postData = txt.text.toString()
            item?.let { itemClickListener.onItemClickListener(it,postData) }
        }
    }

    inner class MyViewHolder(private val binding: RowRepoListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun onBind(item: GitRepoResponseItem) = with(binding) {
            binding.repoListBinding = HomeModel(item.name,item.description)

            Glide.with(this.imgProfile.context).load(item.owner.avatar_url)
                .apply(RequestOptions.circleCropTransform())
                .placeholder(Utils.getImagePlaceHolderLoading(this.imgProfile.context))
                .error(Constant.dummyImage)
                .into(this.imgProfile)
        }
    }

    object GitRepoResponseItemsComparator : DiffUtil.ItemCallback<GitRepoResponseItem>() {
        override fun areItemsTheSame(
            oldItem: GitRepoResponseItem,
            newItem: GitRepoResponseItem
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: GitRepoResponseItem,
            newItem: GitRepoResponseItem
        ): Boolean {
            return oldItem == newItem
        }
    }

    interface ItemClickListener {
        fun onItemClickListener(item: GitRepoResponseItem, postData: String)
    }
}