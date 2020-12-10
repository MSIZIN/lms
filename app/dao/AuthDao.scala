package dao

import java.util.UUID

import com.google.inject.ImplementedBy
import domain.model.{Account, Email, Person}

@ImplementedBy(classOf[AuthDaoImpl])
trait AuthDao {
  def findAccByEmail(email: Email): Option[Account]
  def findAccByVerCode(verificationCode: UUID): Option[Account]
  def findPersonByVerCode(verificationCode: UUID): Option[Person]
  def insertAcc(verificationCode: UUID, account: Account): Boolean
}
