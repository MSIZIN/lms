package dao.table

import java.time.LocalDate
import java.util.UUID

import anorm.SqlParser.get
import anorm.{RowParser, SqlStringInterpolation, ~}
import play.api.db.Database

final case class HomeTaskSolutionTable(
  homeTaskId: Long,
  studentId: UUID,
  dateOfAdding: LocalDate,
  content: String
)
object HomeTaskSolutionTable {

  private val homeTaskSolutionTableParser: RowParser[HomeTaskSolutionTable] = {
    get[Long]("home_task_solution.home_task_id") ~
      get[UUID]("home_task_solution.student_id") ~
      get[LocalDate]("home_task_solution.date_of_adding") ~
      get[String]("home_task_solution.content") map {
      case homeTaskId ~ studentId ~ dateOfAdding ~ content => HomeTaskSolutionTable(homeTaskId, studentId, dateOfAdding, content)
    }
  }

  def findHomeTaskSolutionTable(homeTaskId: Long, studentId: UUID)(implicit db: Database): Option[HomeTaskSolutionTable] =
    db.withConnection { implicit connection =>
      SQL"SELECT * FROM home_task_solution where home_task_id = $homeTaskId AND student_id = CAST(${studentId.toString} AS UUID)"
        .as(homeTaskSolutionTableParser.singleOpt)
    }

}
