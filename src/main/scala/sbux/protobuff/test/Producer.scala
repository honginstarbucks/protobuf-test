package sbux.protobuff.test

import java.util.Properties
import java.util.concurrent.Future
import org.apache.kafka.clients.producer.{KafkaProducer, ProducerRecord, RecordMetadata}

class Producer[A] extends ProducerProperties {
  protected val producer = new KafkaProducer[String, A](producerProps)

  def send(obj: A, modelTopic: String): Future[RecordMetadata] =
    producer.send(new ProducerRecord[String, A](modelTopic, obj.getClass.getSimpleName, obj))

  def close(): Unit = {
    producer.flush()
    producer.close()
  }
}

object Producer {
  def apply[T](topic: String, props: ProducerProperties): Producer[T] = new Producer[T]() {
    override val producer = new KafkaProducer[String, T](props)
  }
}

class ProducerProperties extends Properties {
  val producerProps = new Properties()
  producerProps.setProperty("bootstrap.servers", "192.168.99.100:9092")
}