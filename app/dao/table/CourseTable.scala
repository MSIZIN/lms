package dao.table

import anorm.SqlParser.get
import anorm.{RowParser, SqlStringInterpolation, ~}
import play.api.db.Database

final case class CourseTable(
  id: Option[Long] = None,
  name: String,
  description: String
)
object CourseTable {

  private val courseParser: RowParser[CourseTable] = {
    get[Option[Long]]("course.id") ~
      get[String]("course.name") ~
      get[String]("course.description") map {
      case id ~ name ~ description => CourseTable(id, name, description)
    }
  }

  def findCourseTableById(id: Long)(implicit db: Database): Option[CourseTable] =
    db.withConnection { implicit connection =>
      SQL"SELECT * FROM course WHERE id = $id".as(courseParser.singleOpt)
    }

}
