package com.ot.core

import kotlinx.coroutines.CoroutineDispatcher

data class AppDispatchers(
    val work: CoroutineDispatcher,
    val result: CoroutineDispatcher,
)
