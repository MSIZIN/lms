package controllers

import controllers.ControllerHelpers._
import domain.model._
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

}
