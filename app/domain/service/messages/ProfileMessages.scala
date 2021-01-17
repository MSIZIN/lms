package domain.service.messages

import domain.model.{Email, SocialNetworkLink}

final case class ProfileRequest(email: Email)

sealed trait ProfileResponse
final case class ProfileSuccess(content: String) extends ProfileResponse
case object ProfileNotFound extends ProfileResponse

final case class UpdatePhoneRequest(phone: String, email: Email)
final case class UpdateHomeTownRequest(homeTown: String, email: Email)
final case class UpdatePersonInfoRequest(info: String, email: Email)
final case class UpdateLinkRequest(link: SocialNetworkLink, email: Email)

sealed trait UpdateResponse
case object UpdateSuccess extends UpdateResponse
case object WrongPhoneFormat extends UpdateResponse
final case class WrongLinkFormat(socialNetwork: String, requiredLinkPrefix: String) extends UpdateResponse

final case class GroupmatesRequest(email: Email)

sealed trait GroupmatesResponse
final case class GroupmatesSuccess(content: String) extends GroupmatesResponse
case object UserIsNotStudent extends GroupmatesResponse
