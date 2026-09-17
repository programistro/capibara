package com.example.capibara.di

import android.content.Context
import android.content.SharedPreferences
import androidx.room.Room
import com.example.capibara.data.local.CapibaraDatabase
import com.example.capibara.data.local.InventoryDao
import com.example.capibara.data.local.WalletDao
import com.example.capibara.data.repository.AuthRepositoryImpl
import com.example.capibara.data.repository.InventoryRepositoryImpl
import com.example.capibara.data.repository.PetRepositoryStub
import com.example.capibara.data.repository.ReminderRepositoryStub
import com.example.capibara.data.repository.WalletRepositoryImpl
import com.example.capibara.domain.repository.AuthRepository
import com.example.capibara.domain.repository.InventoryRepository
import com.example.capibara.domain.repository.PetRepository
import com.example.capibara.domain.repository.ReminderRepository
import com.example.capibara.domain.repository.WalletRepository
import com.example.capibara.domain.scheduler.ReminderScheduler
import com.example.capibara.notifications.AlarmReminderScheduler
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @JvmStatic
    fun provideSharedPreferences(
        @ApplicationContext context: Context
    ): SharedPreferences =
        context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    @Provides
    @Singleton
    @JvmStatic
    fun provideAuthRepository(impl: AuthRepositoryImpl): AuthRepository = impl

    @Provides
    @Singleton
    @JvmStatic
    fun providePetRepository(impl: PetRepositoryStub): PetRepository = impl

    @Provides
    @Singleton
    @JvmStatic
    fun provideReminderRepository(impl: ReminderRepositoryStub): ReminderRepository = impl

    @Provides
    @Singleton
    @JvmStatic
    fun provideReminderScheduler(impl: AlarmReminderScheduler): ReminderScheduler = impl

    @Provides
    @Singleton
    @JvmStatic
    fun provideWalletRepository(impl: WalletRepositoryImpl): WalletRepository = impl

    @Provides
    @Singleton
    @JvmStatic
    fun provideInventoryRepository(impl: InventoryRepositoryImpl): InventoryRepository = impl

    @Provides
    @Singleton
    @JvmStatic
    fun provideDatabase(
        @ApplicationContext context: Context
    ): CapibaraDatabase =
        Room.databaseBuilder(context, CapibaraDatabase::class.java, "capibara.db").build()

    @Provides
    @JvmStatic
    fun provideWalletDao(db: CapibaraDatabase): WalletDao = db.walletDao()

    @Provides
    @JvmStatic
    fun provideInventoryDao(db: CapibaraDatabase): InventoryDao = db.inventoryDao()

    private const val PREFS_NAME = "capibara"
}
