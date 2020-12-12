package dao

import java.util.UUID

import com.google.inject.ImplementedBy
import domain.model.{Email, Profile}

@ImplementedBy(classOf[ProfileDaoImpl])
trait ProfileDao {
  def findProfileByEmail(email: Email): Option[Profile]
  def updatePersonStringFieldByEmail(fieldName: String, fieldValue: String, email: Email): Boolean
}
