package domain.service

import dao.CourseDao
import domain.model.{Course, Email}
import domain.service.messages._
import org.scalatestplus.play.PlaySpec

class CourseServiceImplSpec extends PlaySpec {

  val studentEmail: Email = Email("student@email.com")
  val teacherEmail: Email = Email("teacher@email.com")
  val courses: List[Course] = List(Course("course1", 1), Course("course2", 2))

  val courseDaoMock: CourseDao = new CourseDao {

    override def isStudent(email: Email): Boolean = email == studentEmail

    override def findCoursesByEmail(email: Email): Option[List[Course]] = email match {
      case email if isStudent(email)      => Some(courses)
      case email if email == teacherEmail => Some(courses)
      case _                              => None
    }

  }

  val courseService = new CourseServiceImpl(courseDaoMock)

  "courseList method" must {

    "return list of courses of student" in {
      courseService.courseList(CourseListRequest(studentEmail)) must equal(
        StudentCourseListSuccess(
          "Список курсов, на которые вы записаны:\n1. course1 (id курса: 1)\n2. course2 (id курса: 2)"
        )
      )
    }

    "return list of courses of teacher" in {
      courseService.courseList(CourseListRequest(teacherEmail)) must equal(
        TeacherCourseListSuccess(
          "Список курсов, которые вы ведёте:\n1. course1 (id курса: 1)\n2. course2 (id курса: 2)"
        )
      )
    }

    "not return list of courses of unknown email" in {
      courseService.courseList(CourseListRequest(Email("oops@email.com"))) must equal(CourseListFailure)
    }

  }

}
