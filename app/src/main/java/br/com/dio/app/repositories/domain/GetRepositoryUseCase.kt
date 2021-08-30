package br.com.dio.app.repositories.domain

import br.com.dio.app.repositories.core.RepoUseCase
import br.com.dio.app.repositories.data.model.Repo
import br.com.dio.app.repositories.data.repositories.RepoRepository
import kotlinx.coroutines.flow.Flow

class GetRepositoryUseCase(private val repository: RepoRepository) :
    RepoUseCase<Pair<String, String>, Repo>() {
    override suspend fun execute(param: Pair<String, String>): Flow<Repo> {
        return repository.getRepository(param.first, param.second)
    }
}