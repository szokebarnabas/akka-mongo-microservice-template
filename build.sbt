lazy val root = (project in file(".")).enablePlugins(JavaAppPackaging)

name := "microservice"
organization := "com.nextperience"
version := "1.0"
scalaVersion := "2.11.8"

scalacOptions := Seq("-unchecked", "-deprecation", "-encoding", "utf8")
resolvers += Resolver.bintrayRepo("hseeberger", "maven")
resolvers += "Sonatype Snapshots" at "https://oss.sonatype.org/content/repositories/snapshots/"

packageName in Docker := "advert-service"
dockerExposedPorts := Seq(8080)

libraryDependencies ++= {
  val akkaV = "2.4.11"
  val scalaTestV = "3.0.0"
  val json4sJacksonV = "3.4.2"
  val akkaHttpJson4sV = "1.8.0"
  val casbahV: String = "2.8.2"
  val salatV: String = "1.10.0"
  Seq(
    // Akka
    "com.typesafe.akka" %% "akka-actor" % akkaV,
    "com.typesafe.akka" %% "akka-stream" % akkaV,
    "com.typesafe.akka" %% "akka-http-experimental" % akkaV,
    "com.typesafe.akka" %% "akka-slf4j" % akkaV,

    // Json
    "de.heikoseeberger" %% "akka-http-json4s" % akkaHttpJson4sV,
    "org.json4s" %% "json4s-jackson" % json4sJacksonV,
    "org.json4s" % "json4s-ext_2.11" % json4sJacksonV,

    // MongoDB
    "org.mongodb" % "casbah-core_2.11" % casbahV,
    "com.github.salat" %% "salat" % salatV,

    // Testing
    "com.typesafe.akka" %% "akka-http-testkit" % akkaV,
    "org.scalatest" %% "scalatest" % scalaTestV % "test"
  )
}
unmanagedResourceDirectories in Compile += {
  baseDirectory.value / "src/main/resources"
}

Revolver.settings
mainClass in Compile := Some("com.nextperience.template.Main")
