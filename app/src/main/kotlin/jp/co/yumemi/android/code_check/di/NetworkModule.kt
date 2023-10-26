package jp.co.yumemi.android.code_check.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import jp.co.yumemi.android.code_check.sources.GithubRepositoryApiService
import jp.co.yumemi.android.code_check.common.Constants.BASE_URL
import jp.co.yumemi.android.code_check.repository.GithubRepository
import okhttp3.OkHttpClient
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    /**
     * Provides the base URL used for network requests.
     */
    @Singleton
    @Provides
    fun provideBaseUrl(): String {
        return BASE_URL
    }

    /**
     * Provides the converter factory used for JSON serialization and deserialization.
     */
    @Singleton
    @Provides
    fun provideConverterFactory(): Converter.Factory {
        return GsonConverterFactory.create()
    }

    /**
     * Provides the HTTP client used for making network requests.
     */
    @Singleton
    @Provides
    fun provideHttpClient(): OkHttpClient {
        val okHttpClient = OkHttpClient.Builder()
        return okHttpClient.build()
    }

    /**
     * Provides the implementation of the GithubRepositoryApiService interface.
     * It depends on the Retrofit instance.
     */
    @Singleton
    @Provides
    fun provideRetrofit(
        baseUrl: String,
        okHttpClient: OkHttpClient,
        converterFactory: Converter.Factory,
    ): Retrofit {
        val retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(converterFactory)
            .client(okHttpClient)

        return retrofit.build()
    }

    /**
     * Provides the implementation of the GithubRepositoryApiService interface.
     * It depends on the Retrofit instance.
     */
    @Singleton
    @Provides
    fun provideGithubApiService(retrofit: Retrofit): GithubRepositoryApiService {
        return retrofit.create(GithubRepositoryApiService::class.java)
    }

    /**
     * Provides the GithubRepository instance.
     * It depends on the GithubRepositoryApiService.
     */
    @Singleton
    @Provides
    fun provideGithubRepository(githubRepositoryApiService: GithubRepositoryApiService): GithubRepository {
        return GithubRepository(githubRepositoryApiService)
    }


}