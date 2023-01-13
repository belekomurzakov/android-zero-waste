package omurzakov.zerowaste.di

import omurzakov.zerowaste.communication.*
import org.koin.dsl.module

val repositoryModule = module {
    single { provideRemoteRepository(get()) }
    single { provideUtilizedItemLocalRepositoryImpl(get()) }
}

fun provideRemoteRepository(api: API): IRemoteRepository = RemoteRepositoryImpl(api)

fun provideUtilizedItemLocalRepositoryImpl(dao: UtilizedItemDao):
        IUtilizedItemLocalRepository = UtilizedItemLocalRepositoryImpl(dao)