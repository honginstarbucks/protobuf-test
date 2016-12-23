package sbux.protobuff.test

import sbux.protobuff.test.person.Person
import sbux.protobuff.test.person.Person.{Address, PhoneNumber, PhoneType}

object app extends App {

  val p1 = Person()
    .withName("tester")
    .withPhone(
      Seq(
        PhoneNumber(number="444-444-4444").withType(PhoneType.MOBILE),
        PhoneNumber(number="555-555-5555").withType(PhoneType.HOME)
      )
    )
    .withAge(36)
    .withAddresses(
      Seq(
        Address(
          country = "USA",
          name=Some("39 toronto street"),
          city=Some("Vancouver")
        )
      )
    )

  print(p1)

  val p = new Producer()
  p.send(p1, "test")

  val c = new KafkaConsumer
  c.consume()
}
