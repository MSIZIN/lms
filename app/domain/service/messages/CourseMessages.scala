package domain.service.messages

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
