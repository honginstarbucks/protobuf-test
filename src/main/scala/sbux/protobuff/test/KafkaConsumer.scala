package sbux.protobuff.test

import akka.actor.ActorSystem
import akka.kafka.scaladsl.Consumer
import akka.kafka.scaladsl.Consumer.Control
import akka.kafka.{ConsumerSettings, Subscriptions}
import akka.stream.ActorMaterializer
import akka.stream.scaladsl.Sink
import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.common.serialization.StringDeserializer
import sbux.protobuff.test.person.Person
import scala.concurrent.duration._
import scala.concurrent.ExecutionContextExecutor

class KafkaConsumer {

  implicit val system = ActorSystem("point-accrual")
  implicit val ec: ExecutionContextExecutor = system.dispatcher
  implicit val mat: ActorMaterializer = ActorMaterializer()

  val consumerSettings: ConsumerSettings[String, Person] = ConsumerSettings(system, new StringDeserializer, PersonDeserializer)
    .withBootstrapServers("192.168.99.100:9092")
    .withGroupId("test")
    .withProperty(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest")

  def consume(): Unit = {
    val control: Control = Consumer.committableSource(consumerSettings, Subscriptions.topics("test"))
      .map(m => {
        val f = m.record.value()
        println(s"I GOT IT: $f")
        println(f)
        m.committableOffset.commitScaladsl()
      }).to(Sink.ignore).run()

    // shut myself down after 5 seconds, otherwise I can't see anything from the console
    akka.pattern.after(5.seconds, system.scheduler) {    control.shutdown()   }
  }
}
