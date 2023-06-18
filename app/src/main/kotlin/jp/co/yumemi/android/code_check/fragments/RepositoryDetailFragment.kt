/*
 * Copyright © 2021 YUMEMI Inc. All rights reserved.
 */
package jp.co.yumemi.android.code_check.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import coil.load
import jp.co.yumemi.android.code_check.R
import jp.co.yumemi.android.code_check.databinding.RepositoryDetailBinding


/**
 * A fragment that displays the details of a repository.
 */
class RepositoryDetailFragment : Fragment(R.layout.repository_detail) {

    private val args: RepositoryDetailFragmentArgs by navArgs()

    private var binding: RepositoryDetailBinding? = null
    private val _binding get() = binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //Log.d("検索した日時", lastSearchDate.toString())//null pointer

        binding = RepositoryDetailBinding.bind(view)
        // Retrieve the passed item from the arguments
        val item = args.item
        // Load the owner's avatar image
        _binding.ownerIconView.load(item.owner?.avatarUrl)
        // Set the repository name
        _binding.nameView.text = item.name
        // Set the programming language
        _binding.languageView.text = item.language
        // Display the number of stars
        _binding.starsView.text = getString(R.string.stars_count, item.stargazersCount)
        // Display the number of watchers
        _binding.watchersView.text = getString(R.string.watchers_count, item.watchersCount)
        // Display the number of forks
        _binding.forksView.text = getString(R.string.forks_count, item.forksCount)
        // Display the number of open issues
        _binding.openIssuesView.text = getString(R.string.open_issues_count, item.openIssuesCount)
    }
}
