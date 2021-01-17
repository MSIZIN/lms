package domain.service

import com.google.inject.ImplementedBy
import domain.service.messages._

@ImplementedBy(classOf[HomeTaskSolutionServiceImpl])
trait HomeTaskSolutionService {
  def homeTaskSolutions(request: HomeTaskSolutionsRequest): HomeTaskSolutionsResponse
  def updateHomeTaskSol(request: UpdateHomeTaskSolutionRequest): UpdateHomeTaskSolutionResponse
}
