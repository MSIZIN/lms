package domain.service

import com.google.inject.ImplementedBy
import domain.service.messages._

@ImplementedBy(classOf[CourseServiceImpl])
trait CourseService {
  def courseList(request: CourseListRequest): CourseListResponse
  def course(request: CourseRequest): CourseResponse
}
