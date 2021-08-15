package com.uma.cipltask.ui.details

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.transition.ChangeBounds
import com.bumptech.glide.Glide
import com.uma.cipltask.R
import com.uma.cipltask.data.model.GitRepoResponseItem
import com.uma.cipltask.databinding.FragmentDetailsBinding
import com.uma.cipltask.ui.SharedViewModel

class DetailsFragment : Fragment() {

    lateinit var binding: FragmentDetailsBinding

    var item: GitRepoResponseItem? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_details, container, false)
        return binding.root
    }


    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val model = ViewModelProvider(requireActivity()).get(SharedViewModel::class.java)
        model.item.observe(viewLifecycleOwner, Observer { item ->
            if (item.name.isEmpty()) {
                binding.groupDetailsScreen.visibility = View.GONE
                binding.txtNoData.visibility = View.VISIBLE
            } else {
                binding.txtNoData.visibility = View.GONE
                binding.groupDetailsScreen.visibility = View.VISIBLE
                binding.txtTitle.text = "Name : " + item.name
                binding.txtFullName.text = "Full Name : " + item.full_name
                binding.txtDescription.text = "Description : " + item.description
                binding.txtEventUrl.text = "Event_url : " + item.events_url
                binding.txtRepoUrl.text = "Repo_url : " + item.owner.repos_url
                binding.txtType.text = "Type : " + item.owner.type

                context?.let {
                    Glide.with(it).load(item.owner.avatar_url)
                        .error(R.drawable.ic_launcher_background)
                        .into(binding.imageProfile)
                }
            }
        })

        model.postData.observe(viewLifecycleOwner, Observer {
            binding.txtPostData.text = "Post details : $it"
        })
    }
}