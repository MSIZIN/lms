package dao.table

import java.util.UUID

import anorm.SqlParser.get
import anorm.{RowParser, SqlStringInterpolation, ~}
import domain.model.{Email, Password}
import play.api.db.Database

final case class AccountTable(
  id: Option[Long] = None,
  personVerificationCode: UUID,
  email: String,
  password: String
)
object AccountTable {

  private val accountParser: RowParser[AccountTable] = {
    get[Option[Long]]("account.id") ~
      get[UUID]("account.person_verification_code") ~
      get[String]("account.email") ~
      get[String]("account.password") map {
      case id ~ personVerificationCode ~ email ~ password =>
        AccountTable(id, personVerificationCode, email, password)
    }
  }

  def findAccountTableByEmail(email: Email)(implicit db: Database): Option[AccountTable] =
    db.withConnection { implicit connection =>
      SQL"SELECT * FROM account WHERE email = ${email.value}".as(accountParser.singleOpt)
    }

  def findAccountTableByVerCode(verificationCode: UUID)(implicit db: Database): Option[AccountTable] =
    db.withConnection { implicit connection =>
      SQL"SELECT * FROM account WHERE person_verification_code = CAST(${verificationCode.toString} AS UUID)".as(accountParser.singleOpt)
    }

  def insertAccountTable(accTable: AccountTable)(implicit db: Database): Boolean =
    db.withConnection { implicit connection =>
      SQL"INSERT INTO account (person_verification_code, email, password) VALUES (CAST(${accTable.personVerificationCode.toString} AS UUID), ${accTable.email}, ${accTable.password})"
        .execute()
    }

  def updateAccTableWithPswdByEmail(password: Password, email: Email)(implicit db: Database): Boolean =
    db.withConnection { implicit connection =>
      SQL"UPDATE account SET password = ${password.value} where email = ${email.value}".execute()
    }

}
