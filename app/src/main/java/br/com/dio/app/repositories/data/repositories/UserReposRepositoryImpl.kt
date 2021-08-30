package br.com.dio.app.repositories.data.repositories

import br.com.dio.app.repositories.core.RemoteException
import br.com.dio.app.repositories.data.services.GithubUserReposService
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException

class UserReposRepositoryImpl(private val gitHubService: GithubUserReposService) :
    UserReposRepository {
    override suspend fun listRepositories(user: String) = flow {
        try {
            val repoList = gitHubService.listRepositories(user)
            emit(repoList)
        } catch (e: HttpException) {
            throw RemoteException(e.message ?: "Não foi possível fazer a busca no momento.")
        }
    }
}