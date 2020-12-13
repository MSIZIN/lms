package dao

import com.google.inject.ImplementedBy
import domain.model.{Email, Profile, Student}

@ImplementedBy(classOf[ProfileDaoImpl])
trait ProfileDao {
  def findProfileByEmail(email: Email): Option[Profile]
  def updatePersonFieldByEmail(fieldName: String, fieldValue: String, email: Email): Boolean
  def findGroupmatesByEmail(email: Email): Option[List[Student]]
}
