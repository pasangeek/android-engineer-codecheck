package jp.co.yumemi.android.code_check.ui.details

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint
import jp.co.yumemi.android.code_check.data.model.GithubRepositoryData
import jp.co.yumemi.android.code_check.databinding.FragmentRepositoryDetailBinding
import jp.co.yumemi.android.code_check.ui.details.RepositoryDetailViewModel


@AndroidEntryPoint
class RepositoryDetailFragment : Fragment() {
    private val args : RepositoryDetailFragmentArgs by navArgs()
    private lateinit var binding: FragmentRepositoryDetailBinding
    lateinit var viewModel: RepositoryDetailViewModel




    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRepositoryDetailBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[RepositoryDetailViewModel::class.java]
        binding.detailsVM = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        viewModel.setRepositoryDetails(args.repositoryArgument)

        viewModel.gitHubRepositoryDetails.observe(viewLifecycleOwner){

           it.let { Glide.with(this).load(it.owner?.avatarUrl).into(binding.ownerIconView) }
        }

    }




}