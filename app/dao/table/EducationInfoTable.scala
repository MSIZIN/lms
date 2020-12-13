package dao.table

import java.util.UUID

import anorm.SqlParser.get
import anorm.{RowParser, SqlStringInterpolation, ~}
import play.api.db.Database

final case class EducationInfoTable(
  id: Option[Long] = None,
  studentId: UUID,
  groupId: Long,
  admissionYear: Int,
  degree: String,
  educationalForm: String,
  educationalBasis: String
)
object EducationInfoTable {

  private val educationInfoParser: RowParser[EducationInfoTable] = {
    get[Option[Long]]("education_info.id") ~
      get[UUID]("education_info.student_id") ~
      get[Long]("education_info.group_id") ~
      get[Int]("education_info.admission_year") ~
      get[String]("education_info.degree") ~
      get[String]("education_info.educational_form") ~
      get[String]("education_info.educational_basis") map {
      case id ~ studentId ~ groupId ~ admissionYear ~ degree ~ educationalForm ~ educationalBasis =>
        EducationInfoTable(id, studentId, groupId, admissionYear, degree, educationalForm, educationalBasis)
    }
  }

  def findEducInfoTableByVerCode(verificationCode: UUID)(implicit db: Database): Option[EducationInfoTable] =
    db.withConnection { implicit connection =>
      SQL"SELECT * FROM education_info where student_id = CAST(${verificationCode.toString} AS UUID)".as(educationInfoParser.singleOpt)
    }

}
