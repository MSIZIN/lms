package dao

import com.google.inject.ImplementedBy
import domain.model.{Email, Profile}

@ImplementedBy(classOf[ProfileDaoImpl])
trait ProfileDao {
  def findProfileByEmail(email: Email): Option[Profile]
}
