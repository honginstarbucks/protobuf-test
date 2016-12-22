name := "protobuf-test"
version := "1.0"
scalaVersion := "2.11.8"

val akkaVersion = "2.4.14"

resolvers ++= Seq(
  Resolver.jcenterRepo,
  Resolver.sonatypeRepo("releases"),
  Resolver.bintrayRepo("outworkers", "oss-releases"),
  Resolver.sonatypeRepo("snapshots")
)

libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-actor" % akkaVersion,
  "com.typesafe.akka" %% "akka-persistence" % akkaVersion,
  "com.typesafe.akka" %% "akka-slf4j" % akkaVersion,

  "ch.qos.logback" % "logback-classic" % "1.1.7",
  "org.slf4j" % "log4j-over-slf4j" % "1.7.21",

  "com.typesafe.akka" %% "akka-stream" % akkaVersion,
  "com.typesafe.akka" %% "akka-persistence" % akkaVersion,
  "com.typesafe.akka" %% "akka-stream-kafka" % "0.13",

  "org.apache.kafka" %% "kafka" % "0.10.0.1",
  "org.apache.kafka" % "kafka-clients" % "0.10.0.1",

  "com.typesafe.akka" %% "akka-testkit" % akkaVersion % "test",
  "org.scalatest" %% "scalatest" % "3.0.1" % "test"
)

PB.targets in Compile := Seq(
  scalapb.gen() -> (sourceManaged in Compile).value
)