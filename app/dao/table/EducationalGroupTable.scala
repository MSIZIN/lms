package dao.table

import anorm.SqlParser.get
import anorm.{RowParser, ~}

final case class EducationalGroupTable(
  id: Option[Long] = None,
  name: String,
  department: String,
  courseNumber: Int
)
object EducationalGroupTable {
  val educationalGroupParser: RowParser[EducationalGroupTable] = {
    get[Option[Long]]("educational_group.id") ~
      get[String]("educational_group.name") ~
      get[String]("educational_group.department") ~
      get[Int]("educational_group.course_number") map {
      case id ~ name ~ department ~ courseNumber =>
        EducationalGroupTable(id, name, department, courseNumber)
    }
  }
}
