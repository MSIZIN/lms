package dao

import com.google.inject.ImplementedBy
import domain.model.{EducationalGroup, Email, HomeTaskSolution, Student}

@ImplementedBy(classOf[HomeTaskSolutionDaoImpl])
trait HomeTaskSolutionDao {
  def doesHomeTaskBelongToTeacher(homeTaskId: Long, email: Email): Boolean
  def groupsOfHomeTask(homeTaskId: Long): List[EducationalGroup]
  def solutionsOfGroup(homeTaskId: Long, group: EducationalGroup): List[HomeTaskSolution]
}
