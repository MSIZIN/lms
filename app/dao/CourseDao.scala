package dao

import com.google.inject.ImplementedBy
import domain.model._

@ImplementedBy(classOf[CourseDaoImpl])
trait CourseDao {
  def isStudent(email: Email): Boolean
  def findCoursesByEmail(email: Email): Option[List[Course]]
  def findCourseInfoById(id: Long, email: Email): Option[CourseInfo]
}
