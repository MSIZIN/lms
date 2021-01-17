package controllers

import controllers.ControllerHelpers._
import domain.service._
import domain.service.messages._
import javax.inject._
import play.api.mvc._

@Singleton
class HomeTaskSolutionController @Inject() (
  authService: AuthService,
  homeTaskSolutionService: HomeTaskSolutionService,
  cc: ControllerComponents
) extends AbstractController(cc) {

  def homeTaskSolutions(id: Long): Action[AnyContent] = Action { request =>
    withAuthenticatedUser(request, authService) { creds =>
      homeTaskSolutionService.homeTaskSolutions(HomeTaskSolutionsRequest(id, creds.email)) match {
        case HomeTaskSolutionsSuccess(content) => Ok(content)
        case NotEnoughRightsToSeeSolutions =>
          BadRequest("Чтобы просматривать решения домашнего задания, нужно быть преподавателем соотвествующего курса")
      }
    }
  }

}
