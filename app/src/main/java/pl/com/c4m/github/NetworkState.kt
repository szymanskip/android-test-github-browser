package pl.com.c4m.github

sealed class NetworkState {
    object Success : NetworkState()
    object Loading : NetworkState()
    class Error(val message: String? = null) : NetworkState()
}