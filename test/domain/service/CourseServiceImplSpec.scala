package domain.service

import java.time.LocalDate

import dao.CourseDao
import domain.model._
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

    override def findCourseInfoById(id: Long, email: Email): Option[CourseInfo] =
      if (id == 1) {
        Some(
          CourseInfo(
            "Основы прокрастинации",
            "Описание курса добавится на следующей неделе.",
            List(Teacher("Елизавета", "Балтинова", "Игоревна"), Teacher("Николай", "Дубровский", "Фёдорович")),
            List(Student("Галина", "Епископова", "Семёновна")),
            List(
              CourseMaterial(
                5,
                "Материал по первому занятию",
                "Кто-то из преподавателей добавит этот материл, как только пройдёт первое занятие",
                LocalDate.of(2020, 10, 2)
              )
            ),
            List(HomeTask(4, "В последний момент", LocalDate.of(2020, 10, 15), LocalDate.of(2020, 12, 1), "Нужно прислать любой текст."))
          )
        )
      } else
        None

    override def isTeacherOfCourse(courseId: Long, email: Email): Boolean = true

    override def isGroupLeaderOfCourse(courseId: Long, email: Email): Boolean = true

    override def findCourseIdByMaterialId(materialId: Long): Long = 1

    override def addCourseMaterial(courseId: Long, name: String, content: String): Unit = ()

    override def deleteCourseMaterial(id: Long): Unit = ()

    override def updateCourseMaterial(id: Long, name: Option[String], content: Option[String]): Unit = ()

    override def findCourseIdByHomeTaskId(homeTaskId: Long): Long = 1

    override def addHomeTask(courseId: Long, name: String, startDate: LocalDate, finishDate: LocalDate, description: String): Unit = ()

    override def deleteHomeTask(id: Long): Unit = ()

    override def updateHomeTask(
      id: Long,
      name: Option[String],
      timeInterval: Option[(LocalDate, LocalDate)],
      description: Option[String]
    ): Unit = ()

    override def isStudentOfCourse(courseId: Long, email: Email): Boolean = true

    override def addGroupLeader(courseId: Long, email: Email): Unit = ()

    override def deleteGroupLeader(courseId: Long, email: Email): Unit = ()
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

  "course method" must {

    "return course info of known course id" in {
      courseService.course(CourseRequest(1, studentEmail)) must equal(
        CourseSuccess(
          """Информация о курсе:
            |
            |Название: Основы прокрастинации
            |Описание: Описание курса добавится на следующей неделе.
            |
            |Преподаватели:
            |1. Балтинова Елизавета Игоревна
            |2. Дубровский Николай Фёдорович
            |
            |Доверенные лица:
            |1. Епископова Галина Семёновна
            |
            |Материалы курса:
            |
            |id: 5
            |Имя материала: Материал по первому занятию
            |Содержимое: Кто-то из преподавателей добавит этот материл, как только пройдёт первое занятие
            |Дата добавления: 02.10.2020
            |
            |Домашние задания:
            |
            |id: 4
            |Название: В последний момент
            |Интервал времени сдачи: 15.10.2020 - 01.12.2020
            |Описание: Нужно прислать любой текст.""".stripMargin
        )
      )
    }

    "not return course info of unknown course id" in {
      courseService.course(CourseRequest(404, studentEmail)) must equal(CourseFailure)
    }

  }

}
