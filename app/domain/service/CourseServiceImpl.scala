package domain.service

import java.time.LocalDate

import dao.CourseDao
import domain.model._
import domain.service.CourseServiceImpl.{decorateCourseInfo, decorateCourseList}
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

  override def course(request: CourseRequest): CourseResponse =
    courseDao.findCourseInfoById(request.id, request.email) match {
      case Some(courseInfo) => CourseSuccess(decorateCourseInfo(courseInfo))
      case None             => CourseFailure
    }

  override def addCourseMaterial(request: AddCourseMaterialRequest): AddCourseMaterialResponse =
    if (!courseDao.isGroupLeaderOfCourse(request.courseId, request.email) && !courseDao.isTeacherOfCourse(request.courseId, request.email))
      NotEnoughRightsToAddCourseMaterial
    else {
      courseDao.addCourseMaterial(request.courseId, request.name, request.content)
      AddCourseMaterialSuccess
    }

  override def deleteCourseMaterial(request: DeleteCourseMaterialRequest): DeleteCourseMaterialResponse = {
    val courseId = courseDao.findCourseIdByMaterialId(request.materialId)
    if (!courseDao.isGroupLeaderOfCourse(courseId, request.email) && !courseDao.isTeacherOfCourse(courseId, request.email))
      NotEnoughRightsToDeleteCourseMaterial
    else {
      courseDao.deleteCourseMaterial(request.materialId)
      DeleteCourseMaterialSuccess
    }
  }

  override def updateCourseMaterial(request: UpdateCourseMaterialRequest): UpdateCourseMaterialResponse = {
    val courseId = courseDao.findCourseIdByMaterialId(request.materialId)
    if (!courseDao.isGroupLeaderOfCourse(courseId, request.email) && !courseDao.isTeacherOfCourse(courseId, request.email))
      NotEnoughRightsToUpdateCourseMaterial
    else {
      courseDao.updateCourseMaterial(request.materialId, request.name, request.content)
      UpdateCourseMaterialSuccess
    }
  }

  override def addHomeTask(request: AddHomeTaskRequest): AddHomeTaskResponse =
    if (!courseDao.isTeacherOfCourse(request.courseId, request.email))
      NotEnoughRightsToAddHomeTask
    else {
      courseDao.addHomeTask(request.courseId, request.name, request.startDate, request.finishDate, request.description)
      AddHomeTaskSuccess
    }

  override def deleteHomeTask(request: DeleteHomeTaskRequest): DeleteHomeTaskResponse = {
    val courseId = courseDao.findCourseIdByHomeTaskId(request.homeTaskId)
    if (!courseDao.isTeacherOfCourse(courseId, request.email))
      NotEnoughRightsToDeleteHomeTask
    else {
      courseDao.deleteHomeTask(request.homeTaskId)
      DeleteHomeTaskSuccess
    }
  }

  override def updateHomeTask(request: UpdateHomeTaskRequest): UpdateHomeTaskResponse = {
    val courseId = courseDao.findCourseIdByHomeTaskId(request.homeTaskId)
    if (!courseDao.isTeacherOfCourse(courseId, request.email))
      NotEnoughRightsToUpdateHomeTask
    else {
      courseDao.updateHomeTask(request.homeTaskId, request.name, request.timeInterval, request.description)
      UpdateHomeTaskSuccess
    }
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

  private def decorateCourseInfo(courseInfo: CourseInfo): String =
    s"""Информация о курсе:
       |
       |Название: ${courseInfo.name}
       |Описание: ${courseInfo.description}
       |
       |Преподаватели:
       |${decorateFios(courseInfo.teachers.map(teacher => (teacher.lastName, teacher.firstName, teacher.middleName)))}
       |
       |Доверенные лица:
       |${decorateFios(courseInfo.groupLeaders.map(leader => (leader.lastName, leader.firstName, leader.middleName)))}
       |
       |Материалы курса:
       |
       |${decorateCourseMaterials(courseInfo.materials)}
       |
       |Домашние задания:
       |
       |${decorateHomeTasks(courseInfo.homeTasks)}""".stripMargin

  private def decorateFios(fios: List[(String, String, String)]): String =
    fios.sorted.zipWithIndex
      .map { case (fio, idx) => s"${idx + 1}. ${fio._1} ${fio._2} ${fio._3}" }
      .mkString("\n")

  private def decorateCourseMaterials(materials: List[CourseMaterial]): String =
    materials
      .map(material => (material.id, material.name, material.content, material.dateOfAdding))
      .sorted
      .map {
        case (id, name, content, date) =>
          s"""id: $id
            |Имя материала: $name
            |Содержимое: $content
            |Дата добавления: ${toRussionDateFormat(date)}""".stripMargin
      }
      .mkString("\n\n")

  private def decorateHomeTasks(homeTasks: List[HomeTask]): String =
    homeTasks
      .map(task => (task.id, task.name, task.startDate, task.finishDate, task.description))
      .sorted
      .map {
        case (id, name, startDate, finishDate, description) =>
          s"""id: $id
             |Название: $name
             |Интервал времени сдачи: ${toRussionDateFormat(startDate)} - ${toRussionDateFormat(finishDate)}
             |Описание: $description""".stripMargin
      }
      .mkString("\n\n")

  private def toRussionDateFormat(date: LocalDate): String = date.toString.split('-').reverse.mkString(".")

}
