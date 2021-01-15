package domain.service.messages

import java.time.LocalDate

import domain.model.Email

final case class CourseListRequest(email: Email)

sealed trait CourseListResponse
final case class StudentCourseListSuccess(content: String) extends CourseListResponse
final case class TeacherCourseListSuccess(content: String) extends CourseListResponse
case object CourseListFailure extends CourseListResponse

final case class CourseRequest(id: Long, email: Email)

sealed trait CourseResponse
final case class CourseSuccess(content: String) extends CourseResponse
case object CourseFailure extends CourseResponse

final case class AddCourseMaterialRequest(courseId: Long, name: String, content: String, email: Email)

sealed trait AddCourseMaterialResponse
case object AddCourseMaterialSuccess extends AddCourseMaterialResponse
case object NotEnoughRightsToAddCourseMaterial extends AddCourseMaterialResponse

final case class DeleteCourseMaterialRequest(materialId: Long, email: Email)

sealed trait DeleteCourseMaterialResponse
case object DeleteCourseMaterialSuccess extends DeleteCourseMaterialResponse
case object NotEnoughRightsToDeleteCourseMaterial extends DeleteCourseMaterialResponse

final case class UpdateCourseMaterialRequest(materialId: Long, name: Option[String], content: Option[String], email: Email)

sealed trait UpdateCourseMaterialResponse
case object UpdateCourseMaterialSuccess extends UpdateCourseMaterialResponse
case object NotEnoughRightsToUpdateCourseMaterial extends UpdateCourseMaterialResponse

final case class AddHomeTaskRequest(
  courseId: Long,
  name: String,
  startDate: LocalDate,
  finishDate: LocalDate,
  description: String,
  email: Email
)

sealed trait AddHomeTaskResponse
case object AddHomeTaskSuccess extends AddHomeTaskResponse
case object NotEnoughRightsToAddHomeTask extends AddHomeTaskResponse

final case class DeleteHomeTaskRequest(homeTaskId: Long, email: Email)

sealed trait DeleteHomeTaskResponse
case object DeleteHomeTaskSuccess extends DeleteHomeTaskResponse
case object NotEnoughRightsToDeleteHomeTask extends DeleteHomeTaskResponse

final case class UpdateHomeTaskRequest(
  homeTaskId: Long,
  name: Option[String],
  timeInterval: Option[(LocalDate, LocalDate)],
  description: Option[String],
  email: Email
)

sealed trait UpdateHomeTaskResponse
case object UpdateHomeTaskSuccess extends UpdateHomeTaskResponse
case object NotEnoughRightsToUpdateHomeTask extends UpdateHomeTaskResponse
