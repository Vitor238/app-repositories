package br.com.dio.app.repositories.data.model

import com.google.gson.annotations.SerializedName

data class License(
    val key: String?,
    val name: String?,
    @SerializedName("spdx_id")
    val spdxId: String?,
)
