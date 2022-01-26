package com.manic.galaxy.infrastructure.mongodb

import org.reactivestreams.Subscriber
import org.reactivestreams.Subscription
import kotlin.coroutines.Continuation
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

class MultipleSubscriber<T>(private val continuation: Continuation<List<T>>) : Subscriber<T> {
    var state = SubscriberState.INACTIVE
        private set
    private var value: MutableList<T> = mutableListOf()

    override fun onSubscribe(subscription: Subscription) {
        state = SubscriberState.ACTIVE
        subscription.request(Long.MAX_VALUE)
    }

    override fun onNext(next: T) {
        value.add(next)
    }

    override fun onError(error: Throwable) {
        state = SubscriberState.COMPLETED
        continuation.resumeWithException(error)
    }

    override fun onComplete() {
        state = SubscriberState.COMPLETED
        continuation.resume(value)
    }
}