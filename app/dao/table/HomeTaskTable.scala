package dao.table

import java.time.LocalDate

import anorm.SqlParser.get
import anorm.{RowParser, SqlStringInterpolation, ~}
import play.api.db.Database

final case class HomeTaskTable(
  id: Option[Long] = None,
  courseId: Long,
  name: String,
  startDate: LocalDate,
  finishDate: LocalDate,
  description: String
)
object HomeTaskTable {

  private val homeTaskParser: RowParser[HomeTaskTable] = {
    get[Option[Long]]("home_task.id") ~
      get[Long]("home_task.course_id") ~
      get[String]("home_task.name") ~
      get[LocalDate]("home_task.start_date") ~
      get[LocalDate]("home_task.finish_date") ~
      get[String]("home_task.description") map {
      case id ~ courseId ~ name ~ startDate ~ finishDate ~ description =>
        HomeTaskTable(id, courseId, name, startDate, finishDate, description)
    }
  }

  def findHomeTaskTablesByCourseId(courseId: Long)(implicit db: Database): List[HomeTaskTable] =
    db.withConnection { implicit connection =>
      SQL"SELECT * FROM home_task where course_id = $courseId".as(homeTaskParser.*)
    }

  def findAvailableHomeTaskTablesByCourseId(courseId: Long)(implicit db: Database): List[HomeTaskTable] =
    db.withConnection { implicit connection =>
      SQL"SELECT * FROM home_task where (course_id = $courseId AND start_date <= NOW())".as(homeTaskParser.*)
    }

}
