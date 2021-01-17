package dao

import java.util.UUID

import dao.table.AccountTable._
import dao.table.EducationInfoTable._
import dao.table.GroupsCoursesTable._
import dao.table.PersonTable._
import dao.table.TeachersCoursesTable._
import domain.model.{Email, Student, Teacher}
import play.api.db.Database

object CommonDaoActions {

  def findVerCodeByEmail(email: Email)(implicit db: Database): Option[UUID] =
    findAccountTableByEmail(email).map(_.personVerificationCode)

  def isStudent(email: Email)(implicit db: Database): Boolean =
    (
      for {
        vercode <- findVerCodeByEmail(email)
        student <- findStudentByVerCode(vercode)
      } yield student
    ) match {
      case Some(_) => true
      case None    => false
    }

  def isTeacherOfCourse(courseId: Long, email: Email)(implicit db: Database): Boolean =
    (
      for {
        vercode <- findVerCodeByEmail(email)
        teacherTable <- findTeachersCoursesTablesByCourseId(courseId).find(table => table.teacherId == vercode)
      } yield teacherTable
    ) match {
      case Some(_) => true
      case None    => false
    }

  def isStudentOfCourse(courseId: Long, email: Email)(implicit db: Database): Boolean =
    (
      for {
        vercode <- findVerCodeByEmail(email)
        groupId <- findEducInfoTableByVerCode(vercode).map(_.groupId)
      } yield findGroupsCoursesTablesByGroupId(groupId)
    ) match {
      case Some(groupsCourses) if groupsCourses.exists(_.courseId == courseId) => true
      case _                                                                   => false
    }

  def findStudentByVerCode(verificationCode: UUID)(implicit db: Database): Option[Student] =
    findPersonTableByVerCode(verificationCode)
      .filter(_.position == "студент")
      .map(personTable => Student(personTable.firstName, personTable.lastName, personTable.middleName))

  def findTeacherByVerCode(verificationCode: UUID)(implicit db: Database): Option[Teacher] =
    findPersonTableByVerCode(verificationCode)
      .filter(_.position == "преподаватель")
      .map(personTable => Teacher(personTable.firstName, personTable.lastName, personTable.middleName))

}
