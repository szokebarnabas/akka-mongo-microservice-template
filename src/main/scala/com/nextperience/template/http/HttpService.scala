package com.nextperience.template.http

import akka.http.scaladsl.server.Directives._
import com.nextperience.template.http.routes.{AdvertServiceRoute, HealthServiceRoute}

class HttpService {

  val advertRouter = new AdvertServiceRoute
  val healthRouter = new HealthServiceRoute

  val routes =
    pathPrefix("api") {
      advertRouter.route ~ healthRouter.route
    }

}
