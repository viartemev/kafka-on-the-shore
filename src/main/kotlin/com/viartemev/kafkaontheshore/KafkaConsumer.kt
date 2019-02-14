package com.viartemev.kafkaontheshore

import kotlinx.coroutines.suspendCancellableCoroutine
import org.apache.kafka.clients.consumer.KafkaConsumer
import org.apache.kafka.clients.consumer.OffsetAndMetadata
import org.apache.kafka.clients.consumer.OffsetCommitCallback
import org.apache.kafka.common.TopicPartition
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

suspend inline fun <reified K : Any, reified V : Any> KafkaConsumer<K, V>.commitAsync(crossinline handler: (offsets: Map<TopicPartition, OffsetAndMetadata>) -> Unit = {}) =
        suspendCancellableCoroutine<Unit> { continuation ->
            val callback = OffsetCommitCallback { offsets, exception ->
                if (exception == null) {
                    handler(offsets)
                    continuation.resume(Unit)
                } else {
                    continuation.resumeWithException(exception)
                }
            }
            this.commitAsync(callback)
        }