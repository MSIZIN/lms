package controllers

import controllers.ControllerHelpers._
import domain.model.Email
import domain.service._
import domain.service.messages._
import javax.inject._
import play.api.mvc._

@Singleton
class CourseController @Inject() (authService: AuthService, courseService: CourseService, cc: ControllerComponents)
    extends AbstractController(cc) {

  def courseList: Action[AnyContent] = Action { request =>
    withAuthenticatedUser(request, authService) { creds =>
      courseService.courseList(CourseListRequest(creds.email)) match {
        case StudentCourseListSuccess(content) => Ok(content)
        case TeacherCourseListSuccess(content) => Ok(content)
        case CourseListFailure                 => InternalServerError("Произошла ошибка на сервере при попытке получить список ваших курсов")
      }
    }
  }

  def course(id: Long): Action[AnyContent] = Action { request =>
    withAuthenticatedUser(request, authService) { creds =>
      courseService.course(CourseRequest(id, creds.email)) match {
        case CourseSuccess(content) => Ok(content)
        case CourseFailure          => BadRequest("Не удалось найти курс с таким id")
      }
    }
  }

  def addCourseMaterial(courseId: Long, name: String, content: String): Action[AnyContent] = Action { request =>
    withAuthenticatedUser(request, authService) { creds =>
      courseService.addCourseMaterial(AddCourseMaterialRequest(courseId, name, content, creds.email)) match {
        case AddCourseMaterialSuccess => Ok("Материал курса успешно добавлен")
        case NotEnoughRightsToAddCourseMaterial =>
          BadRequest("Чтобы добавлять материалы курса, нужно быть либо преподавателем этого курса либо доверенным лицом")
      }
    }
  }

  def deleteCourseMaterial(id: Long): Action[AnyContent] = Action { request =>
    withAuthenticatedUser(request, authService) { creds =>
      courseService.deleteCourseMaterial(DeleteCourseMaterialRequest(id, creds.email)) match {
        case DeleteCourseMaterialSuccess => Ok("Материал курса успешно удалён")
        case NotEnoughRightsToDeleteCourseMaterial =>
          BadRequest("Чтобы удалять материалы курса, нужно быть либо преподавателем этого курса либо доверенным лицом")
      }
    }
  }

  def updateCourseMaterial(id: Long, name: Option[String], content: Option[String]): Action[AnyContent] = Action { request =>
    withAuthenticatedUser(request, authService) { creds =>
      courseService.updateCourseMaterial(UpdateCourseMaterialRequest(id, name, content, creds.email)) match {
        case UpdateCourseMaterialSuccess => Ok("Материал курса успешно изменён")
        case NotEnoughRightsToUpdateCourseMaterial =>
          BadRequest("Чтобы модифицировать материалы курса, нужно быть либо преподавателем этого курса либо доверенным лицом")
      }
    }
  }

  def addHomeTask(courseId: Long, name: String, startDate: String, finishDate: String, description: String): Action[AnyContent] =
    Action { request =>
      withAuthenticatedUser(request, authService) { creds =>
        val startLocalDate = parseDate(startDate)
        val finishLocalDate = parseDate(finishDate)
        if (startLocalDate.isEmpty || finishLocalDate.isEmpty)
          BadRequest("Дата введена неправильно (формат должен быть такой: год-месяц-день)")
        else if (startLocalDate.get.isAfter(finishLocalDate.get))
          BadRequest("Дата начала времени сдачи не должна быть позже даты конца времени сдачи")
        else
          courseService.addHomeTask(AddHomeTaskRequest(courseId, name, startLocalDate.get, finishLocalDate.get, description, creds.email)) match {
            case AddHomeTaskSuccess           => Ok("Домашнее задание успешно добавлено")
            case NotEnoughRightsToAddHomeTask => BadRequest("Чтобы добавлять домашние задания, нужно быть преподавателем")
          }
      }
    }

  def deleteHomeTask(id: Long): Action[AnyContent] = Action { request =>
    withAuthenticatedUser(request, authService) { creds =>
      courseService.deleteHomeTask(DeleteHomeTaskRequest(id, creds.email)) match {
        case DeleteHomeTaskSuccess           => Ok("Домашнее задание успешно удалено")
        case NotEnoughRightsToDeleteHomeTask => BadRequest("Чтобы удалять домашние задания, нужно быть преподавателем")
      }
    }
  }

  def updateHomeTask(
    id: Long,
    name: Option[String],
    startDate: Option[String],
    finishDate: Option[String],
    description: Option[String]
  ): Action[AnyContent] = Action { request =>
    withAuthenticatedUser(request, authService) { creds =>
      val startLocalDate = startDate.flatMap(date => parseDate(date))
      val finishLocalDate = finishDate.flatMap(date => parseDate(date))
      if (startDate.nonEmpty && finishDate.isEmpty || startDate.isEmpty && finishDate.nonEmpty)
        BadRequest("Чтобы изменить временной интервал, введите начало и конец нового интервала")
      else if (startDate.nonEmpty && finishDate.nonEmpty && (startLocalDate.isEmpty || finishLocalDate.isEmpty))
        BadRequest("Дата введена неправильно (формат должен быть такой: год-месяц-день)")
      else if (startDate.nonEmpty && finishDate.nonEmpty && startLocalDate.get.isAfter(finishLocalDate.get))
        BadRequest("Дата начала времени сдачи не должна быть позже даты конца времени сдачи")
      else {
        val timeInterval =
          for {
            start <- startLocalDate
            finish <- finishLocalDate
          } yield (start, finish)
        courseService.updateHomeTask(UpdateHomeTaskRequest(id, name, timeInterval, description, creds.email)) match {
          case UpdateHomeTaskSuccess           => Ok("Домашнее задание успешно изменено")
          case NotEnoughRightsToUpdateHomeTask => BadRequest("Чтобы модифицировать домашние задания, нужно быть преподавателем")
        }
      }
    }
  }

  def addGroupLeader(courseId: Long, email: String): Action[AnyContent] = Action { request =>
    withAuthenticatedUser(request, authService) { creds =>
      courseService.addGroupLeader(AddGroupLeaderRequest(courseId, Email(email), creds.email)) match {
        case AddGroupLeaderSuccess           => Ok("Студент успешно добавлен в список доверенных лиц")
        case StudentIsNotEnrolledInCourse    => BadRequest("Студент не записан на этот курс")
        case StudentIsAlreadyGroupLeader     => BadRequest("Студент уже является доверенным лицом")
        case NotEnoughRightsToAddGroupLeader => BadRequest("Чтобы сделать студента доверенным лицом, нужно быть преподавателем этого курса")
      }
    }
  }

  def deleteGroupLeader(courseId: Long, email: String): Action[AnyContent] = Action { request =>
    withAuthenticatedUser(request, authService) { creds =>
      courseService.deleteGroupLeader(DeleteGroupLeaderRequest(courseId, Email(email), creds.email)) match {
        case DeleteGroupLeaderSuccess => Ok("Студент успешно убран из списка доверенных лиц")
        case StudentIsNotGroupLeader  => BadRequest("Студент не является доверенным лицом")
        case NotEnoughRightsToDeleteGroupLeader =>
          BadRequest("Чтобы убрать студента из списка доверенных лиц, нужно быть преподавателем этого курса")
      }
    }
  }

}
