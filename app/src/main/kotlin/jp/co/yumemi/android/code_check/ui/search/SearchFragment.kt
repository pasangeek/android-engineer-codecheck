/*
 * Copyright © 2021 YUMEMI Inc. All rights reserved.
 */
package jp.co.yumemi.android.code_check.ui.search

import android.os.Bundle
import android.util.Log
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
        // Inflating the layout for this fragment
        binding = RepositorySearchBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Initializing the ViewModel
        viewModel = ViewModelProvider(this)[SearchViewModel::class.java]
        // Setting ViewModel for data binding
        binding?.vm = viewModel
        binding?.lifecycleOwner = viewLifecycleOwner

// Setting up a listener for the search input field
        binding?.searchInputText?.setOnEditorActionListener { editText, action, _ ->
            if (action == EditorInfo.IME_ACTION_SEARCH) {
                // Trigger a search when the search action is performed
                editText.text.toString().let {
                    viewModel.searchResults(it)
                    logMessage("Search initiated with query: $it")
                }
                return@setOnEditorActionListener true
            }
            return@setOnEditorActionListener false
        }
        // Initializing the RecyclerView adapter
        githubRepositoryDetailAdapter = GithubRepositoryDetailAdapter(object :
            GithubRepositoryDetailAdapter.OnItemClickListener {
            override fun itemClick(item: GithubRepositoryData) {
                gotoRepositoryFragment(item)
                logMessage("GitHub repository list updated")
            }
        })

// Setting the RecyclerView adapter
        binding?.recyclerView?.adapter = githubRepositoryDetailAdapter
// Observing changes in the GitHub repository list and updating the adapter
        viewModel.gitHubRepositoryList.observe(viewLifecycleOwner) {
            githubRepositoryDetailAdapter.submitList(it)
            Log.d("SearchFragment", "GitHub repository list updated")
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
        logMessage("Navigating to RepositoryDetailFragment with item: ${item.name}")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        // Clearing the binding reference
        binding = null
        logMessage("View destroyed")
    }
    // Helper function for logging messages with a specified tag
    private fun logMessage(message: String) {
        Log.d("SearchFragment", message)
    }
}




