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
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import jp.co.yumemi.android.code_check.R
import jp.co.yumemi.android.code_check.data.model.GithubRepositoryData
import jp.co.yumemi.android.code_check.databinding.FragmentRepositoryDetailBinding
import jp.co.yumemi.android.code_check.ui.details.RepositoryDetailViewModel


@AndroidEntryPoint
class RepositoryDetailFragment : Fragment() {
    private val args: RepositoryDetailFragmentArgs by navArgs()
    private var binding: FragmentRepositoryDetailBinding? = null // Change to nullable
    lateinit var viewModel: RepositoryDetailViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRepositoryDetailBinding.inflate(inflater, container, false)
        return binding?.root ?: inflater.inflate(R.layout.fragment_repository_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[RepositoryDetailViewModel::class.java]
        binding?.detailsVM = viewModel
        binding?.lifecycleOwner = viewLifecycleOwner

        viewModel.setRepositoryDetails(args.repositoryArgument)

        viewModel.gitHubRepositoryDetails.observe(viewLifecycleOwner) { it ->
            it?.let {
                try {
                    binding?.ownerIconView?.let { image_view ->
                        Glide.with(this)
                            .load(it.owner?.avatarUrl)
                            .error(R.drawable.no_image_background) // Use a placeholder for error
                            .into(image_view)
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                    // Handle the exception, e.g., show an error message or log it.
                    Snackbar.make(view, "Image loading error: ${e.message}", Snackbar.LENGTH_LONG).show()
                }
            }
        }
        }


    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}