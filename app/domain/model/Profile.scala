package domain.model

case class Profile(
  person: Person,
  educationInfo: Option[EducationInfo]
)
