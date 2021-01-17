package dao.table

import anorm.SqlParser.get
import anorm.{RowParser, SqlStringInterpolation, ~}
import play.api.db.Database

final case class GroupsCoursesTable(
  groupId: Long,
  courseId: Long
)
object GroupsCoursesTable {

  private val groupsCoursesParser: RowParser[GroupsCoursesTable] = {
    get[Long]("groups_courses.group_id") ~
      get[Long]("groups_courses.course_id") map {
      case groupId ~ courseId => GroupsCoursesTable(groupId, courseId)
    }
  }

  def findGroupsCoursesTablesByGroupId(groupId: Long)(implicit db: Database): List[GroupsCoursesTable] =
    db.withConnection { implicit connection =>
      SQL"SELECT * FROM groups_courses WHERE group_id = $groupId".as(groupsCoursesParser.*)
    }

  def findGroupsCoursesTablesByCourseId(courseId: Long)(implicit db: Database): List[GroupsCoursesTable] =
    db.withConnection { implicit connection =>
      SQL"SELECT * FROM groups_courses WHERE course_id = $courseId".as(groupsCoursesParser.*)
    }

}
