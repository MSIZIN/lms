package domain.model

final case class Profile(
  person: Person,
  educationInfo: Option[EducationInfo]
)
