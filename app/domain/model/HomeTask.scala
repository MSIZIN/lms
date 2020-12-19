package domain.model

import java.time.LocalDate

final case class HomeTask(
  id: Long,
  name: String,
  startDate: LocalDate,
  finishDate: LocalDate,
  description: String
)
