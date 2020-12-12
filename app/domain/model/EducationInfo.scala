package domain.model

case class EducationInfo(
  group: EducationalGroup,
  admissionYear: Int,
  degree: String,
  educationalForm: String,
  educationalBasis: String
)
