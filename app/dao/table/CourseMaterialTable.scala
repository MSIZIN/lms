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

  def findCourseMaterialTableById(id: Long)(implicit db: Database): Option[CourseMaterialTable] =
    db.withConnection { implicit connection =>
      SQL"SELECT * FROM course_material where id = $id".as(courseMaterialParser.singleOpt)
    }

  def insertCourseMaterialTable(courseId: Long, name: String, content: String)(implicit db: Database): Boolean =
    db.withConnection { implicit connection =>
      SQL"INSERT INTO course_material(course_id, name, content, date_of_adding) VALUES ($courseId, $name, $content, NOW()::DATE)".execute()
    }

  def deleteCourseMaterialTable(id: Long)(implicit db: Database): Boolean =
    db.withConnection { implicit connection =>
      SQL"DELETE FROM course_material WHERE id=$id".execute()
    }

  def updateNameInCourseMaterialTable(id: Long, name: String)(implicit db: Database): Boolean =
    db.withConnection { implicit connection =>
      SQL"UPDATE course_material SET name = $name WHERE id=$id".execute()
    }

  def updateContentInCourseMaterialTable(id: Long, content: String)(implicit db: Database): Boolean =
    db.withConnection { implicit connection =>
      SQL"UPDATE course_material SET content = $content WHERE id=$id".execute()
    }

  def updateDateOfAddingInCourseMaterialTable(id: Long)(implicit db: Database): Boolean =
    db.withConnection { implicit connection =>
      SQL"UPDATE course_material SET date_of_adding = NOW()::DATE WHERE id=$id".execute()
    }

}
