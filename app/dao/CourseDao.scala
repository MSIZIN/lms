package dao

import com.google.inject.ImplementedBy
import domain.model.{Course, Email, Student}

@ImplementedBy(classOf[CourseDaoImpl])
trait CourseDao {
  def isStudent(email: Email): Boolean
  def findCoursesByEmail(email: Email): Option[List[Course]]
}
