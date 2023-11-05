package jp.co.yumemi.android.code_check.common

sealed class ErrorState {
    data class NetworkError(val message: String) : ErrorState()
    data class InputError(val message: String) :ErrorState()
    // Add other events here if needed
}
