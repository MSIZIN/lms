package dao

import anorm.SqlStringInterpolation
import dao.table.AccountTable
import domain.model.{Account, Email, Password}
import javax.inject._
import play.api.db.DBApi

@Singleton
class AccountDaoImpl @Inject() (dbapi: DBApi) extends AccountDao {

  private val db = dbapi.database("default")

  override def findAccByEmail(email: Email): Option[Account] =
    db.withConnection { implicit connection =>
        SQL"SELECT * FROM account WHERE email = ${email.value}".as(AccountTable.simpleParser.singleOpt)
      }
      .map(queryRes => Account(Email(queryRes.email), Password(queryRes.password)))

}
