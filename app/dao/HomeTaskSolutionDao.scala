package dao

import java.time.LocalDate

import com.google.inject.ImplementedBy
import domain.model.{EducationalGroup, Email, HomeTaskSolution}

@ImplementedBy(classOf[HomeTaskSolutionDaoImpl])
trait HomeTaskSolutionDao {
  def doesHomeTaskBelongToTeacher(homeTaskId: Long, email: Email): Boolean
  def doesHomeTaskBelongToStudent(homeTaskId: Long, email: Email): Boolean
  def groupsOfHomeTask(homeTaskId: Long): List[EducationalGroup]
  def solutionsOfGroup(homeTaskId: Long, group: EducationalGroup): List[HomeTaskSolution]
  def homeTaskTimeInterval(homeTaskId: Long): Option[(LocalDate, LocalDate)]
  def updateHomeTaskSolution(homeTaskId: Long, email: Email, content: String): Unit
}
