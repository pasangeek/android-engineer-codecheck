/*
 * Copyright © 2021 YUMEMI Inc. All rights reserved.
 */
package jp.co.yumemi.android.code_check.ui.search

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import jp.co.yumemi.android.code_check.common.ErrorState
import jp.co.yumemi.android.code_check.data.model.GithubRepositoryData
import jp.co.yumemi.android.code_check.databinding.RepositorySearchBinding
import jp.co.yumemi.android.code_check.ui.adapters.GithubRepositoryDetailAdapter
import jp.co.yumemi.android.code_check.ui.error.ErrorDialog


/**
 * A Fragment that displays a list of GitHub repositories and handles user interactions.
 */
@AndroidEntryPoint
class SearchFragment : Fragment() {

    private var binding: RepositorySearchBinding? = null
    lateinit var viewModel: SearchViewModel
    private lateinit var githubRepositoryDetailAdapter: GithubRepositoryDetailAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflating the layout for this fragment
        binding = RepositorySearchBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this)[SearchViewModel::class.java]
        binding!!.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding?.vm = viewModel
        binding?.lifecycleOwner = viewLifecycleOwner
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initializeRecycleViewAdapter()
        initiateGithubAccountAdapter()
        initializeErrorDialog()
        initializeProgressBarAndAnimation()

    }

    private fun initializeSearch() {
        binding?.searchInputText?.setOnEditorActionListener { editText, action, _ ->
            if (action == EditorInfo.IME_ACTION_SEARCH) {
                // Get the text from the input field
                val searchText = editText.text.toString()

                if (searchText.isEmpty()) {
                    // Show a message if the search input is empty
                    viewModel.errorState.value = ErrorState.NetworkError("search input is empty")
                } else {
                    // Trigger a search when the search action is performed
                    viewModel.searchResults(searchText)
                    logMessage("Search initiated with query: $searchText")

                    // Hide the keyboard after initiating the search
                    hideKeyboard()
                }
                return@setOnEditorActionListener true
            }
            return@setOnEditorActionListener false
        }
    }

    private fun initializeRecycleViewAdapter() {
        // Initializing the RecyclerView adapter
        this.githubRepositoryDetailAdapter = GithubRepositoryDetailAdapter(object :
            GithubRepositoryDetailAdapter.OnItemClickListener {
            override fun itemClick(repo: GithubRepositoryData) {
                gotoRepositoryFragment(repo)
                logMessage("GitHub repository list updated")
            }
        })

    }

    private fun initiateGithubAccountAdapter() {

// Setting the RecyclerView adapter
        binding?.recyclerView?.adapter = githubRepositoryDetailAdapter
// Observing changes in the GitHub repository list and updating the adapter
        viewModel.gitHubRepositoryList.observe(viewLifecycleOwner) {
            githubRepositoryDetailAdapter.submitList(it)
            // Check if the list is empty and set the visibility of the "empty" layout
            Log.d("SearchFragment", "GitHub repository list updated")
        }

        initializeSearch()
    }

    private fun initializeErrorDialog() {
        viewModel.errorLiveData.observe(viewLifecycleOwner) { errorState ->
            when (errorState) {
                is ErrorState.NetworkError -> {
                    val dialogFragment = ErrorDialog(errorState.message, viewModel)
                    dialogFragment.show(childFragmentManager, "NetworkErrorDialog")
                }

                is ErrorState.InputError -> {

                    binding?.errorMessageTextView?.text = errorState.message
                }
            }

            // Handle other error states as needed
        }
    }


    private fun initializeProgressBarAndAnimation() {
        // Observe the loading state and show/hide the progress bar
        viewModel.loading.observe(viewLifecycleOwner) { isLoading ->
            if (isLoading) {
                // Show the progress bar
                binding!!.progressBar.visibility = View.VISIBLE
                binding!!.animationView.visibility = View.VISIBLE
            } else {
                // Hide the progress bar
                binding!!.progressBar.visibility = View.GONE
                binding!!.animationView.visibility = View.GONE
            }
        }
    }

    private fun hideKeyboard() {
        val imm =
            requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(binding?.searchInputText?.windowToken, 0)
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



