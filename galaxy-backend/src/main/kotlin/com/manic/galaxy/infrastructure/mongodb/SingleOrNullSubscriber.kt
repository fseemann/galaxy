package com.manic.galaxy.infrastructure.mongodb

import org.reactivestreams.Subscriber
import org.reactivestreams.Subscription
import kotlin.coroutines.Continuation
import kotlin.coroutines.resume

class SingleOrNullSubscriber<T>(private val continuation: Continuation<T?>) : Subscriber<T> {
    var state = SubscriberState.INACTIVE
        private set
    private var value: T? = null

    override fun onSubscribe(subscription: Subscription) {
        state = SubscriberState.ACTIVE
        subscription.request(1)
    }

    override fun onNext(next: T) {
        value = next
    }

    override fun onError(error: Throwable) {
        state = SubscriberState.COMPLETED
        continuation.resumeWith(Result.failure(error))
    }

    override fun onComplete() {
        state = SubscriberState.COMPLETED
        continuation.resume(value)
    }
}