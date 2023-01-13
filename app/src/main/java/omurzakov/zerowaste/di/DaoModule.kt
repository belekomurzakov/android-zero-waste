package omurzakov.zerowaste.di

import omurzakov.zerowaste.communication.UtilizedItemDao
import omurzakov.zerowaste.communication.ZeroWasteDatabase
import org.koin.dsl.module

val daoModule = module { single { provideUtilizedItemDao(get()) } }

fun provideUtilizedItemDao(db: ZeroWasteDatabase): UtilizedItemDao = db.utilizedItemDao()