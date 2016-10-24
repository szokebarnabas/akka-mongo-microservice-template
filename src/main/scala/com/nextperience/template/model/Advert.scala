package com.nextperience.template.model

import org.bson.types.ObjectId
import salat.annotations._

case class Advert(@Key("_id") id: String = new ObjectId().toString, title: String, description: String, salary: Int, jobType: String, owner: String)

