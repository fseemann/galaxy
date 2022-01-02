package com.manic.galaxy

import org.koin.core.component.KoinComponent

class Nothing() : KoinComponent

fun given(test: Nothing.() -> Unit) {
    given(::Nothing, test)
}

fun <T> given(
    scenario: () -> T,
    test: T.() -> Unit,
) {
    scenario().test()
}
