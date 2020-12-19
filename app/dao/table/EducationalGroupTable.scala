package dao.table

import anorm.SqlParser.get
import anorm.{RowParser, SqlStringInterpolation, ~}
import play.api.db.Database

final case class EducationalGroupTable(
  id: Option[Long] = None,
  name: String,
  department: String,
  courseNumber: Int
)
object EducationalGroupTable {

  private val educationalGroupParser: RowParser[EducationalGroupTable] = {
    get[Option[Long]]("educational_group.id") ~
      get[String]("educational_group.name") ~
      get[String]("educational_group.department") ~
      get[Int]("educational_group.course_number") map {
      case id ~ name ~ department ~ courseNumber => EducationalGroupTable(id, name, department, courseNumber)
    }
  }

  def findEducGroupTableById(id: Long)(implicit db: Database): Option[EducationalGroupTable] =
    db.withConnection { implicit connection =>
      SQL"SELECT * FROM educational_group where id = $id".as(educationalGroupParser.singleOpt)
    }

}
