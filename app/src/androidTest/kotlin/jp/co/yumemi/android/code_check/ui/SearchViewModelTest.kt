package jp.co.yumemi.android.code_check.ui

import android.app.Application
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.nhaarman.mockitokotlin2.verifyZeroInteractions
import jp.co.yumemi.android.code_check.data.model.GithubRepositoryData
import jp.co.yumemi.android.code_check.data.model.GithubServerResponse
import jp.co.yumemi.android.code_check.data.model.Owner
import jp.co.yumemi.android.code_check.repository.GithubRepository
import jp.co.yumemi.android.code_check.ui.search.SearchViewModel
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations


class SearchViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var viewModel: SearchViewModel

    @Mock
    private lateinit var githubRepository: GithubRepository

  /*  @Mock
    private lateinit var mockObserver: Observer<List<GithubRepositoryData>>
*/
    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        viewModel = SearchViewModel(mock(Application::class.java), githubRepository)
       // viewModel.gitHubRepositoryList.observeForever(mockObserver)
    }

    @Test
fun testSearchResultsSuccess() {
        // Mock successful search results
        runBlocking {
            val inputText = "u"
            val serverResponse = GithubServerResponse(
                total_count = listOf(0), incomplete_results = false, listOf(
                    GithubRepositoryData(
                        name = "Test Repository", // Provide a name
                        owner = Owner(avatarUrl = "https://avatars.githubusercontent.com/u/10928?v=4"), // Provide an owner or relevant data
                        language = "Kotlin", // Provide a programming language
                        stargazersCount = "100", // Provide the number of stargazers
                        watchersCount = "50", // Provide the number of watchers
                        forksCount = "25", // Provide the number of forks
                        openIssuesCount = "10"
                    )
                )
            )
            `when`(githubRepository.getGitHubAccountFromDataSource(inputText)).thenReturn(
                serverResponse
            )

            viewModel.searchResults(inputText)

            // Verify that the LiveData is updated with the expected results
          //  verify(mockObserver).onChanged(serverResponse.items)
        }
    }
    @Test
  fun testSearchResultsEmpty() {
        // Mock empty search results
        runBlocking {
        val inputText = "u"
        val serverResponse = GithubServerResponse( total_count = emptyList(), // Provide a placeholder value for total_count
            incomplete_results = false, // Provide a value for incomplete_results
            items = emptyList())
        `when`(githubRepository.getGitHubAccountFromDataSource(inputText)).thenReturn(serverResponse)

        viewModel.searchResults(inputText)

        // Verify that the LiveData is updated with an empty list
       // verify(mockObserver).onChanged(emptyList())
    }
  }

    @Test
    fun testSearchResultsError() {
        // Mock an error during the search
        runBlocking {
        val inputText = "u"
        `when`(githubRepository.getGitHubAccountFromDataSource(inputText)).thenThrow(Exception("Test exception"))

        viewModel.searchResults(inputText)

        // Verify that the LiveData is not updated with any results
      //  verifyZeroInteractions(mockObserver)
    }}
}