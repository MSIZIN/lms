package domain.service.messages

import domain.model.Email

final case class HomeTaskSolutionsRequest(homeTaskId: Long, email: Email)

sealed trait HomeTaskSolutionsResponse
final case class HomeTaskSolutionsSuccess(content: String) extends HomeTaskSolutionsResponse
case object NotEnoughRightsToSeeSolutions extends HomeTaskSolutionsResponse

final case class UpdateHomeTaskSolutionRequest(homeTaskId: Long, email: Email, content: String)

sealed trait UpdateHomeTaskSolutionResponse
case object UpdateHomeTaskSolutionSuccess extends UpdateHomeTaskSolutionResponse
case object NotEnoughRightsToUpdateSolution extends UpdateHomeTaskSolutionResponse
case object HomeTaskIsNotAvailable extends UpdateHomeTaskSolutionResponse
case object DeadlineHasExpired extends UpdateHomeTaskSolutionResponse
