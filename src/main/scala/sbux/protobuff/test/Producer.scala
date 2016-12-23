package sbux.protobuff.test

import java.util.Properties
import java.util.concurrent.Future

import org.apache.kafka.clients.producer.{KafkaProducer, ProducerRecord, RecordMetadata}
import org.apache.kafka.common.serialization.StringSerializer
import sbux.protobuff.test.person.Person
class Producer extends ProducerProperties {
  protected val producer = new KafkaProducer[String, Person](producerProps, new StringSerializer, PersonSerializer)

  def send(obj: Person, modelTopic: String): Future[RecordMetadata] =
    producer.send(new ProducerRecord[String, Person](modelTopic, obj.getClass.getSimpleName, obj))

  def close(): Unit = {
    producer.flush()
    producer.close()
  }
}

class ProducerProperties extends Properties {
  val producerProps = new Properties()
  producerProps.setProperty("bootstrap.servers", "192.168.99.100:9092")
}