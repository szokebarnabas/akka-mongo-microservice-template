package com.nextperience.template.config

import com.typesafe.config.ConfigFactory

trait Config {

  private val config = ConfigFactory.load()
  val httpInterface = config.getString("http.interface")
  val httpPort = config.getInt("http.port")
  val mongoHost = config.getString("mongo.host")
  val mongoPort = config.getInt("mongo.port")
  val database = config.getString("mongo.db")

}
