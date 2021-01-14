package dao

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
  def addCourseMaterial(id: Long, name: String, content: String): Boolean
  def deleteCourseMaterial(id: Long): Boolean
  def updateCourseMaterial(id: Long, name: Option[String], content: Option[String]): Boolean
}
