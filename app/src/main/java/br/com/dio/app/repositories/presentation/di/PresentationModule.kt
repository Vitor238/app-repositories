package br.com.dio.app.repositories.presentation.di

import br.com.dio.app.repositories.presentation.MainViewModel
import br.com.dio.app.repositories.presentation.RepoViewModel
import org.koin.core.context.loadKoinModules
import org.koin.core.module.Module
import org.koin.dsl.module

object PresentationModule {

    fun load() {
        loadKoinModules(viewModelModule() + repoViewModelModule())
    }

    private fun viewModelModule(): Module {
        return module {
            factory { MainViewModel(get()) }
        }
    }

    private fun repoViewModelModule(): Module {
        return module {
            factory { RepoViewModel(get()) }
        }
    }

}