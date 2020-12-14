package dao.table

import java.util.UUID

import anorm.SqlParser.get
import anorm.{RowParser, SqlStringInterpolation, ~}
import play.api.db.Database

final case class TeachersCoursesTable(
  teacherId: UUID,
  courseId: Long
)
object TeachersCoursesTable {

  private val groupsCoursesParser: RowParser[TeachersCoursesTable] = {
    get[UUID]("teachers_courses.teacher_id") ~
      get[Long]("teachers_courses.course_id") map {
      case teacherId ~ courseId =>
        TeachersCoursesTable(teacherId, courseId)
    }
  }

  def findTeachersCoursesTablesByVerCode(verificationCode: UUID)(implicit db: Database): List[TeachersCoursesTable] =
    db.withConnection { implicit connection =>
      SQL"SELECT * FROM teachers_courses where teacher_id = CAST(${verificationCode.toString} AS UUID)".as(groupsCoursesParser.*)
    }

}
