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

  def updateHomeTaskSolution(id: Long, content: String): Action[AnyContent] = Action { request =>
    withAuthenticatedUser(request, authService) { creds =>
      homeTaskSolutionService.updateHomeTaskSol(UpdateHomeTaskSolutionRequest(id, creds.email, content)) match {
        case UpdateHomeTaskSolutionSuccess => Ok("Решение успешно отправлено")
        case NotEnoughRightsToUpdateSolution =>
          BadRequest("Чтобы отправлять решения домашнего задания, нужно быть студентом соотвествующего курса")
        case HomeTaskIsNotAvailable => BadRequest("Нет доступного домашнего задания с таким id")
        case DeadlineHasExpired     => BadRequest("Срок сдачи задания истёк")
      }
    }
  }

}
