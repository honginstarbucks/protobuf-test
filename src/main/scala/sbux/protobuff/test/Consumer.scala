package sbux.protobuff.test

import akka.actor.ActorSystem
import akka.kafka.scaladsl.Consumer
import akka.kafka.{ConsumerSettings, Subscriptions}
import akka.stream.ActorMaterializer
import akka.stream.scaladsl.Sink
import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.common.serialization.StringDeserializer
import sbux.protobuff.test.person.Person
import scala.concurrent.ExecutionContextExecutor

class Consumer[A] {

  implicit val system = ActorSystem("point-accrual")
  implicit val ec: ExecutionContextExecutor = system.dispatcher
  implicit val mat: ActorMaterializer = ActorMaterializer()

  val consumerSettings: ConsumerSettings[String, A] = ConsumerSettings(system, new StringDeserializer, Person)
    .withBootstrapServers("192.168.99.100:9092")
    .withProperty(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest")

  def consume(): Unit = {
    Consumer.committableSource(consumerSettings, Subscriptions.topics("test"))
      .map(m => {
        val f = m.record.value()
        println(f)
        m.committableOffset.commitScaladsl()
      })
      .runWith(Sink.ignore)
  }
}
