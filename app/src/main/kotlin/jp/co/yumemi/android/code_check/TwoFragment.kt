/*
 * Copyright © 2021 YUMEMI Inc. All rights reserved.
 */
package jp.co.yumemi.android.code_check

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import coil.load
import jp.co.yumemi.android.code_check.databinding.FragmentTwoBinding

class TwoFragment : Fragment(R.layout.fragment_two) {

    private val args: TwoFragmentArgs by navArgs()

    private var binding: FragmentTwoBinding? = null
    private val _binding get() = binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //Log.d("検索した日時", lastSearchDate.toString())//null pointer

        binding = FragmentTwoBinding.bind(view)
        // Retrieve the passed item from the arguments
        var item = args.item
        // Load the owner's avatar image
        _binding.ownerIconView.load(item.owner?.avatarUrl);
        // Set the repository name
        _binding.nameView.text = item.name;
        // Set the programming language
        _binding.languageView.text = item.language;
        // Display the number of stars
        _binding.starsView.text = "${item.stargazersCount} stars";
        // Display the number of watchers
        _binding.watchersView.text = "${item.watchersCount} watchers";
        // Display the number of forks
        _binding.forksView.text = "${item.forksCount} forks";
        // Display the number of open issues
        _binding.openIssuesView.text = "${item.openIssuesCount} open issues";
    }
}
