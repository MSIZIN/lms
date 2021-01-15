package domain.service

import com.google.inject.ImplementedBy
import domain.service.messages._

@ImplementedBy(classOf[CourseServiceImpl])
trait CourseService {
  def courseList(request: CourseListRequest): CourseListResponse
  def course(request: CourseRequest): CourseResponse
  def addCourseMaterial(request: AddCourseMaterialRequest): AddCourseMaterialResponse
  def deleteCourseMaterial(request: DeleteCourseMaterialRequest): DeleteCourseMaterialResponse
  def updateCourseMaterial(request: UpdateCourseMaterialRequest): UpdateCourseMaterialResponse
  def addHomeTask(request: AddHomeTaskRequest): AddHomeTaskResponse
  def deleteHomeTask(request: DeleteHomeTaskRequest): DeleteHomeTaskResponse
  def updateHomeTask(request: UpdateHomeTaskRequest): UpdateHomeTaskResponse
}
