package com.manic.galaxy.infrastructure.mongodb

import org.reactivestreams.Publisher
import kotlin.coroutines.suspendCoroutine

suspend fun <T> Publisher<T>.toSingleOrNull(): T? {
    return suspendCoroutine { continuation ->
        this.subscribe(SingleOrNullSubscriber(continuation))
    }
}

suspend fun <T> Publisher<T>.toSingle(): T {
    return suspendCoroutine { continuation ->
        this.subscribe(SingleSubscriber(continuation))
    }
}

suspend fun <T> Publisher<T>.toMultiple(): List<T> {
    return suspendCoroutine { continuation ->
        this.subscribe(MultipleSubscriber(continuation))
    }
}