package domain.service

import dao.CourseDao
import domain.model.Course
import domain.service.CourseServiceImpl.decorateCourseList
import domain.service.messages._
import javax.inject._

@Singleton
class CourseServiceImpl @Inject() (courseDao: CourseDao) extends CourseService {

  override def courseList(request: CourseListRequest): CourseListResponse =
    courseDao.findCoursesByEmail(request.email) match {
      case Some(courses) if courseDao.isStudent(request.email) =>
        StudentCourseListSuccess("Список курсов, на которые вы записаны:\n" + decorateCourseList(courses))
      case Some(courses) => TeacherCourseListSuccess("Список курсов, которые вы ведёте:\n" + decorateCourseList(courses))
      case None          => CourseListFailure
    }

}
object CourseServiceImpl {

  private def decorateCourseList(courses: List[Course]): String =
    courses
      .map(course => (course.name, course.id))
      .sorted
      .zipWithIndex
      .map { case ((courseName, id), idx) => s"${idx + 1}. $courseName (id курса: $id)" }
      .mkString("\n")

}
