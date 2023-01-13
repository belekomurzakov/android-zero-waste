package omurzakov.zerowaste.di

import omurzakov.zerowaste.ml.IMachineLearningModel
import omurzakov.zerowaste.ml.MachineLearningModelImpl
import org.koin.dsl.module

val mlModule = module { single { provideMachineLearningModel() } }

fun provideMachineLearningModel(): IMachineLearningModel = MachineLearningModelImpl()