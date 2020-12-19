package dao.table

import java.time.LocalDate

import anorm.SqlParser.get
import anorm.{RowParser, SqlStringInterpolation, ~}
import play.api.db.Database

final case class CourseMaterialTable(
  id: Option[Long] = None,
  courseId: Long,
  name: String,
  content: String,
  dateOfAdding: LocalDate
)
object CourseMaterialTable {

  private val courseMaterialParser: RowParser[CourseMaterialTable] = {
    get[Option[Long]]("course_material.id") ~
      get[Long]("course_material.course_id") ~
      get[String]("course_material.name") ~
      get[String]("course_material.content") ~
      get[LocalDate]("course_material.date_of_adding") map {
      case id ~ courseId ~ name ~ content ~ dateOfAdding => CourseMaterialTable(id, courseId, name, content, dateOfAdding)
    }
  }

  def findCourseMaterialTablesByCourseId(courseId: Long)(implicit db: Database): List[CourseMaterialTable] =
    db.withConnection { implicit connection =>
      SQL"SELECT * FROM course_material where course_id = $courseId".as(courseMaterialParser.*)
    }

}
