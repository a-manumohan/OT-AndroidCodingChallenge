package com.ot.core

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

data class AppDispatchers(
    val work: CoroutineDispatcher,
    val result: CoroutineDispatcher
)

val d = AppDispatchers(Dispatchers.IO, Dispatchers.Main)