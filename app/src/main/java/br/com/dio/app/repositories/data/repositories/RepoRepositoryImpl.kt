package br.com.dio.app.repositories.data.repositories

import br.com.dio.app.repositories.data.model.Repo
import br.com.dio.app.repositories.data.services.GitHubService
import kotlinx.coroutines.flow.flow

class RepoRepositoryImpl(private val gitHubService: GitHubService) : RepoRepository {
    override suspend fun listRepositories(user: String) = flow<List<Repo>> {
        val repoList = gitHubService.listRepositories(user)
        emit(repoList)
    }
}