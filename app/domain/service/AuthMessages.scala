package domain.service

import domain.model.{Account, Email, SessionId}

final case class SignupRequest(account: Account)

sealed trait SignupResponse
final case class SignupSuccess(email: Email) extends SignupResponse
final case class UserExists(email: Email) extends SignupResponse

final case class LoginRequest(account: Account)

sealed trait LoginResponse
final case class LoginSuccess(sessionId: SessionId) extends LoginResponse
case object EmailNotFound extends LoginResponse
case object PasswordIncorrect extends LoginResponse

sealed trait WhoamiResponse
final case class Credentials(sessionId: SessionId, email: Email) extends WhoamiResponse
final case class SessionNotFound(sessionId: SessionId) extends WhoamiResponse
