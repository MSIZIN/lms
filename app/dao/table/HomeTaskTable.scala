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
      SQL"SELECT * FROM home_task where (course_id = $courseId AND start_date <= NOW()::DATE)".as(homeTaskParser.*)
    }

  def findHomeTaskTableById(id: Long)(implicit db: Database): Option[HomeTaskTable] =
    db.withConnection { implicit connection =>
      SQL"SELECT * FROM home_task where id = $id".as(homeTaskParser.singleOpt)
    }

  def insertHomeTaskTable(courseId: Long, name: String, startDate: LocalDate, finishDate: LocalDate, description: String)(
    implicit db: Database
  ): Boolean =
    db.withConnection { implicit connection =>
      SQL"INSERT INTO home_task(course_id, name, start_date, finish_date, description) VALUES ($courseId, $name, $startDate, $finishDate, $description)"
        .execute()
    }

  def deleteHomeTaskTable(id: Long)(implicit db: Database): Boolean =
    db.withConnection { implicit connection =>
      SQL"DELETE FROM home_task WHERE id=$id".execute()
    }

  def updateNameInHomeTaskTable(id: Long, name: String)(implicit db: Database): Boolean =
    db.withConnection { implicit connection =>
      SQL"UPDATE home_task SET name = $name WHERE id=$id".execute()
    }

  def updateTimeIntervalInHomeTaskTable(id: Long, timeInterval: (LocalDate, LocalDate))(implicit db: Database): Boolean =
    db.withConnection { implicit connection =>
      SQL"UPDATE home_task SET start_date = ${timeInterval._1}, finish_date = ${timeInterval._2} WHERE id=$id".execute()
    }

  def updateDescriptionInHomeTaskTable(id: Long, description: String)(implicit db: Database): Boolean =
    db.withConnection { implicit connection =>
      SQL"UPDATE home_task SET description = $description WHERE id=$id".execute()
    }

}
