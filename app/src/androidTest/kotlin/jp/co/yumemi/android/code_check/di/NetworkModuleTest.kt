package jp.co.yumemi.android.code_check.di

import androidx.test.runner.AndroidJUnit4
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import jp.co.yumemi.android.code_check.sources.GithubRepositoryApiService
import jp.co.yumemi.android.code_check.ui.search.SearchViewModel
import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import retrofit2.Converter
import retrofit2.Retrofit


@RunWith(AndroidJUnit4::class)
class NetworkModuleTest {

    private lateinit var networkModule: NetworkModule

    @Before
    fun setUp() {
        networkModule = NetworkModule
    }

    @Test
    fun testprovideBaseUrl() {
        val expectedBaseUrl = "https://api.github.com/search/"
        val actualBaseUrl = networkModule.provideBaseUrl()

        // Add an assertion to check the returned baseUrl
        assertEquals(expectedBaseUrl, actualBaseUrl)
    }

    @Test
    fun testprovideConverterFactory() {
        val converterFactory = networkModule.provideConverterFactory()

        // Add an assertion to check that the returned converterFactory is not null
        assertNotNull(converterFactory)
    }

    @Test
    fun testprovideHttpClient() {
        val httpClient = networkModule.provideHttpClient()

        // Add an assertion to check that the returned httpClient is not null
        assertNotNull(httpClient)
    }

    @Test
    fun testprovideRetrofit() {
        val baseUrl = "https://api.github.com/search/"
        val okHttpClient = mock<OkHttpClient>()
        val converterFactory = mock<Converter.Factory>()

        val retrofit = networkModule.provideRetrofit(baseUrl, okHttpClient, converterFactory)

        // Add assertions to check that the baseUrl and other configuration options are properly set on Retrofit
        assertEquals(baseUrl, retrofit.baseUrl().toString())

    }

    @Test
    fun testprovideGithubApiService() {
        val retrofit = mock<Retrofit>()

        networkModule.provideGithubApiService(retrofit)
        val githubApiService = networkModule.provideGithubApiService(retrofit)

        // Add an assertion to check that the returned GithubRepositoryApiService is not null
        assertNotNull(githubApiService)
        // Add assertions to check that the returned GithubRepositoryApiService is not null

    }

    @Test
    fun testprovideGithubRepository() {
        val githubRepositoryApiService = mock<GithubRepositoryApiService>()

        val githubRepository =  networkModule.provideGithubRepository(githubRepositoryApiService)
        assertNotNull(githubRepository)
        // Add assertions to check that the returned GithubRepository is not null

    }


}
