package com.nextperience.template.model

import reactivemongo.bson.Macros
import reactivemongo.bson._

case class Advert(_id: BSONObjectID = BSONObjectID.generate(), title: String, description: String, salary: Int, jobType: String, owner: String)

object Advert {
  implicit val advertHandler = Macros.handler[Advert]
}