package dao.table

import java.util.UUID

import anorm.SqlParser.get
import anorm.{RowParser, ~}

final case class AccountTable(
  id: Option[Long] = None,
  personVerificationCode: UUID,
  email: String,
  password: String
)
object AccountTable {
  val accountParser: RowParser[AccountTable] = {
    get[Option[Long]]("account.id") ~
      get[UUID]("account.person_verification_code") ~
      get[String]("account.email") ~
      get[String]("account.password") map {
      case id ~ personVerificationCode ~ email ~ password =>
        AccountTable(id, personVerificationCode, email, password)
    }
  }
}
