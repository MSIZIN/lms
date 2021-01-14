package controllers

import controllers.ControllerHelpers._
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

  def addCourseMaterial(id: Long, name: String, content: String): Action[AnyContent] = Action { request =>
    withAuthenticatedUser(request, authService) { creds =>
      courseService.addCourseMaterial(AddCourseMaterialRequest(id, name, content, creds.email)) match {
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

}
