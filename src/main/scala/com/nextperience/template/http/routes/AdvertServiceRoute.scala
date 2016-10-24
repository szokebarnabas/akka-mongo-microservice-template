package com.nextperience.template.http.routes

import akka.http.scaladsl.model.{HttpResponse, StatusCodes}
import akka.http.scaladsl.server.Directives
import com.nextperience.template.model.Advert
import com.nextperience.template.persistence.MongoAdvertRepository

class AdvertServiceRoute extends Directives with JsonSupport {

  val route = pathPrefix("advert") {
    pathEndOrSingleSlash {
      get {
        complete {
          MongoAdvertRepository().findAll
        }
      } ~
        post {
          entity(as[Advert]) { advert =>
            complete {
              MongoAdvertRepository().save(advert)
              HttpResponse(StatusCodes.Accepted)
            }
          }
        }
    } ~
      path(Segment) { id =>
        get {
          complete {
            MongoAdvertRepository().findById(id)
          }
        } ~
          put {
            entity(as[Advert]) { advert =>
              complete {
                MongoAdvertRepository().update(advert)
                HttpResponse(StatusCodes.Accepted)
              }
            }
          }
      }
  }
}