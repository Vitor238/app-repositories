package br.com.dio.app.repositories.data.services

import br.com.dio.app.repositories.data.model.Repo
import retrofit2.http.GET
import retrofit2.http.Path

interface GithubRepoService {
    @GET("repos/{user}/{repo}")
    suspend fun getRepository(
        @Path("user") user: String,
        @Path("repo") repo: String
    ): Repo
}