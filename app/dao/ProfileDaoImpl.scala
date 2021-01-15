package dao

import java.util.UUID

import dao.table.AccountTable._
import dao.table.EducationInfoTable._
import dao.table.EducationalGroupTable._
import dao.table.PersonTable._
import domain.model.{EducationInfo, EducationalGroup, Email, Person, Profile, Student}
import javax.inject._
import play.api.db.{DBApi, Database}

@Singleton
class ProfileDaoImpl @Inject() (dbapi: DBApi) extends ProfileDao {

  implicit val db: Database = dbapi.database("default")

  override def findProfileByEmail(email: Email): Option[Profile] =
    for {
      vercode <- findVerCodeByEmail(email)
      profile <- findProfileByVerCode(vercode)
    } yield profile

  override def updatePersonFieldByEmail(fieldName: String, fieldValue: String, email: Email): Unit = {
    val verificationCode = findVerCodeByEmail(email).get
    updatePersonTableFieldByVerCode(fieldName, fieldValue, verificationCode)
  }

  override def findGroupmatesByEmail(email: Email): Option[List[Student]] =
    for {
      vercode <- findVerCodeByEmail(email)
      groupId <- findGroupIdByVerCode(vercode)
      educInfoTables = findEducInfoTablesByGroupId(groupId)
      groupmates = educInfoTables
        .map(educInfoTable => educInfoTable.studentId)
        .flatMap(findPersonTableByVerCode)
        .map(personTable => Student(personTable.firstName, personTable.lastName, personTable.middleName))
    } yield groupmates

  private def findGroupIdByVerCode(verificationCode: UUID): Option[Long] =
    findEducInfoTableByVerCode(verificationCode).map(_.groupId)

  private def findVerCodeByEmail(email: Email): Option[UUID] =
    findAccountTableByEmail(email).map(_.personVerificationCode)

  private def findEducationalGroupById(id: Long): Option[EducationalGroup] =
    findEducGroupTableById(id).map(educGroupTable =>
      EducationalGroup(educGroupTable.name, educGroupTable.department, educGroupTable.courseNumber)
    )

  private def findEducationInfoByVerCode(verificationCode: UUID): Option[EducationInfo] =
    for {
      educInfoTable <- findEducInfoTableByVerCode(verificationCode)
      educationalGroup <- findEducationalGroupById(educInfoTable.groupId)
    } yield EducationInfo(
      educationalGroup,
      educInfoTable.admissionYear,
      educInfoTable.degree,
      educInfoTable.educationalForm,
      educInfoTable.educationalBasis
    )

  private def findProfileByVerCode(verificationCode: UUID): Option[Profile] =
    for {
      personTable <- findPersonTableByVerCode(verificationCode)
      person = Person(
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
      educationInfo = if (personTable.position == "преподаватель")
        None
      else
        findEducationInfoByVerCode(personTable.verificationCode)
    } yield Profile(person, educationInfo)

}
