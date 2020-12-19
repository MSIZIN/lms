package dao

import java.util.UUID

import dao.table.AccountTable._
import dao.table.EducationInfoTable._
import dao.table.PersonTable._
import dao.table.GroupsCoursesTable._
import dao.table.CourseTable._
import dao.table.TeachersCoursesTable._
import dao.table.GroupLeadersCoursesTable._
import dao.table.CourseMaterialTable._
import dao.table.HomeTaskTable._
import domain.model._
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

  override def findCourseInfoById(id: Long, email: Email): Option[CourseInfo] =
    for {
      course <- findCourseTableById(id)
      teachers = findTeachersCoursesTablesByCourseId(id).map(table => findTeacherByVerCode(table.teacherId).get)
      leaders = findGroupLeadersCourseTablesByCourseId(id).map(table => findStudentByVerCode(table.studentId).get)
      materials = findCourseMaterialTablesByCourseId(id).map(table =>
        CourseMaterial(table.id.get, table.name, table.content, table.dateOfAdding)
      )
      homeTasksTables = if (isStudent(email))
        findAvailableHomeTaskTablesByCourseId(id)
      else
        findHomeTaskTablesByCourseId(id)
      homeTasks = homeTasksTables.map(table => HomeTask(table.id.get, table.name, table.startDate, table.finishDate, table.description))
    } yield CourseInfo(
      course.name,
      course.description,
      teachers,
      leaders,
      materials,
      homeTasks
    )

  private def findVerCodeByEmail(email: Email): Option[UUID] =
    findAccountTableByEmail(email).map(_.personVerificationCode)

  private def findStudentByVerCode(verificationCode: UUID): Option[Student] =
    findPersonTableByVerCode(verificationCode)
      .filter(_.position == "студент")
      .map(personTable => Student(personTable.firstName, personTable.lastName, personTable.middleName))

  private def findTeacherByVerCode(verificationCode: UUID): Option[Teacher] =
    findPersonTableByVerCode(verificationCode)
      .filter(_.position == "преподаватель")
      .map(personTable => Teacher(personTable.firstName, personTable.lastName, personTable.middleName))

}
