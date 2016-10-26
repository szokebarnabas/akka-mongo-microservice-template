package com.nextperience.template.persistence

import com.mongodb.casbah.MongoClient
import com.mongodb.casbah.commons.MongoDBObject
import com.nextperience.template.config.Config
import com.nextperience.template.model.Advert
import salat.dao.SalatDAO
import salat.global._

trait AdvertRepository {
  
  def findByTitle(title: String): List[Advert]

  def save(advert: Advert)

  def update(advert: Advert)

  def findById(id: String) : Option[Advert]

  def findAll : List[Advert]
}

class MongoAdvertRepository(advertDao: SalatDAO[Advert, String]) extends AdvertRepository {

  override def findByTitle(title: String) = {
    advertDao.find(ref = MongoDBObject("title" -> MongoDBObject("$eq" -> title)))
      .sort(orderBy = MongoDBObject("_id" -> -1))
      .toList
  }

  override def save(advert: Advert) = advertDao.save(advert)

  override def update(advert: Advert) = {
    val query = MongoDBObject("_id" -> advert.id)
    advertDao.update(q = query, advertDao._grater.asDBObject(advert))
  }

  override def findById(id: String): Option[Advert] = advertDao.findOneById(id)

  override def findAll: List[Advert] = advertDao.find(ref = MongoDBObject.empty).toList
}

object MongoAdvertRepository extends Config {
  val advertDao = new SalatDAO[Advert, String](collection = MongoClient(host = mongoHost , port = mongoPort)(database)("adverts")) {}
  def apply(): MongoAdvertRepository = new MongoAdvertRepository(advertDao)
}