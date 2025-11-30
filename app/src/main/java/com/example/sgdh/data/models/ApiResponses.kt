package com.example.sgdh.data.models

import com.google.gson.annotations.SerializedName

data class ApiResponse<T>(
    @SerializedName("data")
    val data: T,

    @SerializedName("message")
    val message: String?
)

data class PaginatedResponse<T>(
    @SerializedName("data")
    val data: List<T>,

    @SerializedName("links")
    val links: PaginationLinks?,

    @SerializedName("meta")
    val meta: PaginationMeta?
)

data class PaginationLinks(
    @SerializedName("first")
    val first: String?,

    @SerializedName("last")
    val last: String?,

    @SerializedName("prev")
    val prev: String?,

    @SerializedName("next")
    val next: String?
)

data class PaginationMeta(
    @SerializedName("current_page")
    val currentPage: Int,

    @SerializedName("from")
    val from: Int?,

    @SerializedName("last_page")
    val lastPage: Int,

    @SerializedName("per_page")
    val perPage: Int,

    @SerializedName("to")
    val to: Int?,

    @SerializedName("total")
    val total: Int
)

data class ErrorResponse(
    @SerializedName("message")
    val message: String,

    @SerializedName("errors")
    val errors: Map<String, List<String>>?
)