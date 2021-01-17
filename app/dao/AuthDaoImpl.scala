package dao

import java.util.UUID

import dao.table._
import dao.table.AccountTable._
import dao.table.PersonTable._
import domain.model.{Account, Email, Password, Person}
import javax.inject._
import play.api.db.{DBApi, Database}

@Singleton
class AuthDaoImpl @Inject() (dbapi: DBApi) extends AuthDao {

  implicit val db: Database = dbapi.database("default")

  override def findAccByEmail(email: Email): Option[Account] =
    findAccountTableByEmail(email).map(accTable => Account(Email(accTable.email), Password(accTable.password)))

  override def findAccByVerCode(verificationCode: UUID): Option[Account] =
    findAccountTableByVerCode(verificationCode).map(accTable => Account(Email(accTable.email), Password(accTable.password)))

  override def findPersonByVerCode(verificationCode: UUID): Option[Person] =
    findPersonTableByVerCode(verificationCode)
      .map(personTable =>
        Person(
          personTable.firstName,
          personTable.lastName,
          personTable.middleName,
          personTable.phone,
          personTable.homeTown,
          personTable.info,
          personTable.vkLink,
          personTable.facebookLink,
          personTable.linkedinLink,
          personTable.instagramLink
        )
      )

  override def insertAcc(verificationCode: UUID, account: Account): Unit =
    insertAccountTable(AccountTable(None, verificationCode, account.email.value, account.password.value))

  override def updatePasswordByEmail(password: Password, email: Email): Unit =
    updateAccTableWithPswdByEmail(password, email)

}
