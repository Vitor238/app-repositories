package br.com.dio.app.repositories.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.dio.app.repositories.data.model.Repo
import br.com.dio.app.repositories.domain.GetRepositoryUseCase
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class RepoViewModel(private val getRepositoryUseCase: GetRepositoryUseCase) :
    ViewModel() {

    private val _repo = MutableLiveData<State>()
    val repo: LiveData<State> = _repo

    fun getRepo(userName: String, repoName: String) {
        viewModelScope.launch {
            getRepositoryUseCase(Pair(userName, repoName))
                .onStart {
                    _repo.postValue(State.Loading)
                }.catch {
                    _repo.postValue(State.Error(it))
                }.collect {
                    _repo.postValue(State.Success(it))
                }
        }
    }

    sealed class State {
        object Loading : State()
        data class Success(val repo: Repo) : State()
        data class Error(val error: Throwable) : State()
    }
}