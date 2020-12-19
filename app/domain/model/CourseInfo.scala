package domain.model

final case class CourseInfo(
  name: String,
  description: String,
  teachers: List[Teacher],
  groupLeaders: List[Student],
  materials: List[CourseMaterial],
  homeTasks: List[HomeTask]
)
