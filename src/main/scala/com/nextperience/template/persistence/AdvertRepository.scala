package com.nextperience.template.persistence

import com.nextperience.template.config.Config
import com.nextperience.template.model.Advert
import org.slf4j.LoggerFactory
import reactivemongo.api.{DB, MongoDriver}
import reactivemongo.bson.{BSONDocument, BSONObjectID}
import reactivemongo.extensions.dao.BsonDao
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

trait AdvertRepository {

  def findByTitle(title: String): Future[List[Advert]]

  def save(advert: Advert)

  def update(advert: Advert)

  def findById(id: String) : Future[Option[Advert]]

  def findAll : Future[List[Advert]]
}

class MongoAdvertRepository(db : DB) extends BsonDao[Advert, BSONObjectID](db = db, collectionName = "adverts") with AdvertRepository {

  override def findByTitle(title: String): Future[List[Advert]] = super.findAll(selector = BSONDocument("title" -> BSONDocument("$eq" -> title)))

  override def update(advert: Advert): Unit = super.update(selector = BSONDocument("_id" -> BSONDocument("$eq" -> advert._id)), update = advert)

  override def findById(id: String): Future[Option[Advert]] = super.findById(BSONObjectID.parse(id).get)

  override def findAll: Future[List[Advert]] = super.findAll()

  override def save(advert: Advert): Unit = super.insert(advert)
}

object MongoAdvertRepository extends Config {
  val logger = LoggerFactory.getLogger(getClass)
  logger.info("Connecting to advert db... Host: {}, Port: {} Database: {}", mongoHost, mongoPort.toString, database)
  val driver = new MongoDriver
  val connection = driver.connection(nodes = List(s"$mongoHost:$mongoPort"))
  val db: DB = connection(name = database)
  def apply(): MongoAdvertRepository = new MongoAdvertRepository(db)
}