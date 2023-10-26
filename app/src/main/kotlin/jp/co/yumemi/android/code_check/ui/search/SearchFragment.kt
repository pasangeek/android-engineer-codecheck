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

import jp.co.yumemi.android.code_check.adapters.CustomAdapter
import jp.co.yumemi.android.code_check.databinding.RepositorySearchBinding

import jp.co.yumemi.android.code_check.data.model.GithubRepositoryData


/**
 * A Fragment that displays a list of GitHub repositories and handles user interactions.
 */
class OneFragment : Fragment() {


    lateinit var _binding: RepositorySearchBinding
    lateinit var viewModel: SearchViewModel
    lateinit var customAdapter: CustomAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = RepositorySearchBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(requireActivity())[SearchViewModel::class.java]
        _binding.vm = viewModel
        _binding.lifecycleOwner = this

        return _binding.root

    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        customAdapter = CustomAdapter(object : CustomAdapter.OnItemClickListener {
            override fun itemClick(item: GithubRepositoryData) {
                gotoRepositoryFragment(item)
            }
        })

        _binding.searchInputText
            .setOnEditorActionListener { editText, action, _ ->
                if (action == EditorInfo.IME_ACTION_SEARCH) {
                    editText.text.toString().let {
                        viewModel.searchResults(it)
                    }
                    return@setOnEditorActionListener true
                }
                return@setOnEditorActionListener false
            }

        _binding.recyclerView.adapter = customAdapter
        viewModel.gitHubRepositoryList.observe(requireActivity()) {
            customAdapter.submitList(it)
        }
    }

    /**
     * Navigate to the RepositoryFragment with the selected repository item.
     *
     * @param item The selected GithubRepositoryData item.
     */
    fun gotoRepositoryFragment(item: GithubRepositoryData) {
        val action = OneFragmentDirections.actionRepositoriesFragmentToRepositoryFragment(item)
        findNavController().navigate(action)
    }
}

/**
 * DiffUtil.ItemCallback implementation for comparing [GithubRepositoryData] items in [CustomAdapter].
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


