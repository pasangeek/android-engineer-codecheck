package jp.co.yumemi.android.code_check.ui.details

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.nhaarman.mockitokotlin2.argumentCaptor
import com.nhaarman.mockitokotlin2.verify
import jp.co.yumemi.android.code_check.data.model.GithubRepositoryData
import jp.co.yumemi.android.code_check.data.model.Owner
import org.hamcrest.CoreMatchers.equalTo
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.inOrder
import org.mockito.MockitoAnnotations

class RepositoryDetailViewModelTest {

    // Rule to make LiveData work on the main thread
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    // The ViewModel to be tested
    private lateinit var viewModel: RepositoryDetailViewModel

    // Mocked LiveData Observer for GitHub repository details
    @Mock
    private lateinit var githubRepositoryDetailsObserver: Observer<GithubRepositoryData>

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        viewModel = RepositoryDetailViewModel()

        // Observe the GitHub repository details LiveData
        viewModel.gitHubRepositoryDetails.observeForever(githubRepositoryDetailsObserver)
    }

    @Test
    fun testSetRepositoryDetails() {
        // Given a GitHub repository data
        val githubRepositoryData = GithubRepositoryData(
            name = "u",
            owner = Owner(avatarUrl = "https://avatars.githubusercontent.com/u/10928?v=4"),
            language = "Kotlin",
            stargazersCount = "100",
            watchersCount = "50",
            forksCount = "25",
            openIssuesCount = "10"
        )

        viewModel.setRepositoryDetails(githubRepositoryData)
        // Then verify that the LiveData is updated with the expected data
        Mockito.verify(githubRepositoryDetailsObserver).onChanged(githubRepositoryData)

    }
}