package domain.service

import java.util.UUID

import domain.model.{Account, Email, SessionId}

final case class SignupRequest(vercode: UUID, account: Account)

sealed trait SignupResponse
case object SignupSuccess extends SignupResponse
case object UserNotFound extends SignupResponse
case object AccountExists extends SignupResponse
case object EmailExists extends SignupResponse
case object InsecurePassword extends SignupResponse

final case class LoginRequest(account: Account)

sealed trait LoginResponse
final case class LoginSuccess(sessionId: SessionId) extends LoginResponse
case object EmailNotFound extends LoginResponse
case object PasswordIncorrect extends LoginResponse

sealed trait WhoamiResponse
final case class Credentials(sessionId: SessionId, email: Email) extends WhoamiResponse
final case class SessionNotFound(sessionId: SessionId) extends WhoamiResponse
