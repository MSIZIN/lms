package dao

import java.util.UUID

import anorm.SqlStringInterpolation
import dao.table.{AccountTable, EducationInfoTable, EducationalGroupTable, PersonTable}
import domain.model.{EducationInfo, EducationalGroup, Email, Password, Person, Profile}
import javax.inject._
import play.api.db.DBApi

@Singleton
class ProfileDaoImpl @Inject() (dbapi: DBApi) extends ProfileDao {

  private val db = dbapi.database("default")

  override def findProfileByEmail(email: Email): Option[Profile] = findVerCodeByEmail(email).flatMap(findProfileByVerCode)

  override def updatePersonStringFieldByEmail(fieldName: String, fieldValue: String, email: Email): Boolean =
    updatePersonStringFieldByVerCode(fieldName, fieldValue, findVerCodeByEmail(email).get)

  private def findVerCodeByEmail(email: Email): Option[UUID] =
    db.withConnection { implicit connection =>
        SQL"SELECT * FROM account WHERE email = ${email.value}".as(AccountTable.accountParser.singleOpt)
      }
      .map(_.personVerificationCode)

  private def findEducationalGroupById(id: Long): Option[EducationalGroup] =
    db.withConnection { implicit connection =>
        SQL"SELECT * FROM educational_group where id = $id".as(EducationalGroupTable.educationalGroupParser.singleOpt)
      }
      .map(queryRes => EducationalGroup(queryRes.name, queryRes.department, queryRes.courseNumber))

  private def findEducationInfoByVerCode(verificationCode: UUID): Option[EducationInfo] =
    db.withConnection { implicit connection =>
        SQL"SELECT * FROM education_info where student_id = CAST(${verificationCode.toString} AS UUID)".as(
          EducationInfoTable.educationInfoParser.singleOpt
        )
      }
      .map { queryRes =>
        val educationalGroupOpt = findEducationalGroupById(queryRes.groupId)
        EducationInfo(educationalGroupOpt.get, queryRes.admissionYear, queryRes.degree, queryRes.educationalForm, queryRes.educationalBasis)
      }

  private def findProfileByVerCode(verificationCode: UUID): Option[Profile] =
    db.withConnection { implicit connection =>
        SQL"SELECT * FROM person WHERE verification_code = CAST(${verificationCode.toString} AS UUID)".as(
          PersonTable.personParser.singleOpt
        )
      }
      .map { queryRes =>
        val person =
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

        val educationInfo =
          if (queryRes.position == "преподаватель")
            None
          else {
            findEducationInfoByVerCode(queryRes.verificationCode)
          }

        Profile(person, educationInfo)
      }

  // fixme: Не разобрался как вставить переменную fieldName с названием поля в запрос, чтобы не возникала ошибка. Пока приходится хардкодить
  private def updatePersonStringFieldByVerCode(fieldName: String, fieldValue: String, verificationCode: UUID): Boolean =
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
