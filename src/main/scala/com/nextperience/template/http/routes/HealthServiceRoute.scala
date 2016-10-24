package com.nextperience.template.http.routes

import akka.http.scaladsl.server.Directives

class HealthServiceRoute extends Directives with JsonSupport {

  val route = pathPrefix("health") {
    pathEndOrSingleSlash {
      get {
        complete {
          HealthResponse("advert-service", "UP")
        }
      }
    }
  }

  case class HealthResponse(serviceName: String, status: String)
}
