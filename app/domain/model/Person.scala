package domain.model

final case class Person(
  firstName: String,
  lastName: String,
  middleName: String,
  phone: Option[String] = None,
  homeTown: Option[String] = None,
  info: Option[String] = None,
  vkLink: Option[String] = None,
  facebookLink: Option[String] = None,
  linkedinLink: Option[String] = None,
  instagramLink: Option[String] = None
)
