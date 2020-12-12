package domain.service.messages

import domain.model.Email

final case class ProfileRequest(email: Email)

sealed trait ProfileResponse
final case class ProfileSuccess(content: String) extends ProfileResponse
case object ProfileNotFound extends ProfileResponse
