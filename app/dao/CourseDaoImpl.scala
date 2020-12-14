package dao

import java.util.UUID

import dao.table.AccountTable._
import dao.table.EducationInfoTable._
import dao.table.PersonTable._
import dao.table.GroupsCoursesTable._
import dao.table.CourseTable._
import dao.table.TeachersCoursesTable._
import domain.model.{Course, Email, Student}
import javax.inject._
import play.api.db.{DBApi, Database}

@Singleton
class CourseDaoImpl @Inject() (dbapi: DBApi) extends CourseDao {

  implicit val db: Database = dbapi.database("default")

  override def isStudent(email: Email): Boolean =
    (
      for {
        vercode <- findVerCodeByEmail(email)
        student <- findStudentByVerCode(vercode)
      } yield student
    ) match {
      case Some(_) => true
      case None    => false
    }

  override def findCoursesByEmail(email: Email): Option[List[Course]] =
    for {
      vercode <- findVerCodeByEmail(email)
      courseIds = if (isStudent(email)) {
        val groupId = findEducInfoTableByVerCode(vercode).map(_.groupId).get
        findGroupsCoursesTablesByGroupId(groupId).map(_.courseId)
      } else
        findTeachersCoursesTablesByVerCode(vercode).map(_.courseId)
    } yield courseIds.map(id => Course(findCourseTableById(id).get.name, id))

  private def findVerCodeByEmail(email: Email): Option[UUID] =
    findAccountTableByEmail(email).map(_.personVerificationCode)

  private def findStudentByVerCode(verificationCode: UUID): Option[Student] =
    findPersonTableByVerCode(verificationCode)
      .filter(_.position == "студент")
      .map(personTable => Student(personTable.firstName, personTable.lastName, personTable.middleName))

}
