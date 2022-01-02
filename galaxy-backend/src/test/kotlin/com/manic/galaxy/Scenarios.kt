package com.manic.galaxy

import com.manic.galaxy.context.Empty

fun given(test: Empty.() -> Unit) {
    given(::Empty, test)
}

fun <T> given(
    scenario: () -> T,
    test: T.() -> Unit,
) {
    scenario().test()
}
