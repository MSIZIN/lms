package dao

import java.time.LocalDate

import dao.table.EducationInfoTable._
import dao.table.GroupsCoursesTable._
import dao.table.HomeTaskSolutionTable._
import dao.table.EducationalGroupTable._
import dao.table.HomeTaskTable._
import dao.CommonDaoActions._
import domain.model._
import javax.inject._
import play.api.db.{DBApi, Database}

@Singleton
class HomeTaskSolutionDaoImpl @Inject() (dbapi: DBApi) extends HomeTaskSolutionDao {

  implicit val db: Database = dbapi.database("default")

  override def doesHomeTaskBelongToTeacher(homeTaskId: Long, email: Email): Boolean =
    findHomeTaskTableById(homeTaskId).fold(false)(table => isTeacherOfCourse(table.courseId, email))

  override def doesHomeTaskBelongToStudent(homeTaskId: Long, email: Email): Boolean =
    findHomeTaskTableById(homeTaskId).fold(false)(table => isStudentOfCourse(table.courseId, email))

  override def groupsOfHomeTask(homeTaskId: Long): List[EducationalGroup] = {
    val courseId = findHomeTaskTableById(homeTaskId).map(_.courseId).get
    findGroupsCoursesTablesByCourseId(courseId)
      .map(grCourTable => findEducationalGroupById(grCourTable.groupId))
      .filter(_.nonEmpty)
      .map(_.get)
  }

  override def solutionsOfGroup(homeTaskId: Long, group: EducationalGroup): List[HomeTaskSolution] = {
    val groupId = findEducGroupTableByName(group.name).get.id.get
    val studentIds = findEducInfoTablesByGroupId(groupId).map(_.studentId)
    studentIds.map { studentId =>
      val htsTable = findHomeTaskSolutionTable(homeTaskId, studentId)
      HomeTaskSolution(findStudentByVerCode(studentId).get, htsTable.map(_.dateOfAdding), htsTable.map(_.content))
    }
  }

  private def findEducationalGroupById(id: Long): Option[EducationalGroup] =
    findEducGroupTableById(id).map(educGroupTable =>
      EducationalGroup(educGroupTable.name, educGroupTable.department, educGroupTable.courseNumber)
    )

  override def homeTaskTimeInterval(homeTaskId: Long): Option[(LocalDate, LocalDate)] =
    findHomeTaskTableById(homeTaskId).map(table => table.startDate -> table.finishDate)

  override def updateHomeTaskSolution(homeTaskId: Long, email: Email, content: String): Unit = {
    val verCode = findVerCodeByEmail(email).get
    if (findHomeTaskSolutionTable(homeTaskId, verCode).nonEmpty) deleteHomeTaskSolutionTable(homeTaskId, verCode)
    insertHomeTaskSolutionTable(homeTaskId, verCode, content)
  }

}
