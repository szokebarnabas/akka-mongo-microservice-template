package com.nextperience.template

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.stream.ActorMaterializer
import akka.util.Timeout
import com.nextperience.template.http.HttpService
import org.slf4j.LoggerFactory
import scala.concurrent.duration._

object Main extends App {

  val logger = LoggerFactory.getLogger(Main.getClass)

  implicit val actorSystem = ActorSystem()
  implicit val actorMaterializer = ActorMaterializer()
  implicit val executionContext = actorSystem.dispatcher
  implicit val timeout = Timeout(10 seconds)

  val httpService = new HttpService

  Http().bindAndHandle(httpService.routes, "localhost", 8080)

  logger.info("Application has been started.")
}
