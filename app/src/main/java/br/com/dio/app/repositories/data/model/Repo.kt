package br.com.dio.app.repositories.data.model

import com.google.gson.annotations.SerializedName

data class Repo(
    val id: Long,
    val name: String,
    val owner: Owner,
    @SerializedName("stargazers_count")
    val stargazersCount: Long,
    val language: String?,
    @SerializedName("html_url")
    val htmlURL: String,
    val description: String?,
    val fork: Boolean?,
    @SerializedName("forks_count")
    val forksCount: Long,
    @SerializedName("open_issues_count")
    val openIssuesCount: Long,
    val watchers: Long,
    val license: License?
)
