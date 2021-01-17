package domain.model

import java.time.LocalDate

final case class HomeTaskSolution(student: Student, dateOfAdding: Option[LocalDate], content: Option[String])
