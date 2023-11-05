package jp.co.yumemi.android.code_check.ui.details

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment.Companion.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import jp.co.yumemi.android.code_check.R
import jp.co.yumemi.android.code_check.databinding.FragmentRepositoryDetailBinding


@AndroidEntryPoint
class RepositoryDetailFragment : Fragment() {
    private val args: RepositoryDetailFragmentArgs by navArgs()
    private var binding: FragmentRepositoryDetailBinding? = null // Change to nullable
    private lateinit var viewModel: RepositoryDetailViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRepositoryDetailBinding.inflate(inflater, container, false)
        // Log that the fragment has been created
        logMessage("Fragment created")
        // Return the root view from data binding or the fallback layout
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initialize the ViewModel
        this.viewModel = ViewModelProvider(this)[RepositoryDetailViewModel::class.java]

        // Set the ViewModel for data binding
        binding?.detailsVM = viewModel
        binding?.lifecycleOwner = viewLifecycleOwner

        // Set the repository details in the ViewModel and log the action
        viewModel.setRepositoryDetails(args.repositoryArgument)
        logMessage("Repository details set")

        observeRepositoryDetail()
        backButton()

    }

    private fun backButton() {
        binding?.backButton?.setOnClickListener{
            //navigate back to the previous fragment
            findNavController().navigateUp()
        }
    }

    private fun observeRepositoryDetail() {
        // Observe changes in GitHub repository details and load the owner's avatar
        viewModel.gitHubRepositoryDetails.observe(viewLifecycleOwner) { it ->
            it?.let {
                try {
                    binding?.ownerIconView?.let { image_view ->
                        // Load the owner's avatar using Glide
                        Glide.with(this)
                            .load(it.owner?.avatarUrl)
                            .error(R.drawable.no_image_background) // Use a placeholder for error
                            .into(image_view)
                        logMessage("Image loaded")


                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                    // Handle the exception, e.g., show an error message or log it.
                    logMessage("NetworkError loading image: ${e.message}")
                    view?.let { it1 ->
                        Snackbar.make(
                            it1,
                            "Image loading error: ${e.message}",
                            Snackbar.LENGTH_LONG
                        ).show()
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()

        // Clear the binding reference
        binding = null

        // Log that the view has been destroyed
        logMessage("View destroyed")
    }

    // Helper function for logging messages with a specified tag
    private fun logMessage(message: String) {
        Log.d("RepositoryDetailFragment", message)
    }
}