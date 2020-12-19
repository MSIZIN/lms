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
