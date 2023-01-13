package omurzakov.zerowaste.di

import android.content.Context
import omurzakov.zerowaste.communication.ZeroWasteDatabase
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val databaseModule = module { single { provideDatabase(androidContext()) } }

fun provideDatabase(context: Context): ZeroWasteDatabase = ZeroWasteDatabase.getDatabase(context)