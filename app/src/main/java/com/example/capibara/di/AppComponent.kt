package com.example.capibara.di

import android.content.Context
import com.example.capibara.domain.usecase.GetPetStatsUseCase
import com.example.capibara.domain.usecase.GetRemindersUseCase
import com.example.capibara.domain.usecase.GetShopItemsUseCase
import com.example.capibara.domain.usecase.HasPinUseCase
import com.example.capibara.domain.usecase.LoginWithPinUseCase
import com.example.capibara.domain.usecase.RegisterPinUseCase
import com.example.capibara.domain.usecase.SaveReminderUseCase
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class])
interface AppComponent {

    fun hasPinUseCase(): HasPinUseCase
    fun loginWithPinUseCase(): LoginWithPinUseCase
    fun registerPinUseCase(): RegisterPinUseCase
    fun getPetStatsUseCase(): GetPetStatsUseCase
    fun getRemindersUseCase(): GetRemindersUseCase
    fun getShopItemsUseCase(): GetShopItemsUseCase
    fun saveReminderUseCase(): SaveReminderUseCase

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context): AppComponent
    }
}
