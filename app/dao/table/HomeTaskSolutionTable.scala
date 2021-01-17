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
      SQL"SELECT * FROM home_task_solution WHERE home_task_id = $homeTaskId AND student_id = CAST(${studentId.toString} AS UUID)"
        .as(homeTaskSolutionTableParser.singleOpt)
    }

  def insertHomeTaskSolutionTable(homeTaskId: Long, studentId: UUID, content: String)(
    implicit db: Database
  ): Boolean =
    db.withConnection { implicit connection =>
      SQL"INSERT INTO home_task_solution(home_task_id, student_id, date_of_adding, content) VALUES ($homeTaskId, CAST(${studentId.toString} AS UUID), NOW()::DATE, $content)"
        .execute()
    }

  def deleteHomeTaskSolutionTable(homeTaskId: Long, studentId: UUID)(implicit db: Database): Boolean =
    db.withConnection { implicit connection =>
      SQL"DELETE FROM home_task_solution WHERE home_task_id = $homeTaskId AND student_id = CAST(${studentId.toString} AS UUID)".execute()
    }

}
