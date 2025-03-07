package br.com.dio.app.repositories.data.repositories

import br.com.dio.app.repositories.core.RemoteException
import br.com.dio.app.repositories.data.services.GithubRepoService
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException

class RepoRepositoryImpl(private val githubRepoService: GithubRepoService) : RepoRepository {
    override suspend fun getRepository(user: String, repo: String) = flow {
        try {
            val repository = githubRepoService.getRepository(user, repo)
            emit(repository)
        } catch (e: HttpException) {
            throw RemoteException(e.message ?: "Não foi possível fazer a busca no momento.")
        }
    }
}