package domain.model

final case class Email(value: String)
final case class Password(value: String)
final case class SessionId(value: String)
final case class Account(email: Email, password: Password)
