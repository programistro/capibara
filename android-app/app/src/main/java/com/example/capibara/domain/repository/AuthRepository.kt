package com.example.capibara.domain.repository

interface AuthRepository {
    fun hasPin(): Boolean
    fun savePin(pin: String)
    fun checkPin(pin: String): Boolean
}
