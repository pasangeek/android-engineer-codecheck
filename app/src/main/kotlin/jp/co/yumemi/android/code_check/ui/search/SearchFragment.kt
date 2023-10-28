/*
 * Copyright © 2021 YUMEMI Inc. All rights reserved.
 */
package jp.co.yumemi.android.code_check.ui.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.*
import dagger.hilt.android.AndroidEntryPoint

import jp.co.yumemi.android.code_check.ui.adapters.GithubRepositoryDetailAdapter
import jp.co.yumemi.android.code_check.databinding.RepositorySearchBinding

import jp.co.yumemi.android.code_check.data.model.GithubRepositoryData


/**
 * A Fragment that displays a list of GitHub repositories and handles user interactions.
 */
@AndroidEntryPoint
class SearchFragment : Fragment() {

    private var binding: RepositorySearchBinding? = null
    lateinit var viewModel: SearchViewModel
    lateinit var githubRepositoryDetailAdapter: GithubRepositoryDetailAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = RepositorySearchBinding.inflate(inflater, container, false)

        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[SearchViewModel::class.java]
        binding?.vm = viewModel
        binding?.lifecycleOwner = viewLifecycleOwner
        githubRepositoryDetailAdapter = GithubRepositoryDetailAdapter(object :
            GithubRepositoryDetailAdapter.OnItemClickListener {
            override fun itemClick(item: GithubRepositoryData) {
                gotoRepositoryFragment(item)
            }
        })

        binding?.searchInputText
            ?.setOnEditorActionListener { editText, action, _ ->
                if (action == EditorInfo.IME_ACTION_SEARCH) {
                    editText.text.toString().let {
                        viewModel.searchResults(it)
                    }
                    return@setOnEditorActionListener true
                }
                return@setOnEditorActionListener false
            }

        binding?.recyclerView?.adapter = githubRepositoryDetailAdapter
        viewModel.gitHubRepositoryList.observe(viewLifecycleOwner) {
            githubRepositoryDetailAdapter.submitList(it)
        }
    }

    /**
     * Navigate to the RepositoryFragment with the selected repository item.
     *
     * @param item The selected GithubRepositoryData item.
     */
    fun gotoRepositoryFragment(item: GithubRepositoryData) {
        val action =
            SearchFragmentDirections.actionOneFragmentToRepositoryDetailFragment(repositoryArgument = item)
        findNavController().navigate(action)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}

/**
 * DiffUtil.ItemCallback implementation for comparing [GithubRepositoryData] items in [GithubRepositoryDetailAdapter].
 */
val diff_util = object : DiffUtil.ItemCallback<GithubRepositoryData>() {
    override fun areItemsTheSame(
        oldItem: GithubRepositoryData,
        newItem: GithubRepositoryData
    ): Boolean {
        return oldItem.name == newItem.name
    }

    override fun areContentsTheSame(
        oldItem: GithubRepositoryData,
        newItem: GithubRepositoryData
    ): Boolean {
        return oldItem == newItem
    }
}


