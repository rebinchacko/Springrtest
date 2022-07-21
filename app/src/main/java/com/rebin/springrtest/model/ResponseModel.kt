package com.rebin.springrtest.model

import java.io.Serializable

data class ResponseModel(
    val sources: List<Source>,
    val status: String
)

data class Source(
    val category: String,
    val country: String,
    val description: String,
    val id: String,
    val language: String,
    val name: String,
    val url: String
):Serializable