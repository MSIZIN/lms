package dao.table

import java.util.UUID

import anorm.SqlParser.get
import anorm.{RowParser, SqlStringInterpolation, ~}
import play.api.db.Database

final case class PersonTable(
  verificationCode: UUID,
  position: String,
  firstName: String,
  lastName: String,
  middleName: String,
  phone: Option[String],
  homeTown: Option[String],
  info: Option[String],
  vkLink: Option[String],
  facebookLink: Option[String],
  linkedinLink: Option[String],
  instagramLink: Option[String]
)
object PersonTable {

  private val personParser: RowParser[PersonTable] = {
    get[UUID]("person.verification_code") ~
      get[String]("person.position") ~
      get[String]("person.first_name") ~
      get[String]("person.last_name") ~
      get[String]("person.middle_name") ~
      get[Option[String]]("person.phone") ~
      get[Option[String]]("person.home_town") ~
      get[Option[String]]("person.info") ~
      get[Option[String]]("person.vk_link") ~
      get[Option[String]]("person.facebook_link") ~
      get[Option[String]]("person.linkedin_link") ~
      get[Option[String]]("person.instagram_link") map {
      case verificationCode ~
            position ~
            firstName ~
            lastName ~
            middleName ~
            phone ~
            homeTown ~
            info ~
            vkLink ~
            facebookLink ~
            linkedinLink ~
            instagramLink =>
        PersonTable(
          verificationCode,
          position,
          firstName,
          lastName,
          middleName,
          phone,
          homeTown,
          info,
          vkLink,
          facebookLink,
          linkedinLink,
          instagramLink
        )
    }
  }

  def findPersonTableByVerCode(verificationCode: UUID)(implicit db: Database): Option[PersonTable] =
    db.withConnection { implicit connection =>
      SQL"SELECT * FROM person WHERE verification_code = CAST(${verificationCode.toString} AS UUID)".as(personParser.singleOpt)
    }

  // fixme: Не разобрался как вставить переменную fieldName с названием поля в запрос, чтобы не возникала ошибка. Пока приходится хардкодить
  def updatePersonTableFieldByVerCode(fieldName: String, fieldValue: String, verificationCode: UUID)(implicit db: Database): Boolean =
    db.withConnection { implicit connection =>
      val query = fieldName match {
        case "phone" => SQL"UPDATE person SET phone = $fieldValue where verification_code = CAST(${verificationCode.toString} AS UUID)"
        case "home_town" =>
          SQL"UPDATE person SET home_town = $fieldValue where verification_code = CAST(${verificationCode.toString} AS UUID)"
        case "info"    => SQL"UPDATE person SET info = $fieldValue where verification_code = CAST(${verificationCode.toString} AS UUID)"
        case "vk_link" => SQL"UPDATE person SET vk_link = $fieldValue where verification_code = CAST(${verificationCode.toString} AS UUID)"
        case "facebook_link" =>
          SQL"UPDATE person SET facebook_link = $fieldValue where verification_code = CAST(${verificationCode.toString} AS UUID)"
        case "linkedin_link" =>
          SQL"UPDATE person SET linkedin_link = $fieldValue where verification_code = CAST(${verificationCode.toString} AS UUID)"
        case "instagram_link" =>
          SQL"UPDATE person SET instagram_link = $fieldValue where verification_code = CAST(${verificationCode.toString} AS UUID)"
      }
      query.execute()
    }

}
