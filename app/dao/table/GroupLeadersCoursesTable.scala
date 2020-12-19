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

}
