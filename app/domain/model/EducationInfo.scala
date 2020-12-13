package domain.model

final case class EducationInfo(
  group: EducationalGroup,
  admissionYear: Int,
  degree: String,
  educationalForm: String,
  educationalBasis: String
)
