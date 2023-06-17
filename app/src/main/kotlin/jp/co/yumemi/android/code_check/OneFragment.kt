/*
 * Copyright © 2021 YUMEMI Inc. All rights reserved.
 */
package jp.co.yumemi.android.code_check

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.*
import jp.co.yumemi.android.code_check.adapters.CustomAdapter
import jp.co.yumemi.android.code_check.databinding.FragmentOneBinding

import jp.co.yumemi.android.code_check.model.GithubRepositoryData

class OneFragment: Fragment(R.layout.fragment_one){

    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        super.onViewCreated(view, savedInstanceState)

        val binding= FragmentOneBinding.bind(view)

        val oneViewModel= OneViewModel(requireContext())

        val layoutManager= LinearLayoutManager(requireContext())
        val dividerItemDecoration=
            DividerItemDecoration(requireContext(), layoutManager.orientation)
        val adapter= CustomAdapter(object : CustomAdapter.OnItemClickListener{
            override fun itemClick(item: GithubRepositoryData){
                gotoRepositoryFragment(item)
            }
        })

        binding.searchInputText
            .setOnEditorActionListener{ editText, action, _ ->
                if (action== EditorInfo.IME_ACTION_SEARCH){
                    editText.text.toString().let {
                        oneViewModel.searchResults(it).apply{
                            adapter.submitList(this)
                        }
                    }
                    return@setOnEditorActionListener true
                }
                return@setOnEditorActionListener false
            }

        binding.recyclerView.also{
            it.layoutManager= layoutManager
            it.addItemDecoration(dividerItemDecoration)
            it.adapter= adapter
        }
    }

    fun gotoRepositoryFragment(item: GithubRepositoryData)
    {
        val action= OneFragmentDirections
            .actionRepositoriesFragmentToRepositoryFragment(item= item)
        findNavController().navigate(action)
    }
}

val diff_util= object: DiffUtil.ItemCallback<GithubRepositoryData>(){
    override fun areItemsTheSame(oldItem: GithubRepositoryData, newItem: GithubRepositoryData): Boolean
    {
        return oldItem.name== newItem.name
    }

    override fun areContentsTheSame(oldItem: GithubRepositoryData, newItem: GithubRepositoryData): Boolean
    {
        return oldItem == newItem
    }

}


