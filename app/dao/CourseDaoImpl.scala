package dao

import java.time.LocalDate
import java.util.UUID

import dao.table.EducationInfoTable._
import dao.table.PersonTable._
import dao.table.GroupsCoursesTable._
import dao.table.CourseTable._
import dao.table.TeachersCoursesTable._
import dao.table.GroupLeadersCoursesTable._
import dao.table.CourseMaterialTable._
import dao.table.HomeTaskTable._
import CommonDaoActions._
import domain.model._
import javax.inject._
import play.api.db.{DBApi, Database}

@Singleton
class CourseDaoImpl @Inject() (dbapi: DBApi) extends CourseDao {

  implicit val db: Database = dbapi.database("default")

  override def isStudent(email: Email): Boolean = CommonDaoActions.isStudent(email)

  override def isTeacherOfCourse(courseId: Long, email: Email): Boolean = CommonDaoActions.isTeacherOfCourse(courseId, email)

  override def isStudentOfCourse(courseId: Long, email: Email): Boolean = CommonDaoActions.isStudentOfCourse(courseId, email)

  override def isGroupLeaderOfCourse(courseId: Long, email: Email): Boolean =
    (
      for {
        vercode <- findVerCodeByEmail(email)
        studentTable <- findGroupLeadersCourseTablesByCourseId(courseId).find(table => table.studentId == vercode)
      } yield studentTable
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

  override def findCourseIdByMaterialId(materialId: Long): Long = findCourseMaterialTableById(materialId).get.courseId

  override def findCourseIdByHomeTaskId(homeTaskId: Long): Long = findHomeTaskTableById(homeTaskId).get.courseId

  override def addCourseMaterial(courseId: Long, name: String, content: String): Unit =
    insertCourseMaterialTable(courseId, name, content)

  override def deleteCourseMaterial(id: Long): Unit = deleteCourseMaterialTable(id)

  override def updateCourseMaterial(id: Long, name: Option[String], content: Option[String]): Unit = {
    if (name.nonEmpty) updateNameInCourseMaterialTable(id, name.get)
    if (content.nonEmpty) updateContentInCourseMaterialTable(id, content.get)
    updateDateOfAddingInCourseMaterialTable(id)
  }

  override def addHomeTask(courseId: Long, name: String, startDate: LocalDate, finishDate: LocalDate, description: String): Unit =
    insertHomeTaskTable(courseId: Long, name: String, startDate: LocalDate, finishDate: LocalDate, description: String)

  override def deleteHomeTask(id: Long): Unit = deleteHomeTaskTable(id)

  override def updateHomeTask(
    id: Long,
    name: Option[String],
    timeInterval: Option[(LocalDate, LocalDate)],
    description: Option[String]
  ): Unit = {
    if (name.nonEmpty) updateNameInHomeTaskTable(id, name.get)
    if (timeInterval.nonEmpty) updateTimeIntervalInHomeTaskTable(id, timeInterval.get)
    if (description.nonEmpty) updateDescriptionInHomeTaskTable(id, description.get)
  }

  override def addGroupLeader(courseId: Long, email: Email): Unit =
    insertGroupLeadersCoursesTableTable(findVerCodeByEmail(email).get, courseId)

  override def deleteGroupLeader(courseId: Long, email: Email): Unit =
    deleteGroupLeadersCoursesTableTable(findVerCodeByEmail(email).get, courseId)

}
