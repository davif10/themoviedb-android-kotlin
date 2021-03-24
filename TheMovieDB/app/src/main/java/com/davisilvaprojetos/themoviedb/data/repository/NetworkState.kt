package com.davisilvaprojetos.themoviedb.data.repository

enum class Status{
    RUNNING,
    SUCCESS,
    FAILED
}
class NetworkState(val status: Status, val msg: String) {

    companion object{
        val LOADED: NetworkState
        val LOADING: NetworkState
        val ERROR: NetworkState

        init {
            LOADED = NetworkState(Status.SUCCESS, "Sucesso")
            LOADING = NetworkState(Status.RUNNING, "Carregando")
            ERROR = NetworkState(Status.FAILED, "Erro ao tentar carregar")
        }
    }
}