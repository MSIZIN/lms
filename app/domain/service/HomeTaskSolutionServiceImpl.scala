package domain.service

import java.time.LocalDate

import dao.HomeTaskSolutionDao
import domain.model._
import domain.service.messages._
import javax.inject._

@Singleton
class HomeTaskSolutionServiceImpl @Inject() (homeTaskSolutionDao: HomeTaskSolutionDao) extends HomeTaskSolutionService {

  import HomeTaskSolutionServiceImpl._

  override def homeTaskSolutions(request: HomeTaskSolutionsRequest): HomeTaskSolutionsResponse =
    if (!homeTaskSolutionDao.doesHomeTaskBelongToTeacher(request.homeTaskId, request.email))
      NotEnoughRightsToSeeSolutions
    else {
      val groups = homeTaskSolutionDao.groupsOfHomeTask(request.homeTaskId)
      HomeTaskSolutionsSuccess(
        decorateHomeTaskSolutions(groups.map(group => group -> homeTaskSolutionDao.solutionsOfGroup(request.homeTaskId, group)))
      )
    }

  override def updateHomeTaskSol(request: UpdateHomeTaskSolutionRequest): UpdateHomeTaskSolutionResponse = {
    val timeInterval = homeTaskSolutionDao.homeTaskTimeInterval(request.homeTaskId)
    if (!homeTaskSolutionDao.doesHomeTaskBelongToStudent(request.homeTaskId, request.email))
      NotEnoughRightsToUpdateSolution
    else if (timeInterval.fold(true)(interval => LocalDate.now().isBefore(interval._1)))
      HomeTaskIsNotAvailable
    else if (timeInterval.map(interval => LocalDate.now().isAfter(interval._2)).get)
      DeadlineHasExpired
    else {
      homeTaskSolutionDao.updateHomeTaskSolution(request.homeTaskId, request.email, request.content)
      UpdateHomeTaskSolutionSuccess
    }
  }

}
object HomeTaskSolutionServiceImpl {

  private def decorateHomeTaskSolutions(groupsToSolutions: List[(EducationalGroup, List[HomeTaskSolution])]): String =
    "Прогресс выполнения студентами этого домашнего задания:\n\n\n\n" + groupsToSolutions
      .sortBy(_._1.name)
      .map {
        case (group, solutions) =>
          s"Группа ${group.name}:\n\n" + solutions.map(decorateSolution).mkString("\n\n") + "\n\n"
      }
      .mkString("\n\n")

  private def decorateSolution(solution: HomeTaskSolution): String =
    s"Студент: ${solution.student.lastName} ${solution.student.firstName} ${solution.student.middleName}\n" +
      (
        if (solution.dateOfAdding.isEmpty)
          "Решение ещё не отправлено"
        else
          s"Дата отправки решения: ${toRussianDateFormat(solution.dateOfAdding.get)}\nСодержание: ${solution.content.get}"
      )

  private def toRussianDateFormat(date: LocalDate): String = date.toString.split('-').reverse.mkString(".")

}
