# Kafka on the Shore

### Usage:
##### - Async message sending: 
```kotlin
async { kafkaProducer.dispatch(ProducerRecord("topic", "This is record")) }
```

##### - Async consumer commit: 
```kotlin
async { kafkaConsumer.commitAsync() }
```
