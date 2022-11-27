package com.example.mobiledatausage.utils

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.test.StandardTestDispatcher

class FakeDispatcherProvider(
    override val main: CoroutineDispatcher = StandardTestDispatcher(),
    override val io: CoroutineDispatcher = StandardTestDispatcher(),
    override val default: CoroutineDispatcher = StandardTestDispatcher(),
    override val unconfined: CoroutineDispatcher = StandardTestDispatcher()
) : DispatcherProvider