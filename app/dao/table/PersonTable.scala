package dao.table

import java.util.UUID

import anorm.SqlParser.get
import anorm.{RowParser, ~}

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
  val personParser: RowParser[PersonTable] = {
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
}
