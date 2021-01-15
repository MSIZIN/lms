package dao

import java.time.LocalDate

import com.google.inject.ImplementedBy
import domain.model._

@ImplementedBy(classOf[CourseDaoImpl])
trait CourseDao {
  def isStudent(email: Email): Boolean
  def isTeacherOfCourse(courseId: Long, email: Email): Boolean
  def isGroupLeaderOfCourse(courseId: Long, email: Email): Boolean
  def findCoursesByEmail(email: Email): Option[List[Course]]
  def findCourseInfoById(id: Long, email: Email): Option[CourseInfo]
  def findCourseIdByMaterialId(materialId: Long): Long
  def findCourseIdByHomeTaskId(homeTaskId: Long): Long
  def addCourseMaterial(courseId: Long, name: String, content: String): Unit
  def deleteCourseMaterial(id: Long): Unit
  def updateCourseMaterial(id: Long, name: Option[String], content: Option[String]): Unit
  def addHomeTask(courseId: Long, name: String, startDate: LocalDate, finishDate: LocalDate, description: String): Unit
  def deleteHomeTask(id: Long): Unit
  def updateHomeTask(id: Long, name: Option[String], timeInterval: Option[(LocalDate, LocalDate)], description: Option[String]): Unit
}
