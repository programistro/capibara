package com.example.capibara.di

import android.content.Context
import android.content.SharedPreferences
import com.example.capibara.data.repository.AuthRepositoryImpl
import com.example.capibara.data.repository.PetRepositoryStub
import com.example.capibara.data.repository.ReminderRepositoryStub
import com.example.capibara.domain.repository.AuthRepository
import com.example.capibara.domain.repository.PetRepository
import com.example.capibara.domain.repository.ReminderRepository
import com.example.capibara.domain.scheduler.ReminderScheduler
import com.example.capibara.notifications.AlarmReminderScheduler
import dagger.Module
import dagger.Provides

@Module
object AppModule {

    @Provides
    @JvmStatic
    fun provideSharedPreferences(context: Context): SharedPreferences =
        context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    @Provides
    @JvmStatic
    fun provideAuthRepository(impl: AuthRepositoryImpl): AuthRepository = impl

    @Provides
    @JvmStatic
    fun providePetRepository(impl: PetRepositoryStub): PetRepository = impl

    @Provides
    @JvmStatic
    fun provideReminderRepository(impl: ReminderRepositoryStub): ReminderRepository = impl

    @Provides
    @JvmStatic
    fun provideReminderScheduler(impl: AlarmReminderScheduler): ReminderScheduler = impl

    private const val PREFS_NAME = "capibara"
}
