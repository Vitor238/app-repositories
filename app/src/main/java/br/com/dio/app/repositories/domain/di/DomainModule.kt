package br.com.dio.app.repositories.domain.di

import br.com.dio.app.repositories.domain.GetRepositoryUseCase
import br.com.dio.app.repositories.domain.ListUserRepositoriesUseCase
import org.koin.core.context.loadKoinModules
import org.koin.core.module.Module
import org.koin.dsl.module

object DomainModule {

    fun load() {
        loadKoinModules(useCaseModule() + repoUseCaseModule())
    }

    private fun useCaseModule(): Module {
        return module {
            factory { ListUserRepositoriesUseCase(get()) }
        }
    }

    private fun repoUseCaseModule(): Module {
        return module {
            factory { GetRepositoryUseCase(get()) }
        }
    }

}