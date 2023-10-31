package jp.co.yumemi.android.code_check.common

sealed class ErrorState {
    data class Error(val message: String) : ErrorState()
    // Add other events here if needed
}
