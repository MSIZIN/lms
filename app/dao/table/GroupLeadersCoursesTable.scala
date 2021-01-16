package dao.table

import java.util.UUID

import anorm.SqlParser.get
import anorm.{RowParser, SqlStringInterpolation, ~}
import play.api.db.Database

final case class GroupLeadersCoursesTable(
  studentId: UUID,
  courseId: Long
)
object GroupLeadersCoursesTable {

  private val groupLeadersCoursesParser: RowParser[GroupLeadersCoursesTable] = {
    get[UUID]("group_leaders_courses.student_id") ~
      get[Long]("group_leaders_courses.course_id") map {
      case studentId ~ courseId => GroupLeadersCoursesTable(studentId, courseId)
    }
  }

  def findGroupLeadersCourseTablesByCourseId(courseId: Long)(implicit db: Database): List[GroupLeadersCoursesTable] =
    db.withConnection { implicit connection =>
      SQL"SELECT * FROM group_leaders_courses where course_id = $courseId".as(groupLeadersCoursesParser.*)
    }

  def insertGroupLeadersCoursesTableTable(studentId: UUID, courseId: Long)(implicit db: Database): Boolean =
    db.withConnection { implicit connection =>
      SQL"INSERT INTO group_leaders_courses(student_id, course_id) VALUES (CAST(${studentId.toString} AS UUID), $courseId)".execute()
    }

  def deleteGroupLeadersCoursesTableTable(studentId: UUID, courseId: Long)(implicit db: Database): Boolean =
    db.withConnection { implicit connection =>
      SQL"DELETE FROM group_leaders_courses WHERE student_id = CAST(${studentId.toString} AS UUID) AND course_id = $courseId".execute()
    }

}
