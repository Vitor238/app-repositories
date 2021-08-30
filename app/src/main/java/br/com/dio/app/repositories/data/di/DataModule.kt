package br.com.dio.app.repositories.data.di

import android.util.Log
import br.com.dio.app.repositories.data.repositories.RepoRepository
import br.com.dio.app.repositories.data.repositories.RepoRepositoryImpl
import br.com.dio.app.repositories.data.repositories.UserReposRepository
import br.com.dio.app.repositories.data.repositories.UserReposRepositoryImpl
import br.com.dio.app.repositories.data.services.GithubRepoService
import br.com.dio.app.repositories.data.services.GithubUserReposService
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.context.loadKoinModules
import org.koin.core.module.Module
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object DataModule {
    private const val TAG = "OkHttp"

    fun load() {
        loadKoinModules(networkModules() + repositoriesModule() + repositoryModule())
    }

    private fun networkModules(): Module {
        return module {
            single {
                val interceptor = HttpLoggingInterceptor {
                    Log.i(TAG, it)
                }
                interceptor.level = HttpLoggingInterceptor.Level.BODY

                OkHttpClient.Builder()
                    .addInterceptor(interceptor)
                    .build()
            }

            single {
                GsonConverterFactory.create(GsonBuilder().create())
            }

            single {
                createService<GithubUserReposService>(get(), get())
            }
            single {
                createService<GithubRepoService>(get(), get())
            }
        }
    }

    private fun repositoriesModule(): Module {
        return module {
            single<UserReposRepository> {
                UserReposRepositoryImpl(get())
            }
        }
    }

    private fun repositoryModule(): Module {
        return module {
            single<RepoRepository> {
                RepoRepositoryImpl(get())
            }
        }
    }

    private inline fun <reified T> createService(
        client: OkHttpClient,
        factory: GsonConverterFactory
    ): T {
        return Retrofit.Builder()
            .baseUrl("https://api.github.com/")
            .client(client)
            .addConverterFactory(factory)
            .build().create(T::class.java)
    }
}