package sbux.protobuff.test

import java.util

import sbux.protobuff.test.person.Person
import org.apache.kafka.common.serialization.{Deserializer, Serializer}

sealed class ProtobufSerializer[T <: com.trueaccord.scalapb.GeneratedMessage] extends Serializer[T] {
  override def configure(configs: util.Map[String, _], isKey: Boolean): Unit = {}

  override def serialize(topic: String, data: T): Array[Byte] = data.toByteArray

  override def close(): Unit = {}
}

object PersonSerializer extends ProtobufSerializer[Person]

object PersonDeserializer extends Deserializer[Person] {
  override def configure(configs: util.Map[String, _], isKey: Boolean): Unit = {}

  override def close(): Unit = {}
  override def deserialize(topic: String, data: Array[Byte]): Person = {
    Person.parseFrom(data)
  }
}