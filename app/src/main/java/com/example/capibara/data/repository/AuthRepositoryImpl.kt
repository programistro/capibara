package com.example.capibara.data.repository

import android.content.SharedPreferences
import androidx.core.content.edit
import com.example.capibara.domain.repository.AuthRepository
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val prefs: SharedPreferences
) : AuthRepository {

    override fun hasPin(): Boolean = prefs.contains(KEY_PIN)

    override fun savePin(pin: String) {
        prefs.edit { putString(KEY_PIN, pin) }
    }

    override fun checkPin(pin: String): Boolean = prefs.getString(KEY_PIN, null) == pin

    companion object {
        private const val KEY_PIN = "pin_code"
    }
}
