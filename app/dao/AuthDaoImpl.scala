package dao

import java.util.UUID

import anorm.SqlStringInterpolation
import dao.table.{AccountTable, PersonTable}
import domain.model.{Account, Email, Password, Person}
import javax.inject._
import play.api.db.DBApi

@Singleton
class AuthDaoImpl @Inject() (dbapi: DBApi) extends AuthDao {

  private val db = dbapi.database("default")

  override def findAccByEmail(email: Email): Option[Account] =
    db.withConnection { implicit connection =>
        SQL"SELECT * FROM account WHERE email = ${email.value}".as(AccountTable.accountParser.singleOpt)
      }
      .map(queryRes => Account(Email(queryRes.email), Password(queryRes.password)))

  override def findAccByVerCode(verificationCode: UUID): Option[Account] =
    db.withConnection { implicit connection =>
        SQL"SELECT * FROM account WHERE person_verification_code = CAST(${verificationCode.toString} AS UUID)".as(
          AccountTable.accountParser.singleOpt
        )
      }
      .map(queryRes => Account(Email(queryRes.email), Password(queryRes.password)))

  override def findPersonByVerCode(verificationCode: UUID): Option[Person] =
    db.withConnection { implicit connection =>
        SQL"SELECT * FROM person WHERE verification_code = CAST(${verificationCode.toString} AS UUID)".as(
          PersonTable.personParser.singleOpt
        )
      }
      .map(queryRes =>
        Person(
          queryRes.firstName,
          queryRes.lastName,
          queryRes.middleName,
          queryRes.phone,
          queryRes.homeTown,
          queryRes.info,
          queryRes.vkLink,
          queryRes.facebookLink,
          queryRes.linkedinLink,
          queryRes.instagramLink
        )
      )

  override def insertAcc(verificationCode: UUID, account: Account): Boolean =
    db.withConnection { implicit connection =>
      SQL"INSERT INTO account (person_verification_code, email, password) VALUES (CAST(${verificationCode.toString} AS UUID), ${account.email.value}, ${account.password.value})"
        .execute()
    }

}
