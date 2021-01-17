package domain.service.messages

import domain.model.Email

final case class HomeTaskSolutionsRequest(homeTaskId: Long, email: Email)

sealed trait HomeTaskSolutionsResponse
final case class HomeTaskSolutionsSuccess(content: String) extends HomeTaskSolutionsResponse
case object NotEnoughRightsToSeeSolutions extends HomeTaskSolutionsResponse
