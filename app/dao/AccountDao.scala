package dao

import com.google.inject.ImplementedBy
import domain.model.{Account, Email}

@ImplementedBy(classOf[AccountDaoImpl])
trait AccountDao {
  def findAccByEmail(email: Email): Option[Account]
}
