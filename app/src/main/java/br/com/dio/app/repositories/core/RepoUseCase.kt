package br.com.dio.app.repositories.core

import kotlinx.coroutines.flow.Flow

abstract class RepoUseCase<Param, Source> {
    abstract suspend fun execute(param: Param): Flow<Source>

    open suspend operator fun invoke(param: Param) = execute(param)

    abstract class NoParam<Source> : RepoUseCase<None, Flow<Source>>() {
        abstract suspend fun execute(): Flow<Source>

        final override suspend fun execute(param: None) =
            throw UnsupportedOperationException()

        suspend operator fun invoke(): Flow<Source> = execute()
    }

    abstract class NoSource<Params> : RepoUseCase<Params, Unit>() {
        override suspend operator fun invoke(param: Params) = execute(param)
    }

    object None
}