package com.manic.galaxy.infrastructure.mongodb

import com.mongodb.MongoException
import org.reactivestreams.Subscriber
import org.reactivestreams.Subscription
import kotlin.coroutines.Continuation
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

class SingleSubscriber<T>(private val continuation: Continuation<T>) : Subscriber<T> {
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
        continuation.resumeWithException(error)
    }

    override fun onComplete() {
        state = SubscriberState.COMPLETED
        if (value != null) {
            continuation.resume(value!!)
        } else {
            continuation.resumeWithException(MongoException("Failed to execute query."))
        }
    }
}