package com.example.bookajobworker

data class RegistrationData(val data: Map<String, Any>) {
    fun withNewFields(newFields: Map<String, Any>): RegistrationData {
        val newData = data.toMutableMap().apply { putAll(newFields) }
        return RegistrationData(newData)
    }
}
